package edu.ucar.util

import groovy.util.logging.Slf4j

/**
 * Simple utility class to set system timezones to UTC.
 *
 */
@Slf4j
class UtcUtil {

    // static init block won't run until a static method is called or static field is used
    // so it's basically useless for us; we have to call the method anyways

    /**
     * Set the system timezone to UTC.
     *
     * Uses multiple methods to try to assure we are in UTC.
     * Call this method from your Grails BootStrap.init closure.
     */
    static void setZone() {
        System.setProperty('user.timezone','UTC')
        log.info 'user.timezone = ' + System.getProperty('user.timezone')

        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone('UTC'))
        log.info 'java.util.TimeZone.default = ' + java.util.TimeZone.getDefault().getDisplayName()
    }

}
