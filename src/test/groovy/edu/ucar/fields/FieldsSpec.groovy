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
import org.jsoup.Jsoup

/*
 * setups/cleanups copied from:
 *  grails.plugin.formfields.taglib.AbstractFormFieldsTagLibSpec
 * see also grails.plugin.formfields.BuildsAccessorFactory
 * neither of which we can just extend since they're in src/test not in any jar
 */
class FieldsSpec extends Specification
    implements TagLibUnitTest<FormFieldsTagLib>, DataTest
{
    def mockFormFieldsTemplateService = Mock(FormFieldsTemplateService)

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
        given:"a test domain class instance"
            def testBean = Dates.epochZero()
        expect:"the object exists"
            null != testBean
    }

    void 'display of epoch zero has correct year'() {
        given:"test domainclass bean for epoch zero"
            def testBean = Dates.epochZero()
        when:"a simple list template is provided"
            views['/templates/_fields/_list.gsp'] = '''\
<g:each in="${domainProperties}">
${it.name} = ${body(it)}
</g:each>
'''
            def result = applyTemplate('<f:display bean="testBean" />',
                [testBean:testBean]
                )
        then:"correct property strings are displayed"
            result
            result.contains('1970')
            !result.contains('1969')    // UTC!!!
            result.contains('instant = 1970')
    }

    void 'widget selects correct year for java.util.Date'() {
        given:
            def testBean = Dates.epochZero()
        when:"default templates in the plugin are applied"
            def result = applyTemplate('<f:widget bean="testBean" property="utilDate" />',
                [testBean:testBean]
                )
            def doc = Jsoup.parse(result)
        then:"correct year is pre-selected"
            result
            doc
            '1970'.equals doc.select('#utilDate_year option[selected=selected]').text()
    }

    // TODO test finding and using our added templates
    // probably in another tester copied from FormFieldsTemplateServiceSpec (??)
    // currently here we always get "" from applyTemplate

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
