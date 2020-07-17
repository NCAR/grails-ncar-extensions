package edu.ucar.util

import spock.lang.Specification

class UtcUtilSpec extends Specification {

    def setupSpec() {
        UtcUtil.setZone()
    }

    void "test user timezone"() {
        expect:"user timezone is UTC"
            "UTC" == System.getProperty('user.timezone')
    }

    void "test default timezone"() {
        expect:"default timezone is C.U.T."
            "Coordinated Universal Time" == java.util.TimeZone.getDefault().getDisplayName()
    }

    void "test UTC Timestamp"() {
        given:
            def ts
        when:
            ts = new java.sql.Timestamp(0)
        then:
            "Z" == ts.zoneOffset.toString()
    }

    void "test UTC Date"() {
        given:
            def date
        when:
            date = new java.util.Date(0)
        then:
            0 == date.timezoneOffset
    }

}
