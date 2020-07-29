package edu.ucar.grails

import grails.databinding.SimpleMapDataBindingSource
import grails.web.databinding.GrailsWebDataBinder
import spock.lang.Specification
import java.sql.Timestamp
import java.time.Instant

// adapted from grails.web.databinding.GrailsWebDataBindingStructuredEditorSpec
class TimestampStructuredBindingEditorSpec extends Specification {

    static binder
    def myTimestampInstance

    void setupSpec() {
        edu.ucar.util.UtcUtil.setZone()     // UTC!!!
        binder = new GrailsWebDataBinder()
        binder.setStructuredBindingEditors(new TimestampStructuredBindingEditor())
    }

    void setup() {
        myTimestampInstance = new MyTimestamp()
    }

    void cleanup() {
        myTimestampInstance = null
    }

    void 'test basic binding of epoch zero to a Timestamp'() {
        given: 'A binder setup and epoch-zero input parameter'
        def expected = '1970-01-01 00:00:00.0'
        def params = [val:'struct', val_epoch:'0'] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myTimestampInstance, params

        then:
        expected.equals(myTimestampInstance.val.toString())
    }

    void 'test party time'() {
        given: "party like it's"
        def expected = '1999-01-01 00:00:00.0'
        def params = [val:'struct', val_year:'1999'] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myTimestampInstance, params

        then:
        expected.equals(myTimestampInstance.val.toString())
    }

    void 'test some date components'() {
        given: 'a set of date components as String params'
        def expectedEpoch = 1595957434L
        def expectedEpochMillis = expectedEpoch * 1000L
        def expectedString = '2020-07-28 17:30:34.0'
        def params = [val:'struct',
            val_year:'2020',
            val_month:'7',
            val_day:'28',
            val_hour:'17',
            val_minute:'30',
            val_second:'34',
            ] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myTimestampInstance, params

        then:
        expectedEpochMillis == myTimestampInstance.val.time
        Instant.ofEpochSecond(expectedEpoch).equals(myTimestampInstance.val.toInstant())
        expectedString.equals(myTimestampInstance.val.toString())
    }

    void 'test milliseconds'() {
        given: 'millisecond parameter'
        def params = [val:'struct',
            val_year:'1970',
            val_millisecond:'42',
            ] as SimpleMapDataBindingSource

        when: 'binding happens'
        binder.bind myTimestampInstance, params

        then:
        70 == myTimestampInstance.val.year // deprecated
        0 == myTimestampInstance.val.month // deprecated
        42000000 == myTimestampInstance.val.nanos   // ms are converted to ns
    }

}

class MyTimestamp {
    Timestamp val
}
