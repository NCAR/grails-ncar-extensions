package edu.ucar.fields

import spock.lang.Specification
import grails.plugin.formfields.*
import grails.testing.web.taglib.TagLibUnitTest
import grails.testing.gorm.DataTest
import grails.testing.web.GrailsWebUnitTest
import org.grails.plugins.web.DefaultGrailsTagDateHelper
import org.grails.datastore.gorm.validation.constraints.eval.DefaultConstraintEvaluator
import org.grails.scaffolding.model.DomainModelServiceImpl
import org.grails.scaffolding.model.property.DomainPropertyFactory
import org.grails.scaffolding.model.property.DomainPropertyFactoryImpl
import org.grails.datastore.mapping.model.MappingContext
import org.grails.spring.beans.factory.InstanceFactoryBean
import grails.plugin.formfields.BeanPropertyAccessorFactory
import grails.core.support.proxy.DefaultProxyHandler

/*
 * setups/cleanups copied from:
 *  grails.plugin.formfields.taglib.AbstractFormFieldsTagLibSpec
 * which we can't just extend since it's not in any jar (?)
 */
class FieldsSpec extends Specification
    implements TagLibUnitTest<FormFieldsTagLib>,
               DataTest,
               GrailsWebUnitTest
{
    def mockFormFieldsTemplateService = Mock(FormFieldsTemplateService)

    Dates epochZeroDatesInstance

    def setupSpec() {
        edu.ucar.util.UtcUtil.setZone()     // UTC!!!

        mockDomain(Dates)

        defineBeans { ->
            grailsTagDateHelper(DefaultGrailsTagDateHelper)
            constraintsEvaluator(DefaultConstraintEvaluator)
            def dpf = new DomainPropertyFactoryImpl(
                grailsDomainClassMappingContext:
                    applicationContext.getBean("grailsDomainClassMappingContext",
                        MappingContext
                    ),
              trimStrings: true, convertEmptyStringsToNull: true
              )
            fieldsDomainPropertyFactory(InstanceFactoryBean, dpf, DomainPropertyFactory)
            domainModelService(DomainModelServiceImpl) {
                domainPropertyFactory = ref('fieldsDomainPropertyFactory')
            }
            beanPropertyAccessorFactory(BeanPropertyAccessorFactory) {
                //constraintsEvaluator = ref('org.grails.beans.ConstraintsEvaluator')
                constraintsEvaluator = ref('constraintsEvaluator')
                proxyHandler = new DefaultProxyHandler()
                grailsDomainClassMappingContext = ref("grailsDomainClassMappingContext")
                fieldsDomainPropertyFactory = ref('fieldsDomainPropertyFactory')
            }
        }
    }

    def setup() {
        epochZeroDatesInstance = Dates.epochZero()
        mockFormFieldsTemplateService.getTemplateFor('wrapper') >> "wrapper"
        mockFormFieldsTemplateService.getTemplateFor('widget') >> "widget"
        mockFormFieldsTemplateService.getTemplateFor('displayWrapper') >> "displayWrapper"
        mockFormFieldsTemplateService.getTemplateFor('displayWidget') >> "displayWidget"
        tagLib.formFieldsTemplateService = mockFormFieldsTemplateService
    }

    def cleanup() {
        views.clear()
        applicationContext.getBean("groovyPagesTemplateEngine").clearPageCache()
        applicationContext.getBean("groovyPagesTemplateRenderer").clearCache()

        // bit of a hack but messages don't get torn down otherwise
        messageSource.@messages.clear()
    }

    void 'instance not null'() {
        expect:
            null != epochZeroDatesInstance
    }

    void 'display of epoch zero has correct year'() {
        when:
            views['/templates/_fields/_list.gsp'] = '''\
<g:each in="${domainProperties}">
${it.name} = ${body(it)}
</g:each>
'''
            def result = applyTemplate('<f:display bean="epochZeroDatesInstance" />',
                [epochZeroDatesInstance:epochZeroDatesInstance]
                )
        then:
            result.contains('1970')
            !result.contains('1969')    // UTC!!!
    }

}



import java.time.*
import java.sql.Timestamp as SqlTimestamp
import java.sql.Date as SqlDate
import java.sql.Time as SqlTime
import grails.persistence.Entity

@Entity
class Dates {
    Instant instant
    LocalDateTime localDateTime
    LocalDate localDate
    SqlTimestamp timestamp

    // sql.Date and Time are not supported by g:formatDate
    // Date is coming soon: https://github.com/grails/grails-gsp/issues/50
    //SqlDate sqlDate
    //SqlTime sqlTime

    java.util.Date utilDate

    static Dates epochZero() {
        Dates instance = new Dates()
        instance.instant = Instant.EPOCH
        instance.localDateTime = LocalDateTime.ofEpochSecond(0L,0,ZoneOffset.UTC)
        instance.localDate = LocalDate.ofEpochDay(0L)
        instance.timestamp = new SqlTimestamp(0L)
        //instance.sqlDate = new SqlDate(0L)
        //instance.sqlTime = new SqlTime(0L)
        instance.utilDate = new java.util.Date(0L)
        instance
    }

}
