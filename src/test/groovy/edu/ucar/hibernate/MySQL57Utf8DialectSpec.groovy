package edu.ucar.hibernate

import spock.lang.Specification

class MySQL57Utf8DialectSpec extends Specification {

    void "test innodb"() {
        expect:"Hibernate superclass specifies the correct engine"
            new MySQL57Utf8Dialect().tableTypeString.toLowerCase().contains("innodb")
    }

    void "test utf8"() {
        expect:"class specifies the correct charset"
            new MySQL57Utf8Dialect().tableTypeString.toLowerCase().contains("utf8")
    }

}
