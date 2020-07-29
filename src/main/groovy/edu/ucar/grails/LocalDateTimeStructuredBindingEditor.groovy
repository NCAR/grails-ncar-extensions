package edu.ucar.grails

import java.time.*
import grails.util.GrailsStringUtils
import org.grails.web.databinding.converters.AbstractStructuredBindingEditor

/**
 * Structured binding editor for creating a LocalDateTime from web form parameters.
 *
 * Uses 6 parameters, each optional: year, month, day, hour, minute, second.
 * Or, uses "epoch" -- Unix time in seconds.
 * Either set can be used with optional millisecond or nanosecond fields
 * to add fractional seconds to the value.
 *
 * An optional "offset" value can be applied to the epoch,
 * but it is HIGHLY recommended to consider the epoch as UTC (the default).
 * One cannot know the applicable offset without knowing the local time,
 * in order to know the timezone rules in effect at that time,
 * but we cannot convert epoch to local without an offset.
 *
 * Code based on Grails 1/2 org.codehaus.groovy.grails.web.binding.StructuredDateEditor
 * and Grails 4 org.grails.web.binding.StructuredDateEditor,
 * and my previous extensions to that code. Those files are goverened by
 * Apache 2.0 license, as is this whole repo. See file "/LICENSE".
 *
 * @author John Allison
 * @author Graeme Rocher
 */
class LocalDateTimeStructuredBindingEditor
    extends AbstractStructuredBindingEditor<LocalDateTime>
{

    public LocalDateTime getPropertyValue(Map values) {
        String yearString = (String) values.get("year")
        String monthString = (String) values.get("month")
        String dayString = (String) values.get("day")
        String hourString = (String) values.get("hour")
        String minuteString = (String) values.get("minute")
        String secondString = (String) values.get("second")
        String milliSecondString = (String) values.get("millisecond")
        String nanoSecondString = (String) values.get("nanosecond")
        String epochString = (String) values.get("epoch")

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
            LocalDateTime ldt

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

            if (!GrailsStringUtils.isBlank(epochString)) {
                epoch = getLongValue(values, "epoch", 0)
                ZoneOffset offset = ZoneOffset.UTC
                if (values.containsKey("offset") &&
                    !GrailsStringUtils.isBlank(values.get("offset"))
                ) {
                    offset = ZoneOffset.of(values.get("offset"))
                }
                ldt = LocalDateTime.ofEpochSecond(epoch,nanoSecond,offset)
            } else {
                year = getIntegerValue(values, "year", 1970)
                month = getIntegerValue(values, "month", 1)
                day = getIntegerValue(values, "day", 1)
                hour = getIntegerValue(values, "hour", 0)
                minute = getIntegerValue(values, "minute", 0)
                second = getIntegerValue(values, "second", 0)
                ldt = LocalDateTime.of(year,month,day,hour,minute,second,nanoSecond)
            }

            return ldt
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Bad number format: " + nfe.getMessage())
        }
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
