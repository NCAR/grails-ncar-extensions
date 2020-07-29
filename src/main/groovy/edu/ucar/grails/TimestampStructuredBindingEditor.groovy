package edu.ucar.grails

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime
import grails.util.GrailsStringUtils
import org.grails.web.databinding.converters.AbstractStructuredBindingEditor

/**
 * Structured binding editor for creating an SQL Timestamp from web form parameters.
 *
 * Uses 6 parameters, each optional: year, month, day, hour, minute, second.
 * Or, uses "epoch" -- Unix time in seconds.
 * Either set can be used with optional millisecond or nanosecond fields
 * to add fractional seconds to the value.
 * In addition, "epochMilli" -- Unix time in milliseconds -- can be used,
 * in which case other values are ignored.
 *
 * Code based on Grails 1/2 org.codehaus.groovy.grails.web.binding.StructuredDateEditor
 * and Grails 4 org.grails.web.binding.StructuredDateEditor,
 * and my previous extensions to that code. Those files are goverened by
 * Apache 2.0 license, as is this whole repo. See file "/LICENSE".
 *
 * @author John Allison
 * @author Graeme Rocher
 */
class TimestampStructuredBindingEditor
    extends AbstractStructuredBindingEditor<Timestamp>
{

    public Timestamp getPropertyValue(Map values) {
        String yearString = (String) values.get("year")
        String monthString = (String) values.get("month")
        String dayString = (String) values.get("day")
        String hourString = (String) values.get("hour")
        String minuteString = (String) values.get("minute")
        String secondString = (String) values.get("second")
        String milliSecondString = (String) values.get("millisecond")
        String nanoSecondString = (String) values.get("nanosecond")
        String epochString = (String) values.get("epoch")
        String epochMilliString = (String) values.get("epochMilli")

        if (GrailsStringUtils.isBlank(yearString)
                && GrailsStringUtils.isBlank(monthString)
                && GrailsStringUtils.isBlank(dayString)
                && GrailsStringUtils.isBlank(hourString)
                && GrailsStringUtils.isBlank(minuteString)
                && GrailsStringUtils.isBlank(secondString)
                && GrailsStringUtils.isBlank(milliSecondString)
                && GrailsStringUtils.isBlank(nanoSecondString)
                && GrailsStringUtils.isBlank(epochString)
                ) { return null }

        try {
            long epoch = 0
            int year = 0
            int month = 0
            int day = 0
            int hour = 0
            int minute = 0
            int second = 0
            int milliSecond = 0
            int nanoSecond = 0

            if (values.containsKey("nanosecond") &&
                !GrailsStringUtils.isBlank(nanoSecondString)
               ) {
                nanoSecond = getIntegerValue(values, "nanosecond", 0)
            } else if (values.containsKey("millisecond") &&
                !GrailsStringUtils.isBlank(milliSecondString)
               ) {
                nanoSecond = getIntegerValue(values, "millisecond", 0) *
                    (LocalTime.NANOS_PER_SECOND/1000)
            }

            if (!GrailsStringUtils.isBlank(epochMilliString)) {
                return new Timestamp( getLongValue(values, "epochMilli", 0) )
            }

            if (!GrailsStringUtils.isBlank(epochString)) {
                epoch = getLongValue(values, "epoch", 0)
                Timestamp ts = new Timestamp(epoch*1000L)
                ts.setNanos(nanoSecond)
                return ts
            }

            year = getIntegerValue(values, "year", 1970)
            month = getIntegerValue(values, "month", 1)
            day = getIntegerValue(values, "day", 1)
            hour = getIntegerValue(values, "hour", 0)
            minute = getIntegerValue(values, "minute", 0)
            second = getIntegerValue(values, "second", 0)
            LocalDateTime ldt = LocalDateTime.of(year,month,day,hour,minute,second,nanoSecond)
            return Timestamp.valueOf(ldt)

        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Bad number format: " + nfe.getMessage())
        }

        null
    }

    private int getIntegerValue(Map values, String name, int defaultValue)
        throws NumberFormatException
    {
        if (values.get(name) != null)
            return Integer.parseInt((String) values.get(name))
        return defaultValue
    }

    private long getLongValue(Map values, String name, long defaultValue)
        throws NumberFormatException
    {
        if (values.get(name) != null)
            return Long.parseLong((String) values.get(name))
        return defaultValue
    }

}
