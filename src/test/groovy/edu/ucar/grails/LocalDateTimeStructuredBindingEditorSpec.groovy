package edu.ucar.grails

import java.time.*
import grails.databinding.SimpleMapDataBindingSource
import grails.web.databinding.GrailsWebDataBinder
import spock.lang.Specification

// adapted from grails.web.databinding.GrailsWebDataBindingStructuredEditorSpec
class LocalDateTimeStructuredBindingEditorSpec extends Specification {

    static binder
    def myDateInstance

    void setupSpec() {
        binder = new GrailsWebDataBinder()
        binder.setStructuredBindingEditors(new LocalDateTimeStructuredBindingEditor())
    }

    void setup() {
        myDateInstance = new MyDate()
    }

    void cleanup() {
        myDateInstance = null
    }

    void 'test basic binding of epoch zero to a LocalDateTime'() {
        given: 'A binder setup and epoch-zero input parameter'
        def expected = '1970-01-01T00:00'
        def params = [val:'struct', val_epoch:'0'] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myDateInstance, params

        then:
        expected.equals(myDateInstance.val.toString())
    }

    void 'test party time'() {
        given: "party like it's"
        def expected = '1999-01-01T00:00'
        def params = [val:'struct', val_year:'1999'] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myDateInstance, params

        then:
        expected.equals(myDateInstance.val.toString())
    }

    void 'test some date components'() {
        given: 'a set of date components as String params'
        def expectedEpoch = 1595957434
        def expectedString = '2020-07-28T17:30:34'
        def params = [val:'struct',
            val_year:'2020',
            val_month:'7',
            val_day:'28',
            val_hour:'17',
            val_minute:'30',
            val_second:'34',
            ] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myDateInstance, params

        then:
        expectedEpoch == myDateInstance.val.toEpochSecond(ZoneOffset.UTC)
        expectedString.equals(myDateInstance.val.toString())
        Month.JULY == myDateInstance.val.month
        34 == myDateInstance.val.second
    }

    void 'test milliseconds'() {
        given: 'millisecond parameter'
        def params = [val:'struct',
            val_year:'1970',
            val_millisecond:'42',
            ] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myDateInstance, params

        then:
        1970 == myDateInstance.val.year
        Month.JANUARY == myDateInstance.val.month
        42000000 == myDateInstance.val.nano   // ms are converted to ns
    }

    void 'test nanoseconds'() {
        given: 'nanosecond parameter'
        def params = [val:'struct',
            val_year:'1970',
            val_nanosecond:'54',
            ] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myDateInstance, params

        then:
        1970 == myDateInstance.val.year
        Month.JANUARY == myDateInstance.val.month
        54 == myDateInstance.val.nano
        '1970-01-01T00:00:00.000000054'.equals(myDateInstance.val.toString())
    }

}

class MyDate {
    LocalDateTime val
}
