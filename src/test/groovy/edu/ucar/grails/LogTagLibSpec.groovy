package edu.ucar.grails

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification
import uk.org.lidalia.slf4jtest.TestLoggerFactory

class LogTagLibSpec extends Specification implements TagLibUnitTest<LogTagLib> {

    private static final String MESSAGE = 'Hello loggers!'
    private static final ArrayList<String> LEVELS = ['debug','info','warn','error']

    def logger
    def loggingEvents

    void setup() {
        logger = TestLoggerFactory.getTestLogger(LogTagLib.class)
        logger.clear()
    }

    void "test empty tag log"() {
        given:
            def returnVal
        when:"tag executed with no message argument"
            returnVal = tagLib.logMsg()
            loggingEvents = logger.loggingEvents
        then:"no output and empty log message"
            '' == returnVal.toString()
            1 == loggingEvents.size()
            '' == loggingEvents[0].message
    }

    void "test empty return"() {
        expect:"no output and logs went somewhere"
            '' == applyTemplate("<g:logMsg>$MESSAGE</g:logMsg>")
    }

    void "test basic log"() {
        when:
            applyTemplate("<g:logMsg>$MESSAGE</g:logMsg>")
            loggingEvents = logger.loggingEvents
        then:
            1 == loggingEvents.size()
            MESSAGE == loggingEvents[0].message
            uk.org.lidalia.slf4jext.Level.DEBUG == loggingEvents[0].level
    }

    void "test view message"() {
        given:
            def expected = "foo : $MESSAGE"
        when:
            applyTemplate("<g:logMsg view=\"foo\">$MESSAGE</g:logMsg>")
            loggingEvents = logger.loggingEvents
        then:
            1 == loggingEvents.size()
            expected == loggingEvents[0].message
            uk.org.lidalia.slf4jext.Level.DEBUG == loggingEvents[0].level
    }

    void "test log levels"() {
        when:
            LEVELS.each {
                applyTemplate("<g:logMsg level=\"$it\">$MESSAGE via $it</g:logMsg>")
            }
            loggingEvents = logger.loggingEvents
        then:
            LEVELS.size() == loggingEvents.size()
            LEVELS.eachWithIndex { it, i ->
                "$MESSAGE via $it" == loggingEvents[i].message
                uk.org.lidalia.slf4jext.Level."${it.toUpperCase()}" == loggingEvents[i].level
            }
    }

}
