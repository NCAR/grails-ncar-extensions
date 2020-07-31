package edu.ucar.grails

import java.time.format.DateTimeFormatter

/**
 * Holder for pre-defined DateTimeFormatter instances.
 *
 * Usages:
 * <ul>
 *   <li> value.format(DatetimeFormatters.YEAR)
 *   <li> DatetimeFormatters.YEAR.format(value)
 *   <li> DatetimeFormatters.formatYear(value)
 * </ul>
 *
 * Note that not all java time classes support the first usage,
 * and the latter two usages are equivalent.
 */
class DatetimeFormatters {

    static final DateTimeFormatter YEAR
    static {
        YEAR = DateTimeFormatter.ofPattern('yyyy')
    }

    static DateTimeFormatter MONTH
    static {
        MONTH = DateTimeFormatter.ofPattern('MM')
    }

    static DateTimeFormatter DAY
    static {
        DAY = DateTimeFormatter.ofPattern('dd')
    }

    static DateTimeFormatter HOUR
    static {
        HOUR = DateTimeFormatter.ofPattern('HH')
    }

    static DateTimeFormatter MINUTE
    static {
        MINUTE = DateTimeFormatter.ofPattern('mm')
    }

    static DateTimeFormatter SECOND
    static {
        SECOND = DateTimeFormatter.ofPattern('ss')
    }

    static DateTimeFormatter NANO
    static {
        NANO = DateTimeFormatter.ofPattern('nnnnnnnnn')
    }

    static String formatYear(val) { YEAR.format(val) }
    static String formatMonth(val) { MONTH.format(val) }
    static String formatDay(val) { DAY.format(val) }
    static String formatHour(val) { HOUR.format(val) }
    static String formatMinute(val) { MINUTE.format(val) }
    static String formatSecond(val) { SECOND.format(val) }
    static String formatNano(val) { NANO.format(val) }

}
