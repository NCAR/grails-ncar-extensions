<%@ page import="edu.ucar.grails.DatetimeFormatters"
%><g:set var="localValue" value="${value.toLocalDateTime()}"
/><input type="hidden" name="${property}" value="struct"
/><input type="text" name="${property}_year" id="${property}_year" aria-labelledby="${property}" title="year" value="${value ? DatetimeFormatters.formatYear(localValue) : ''}" size="4" maxlength="4" tabindex="1"
/>-<input type="text" name="${property}_month" id="${property}_month" aria-labelledby="${property}" title="month" value="${value ? DatetimeFormatters.formatMonth(localValue) : ''}" size="2" maxlength="2" tabindex="1"
/>-<input type="text" name="${property}_day" id="${property}_day" aria-labelledby="${property}" title="day" value="${value ? DatetimeFormatters.formatDay(localValue) : ''}" size="2" maxlength="2" tabindex="1"
/>&nbsp;&nbsp;&nbsp;<input type="text" name="${property}_hour" id="${property}_hour" aria-labelledby="${property}" title="hour" value="${value ? DatetimeFormatters.formatHour(localValue) : ''}" size="2" maxlength="2" tabindex="1"
/>:<input type="text" name="${property}_minute" id="${property}_minute" aria-labelledby="${property}" title="minute" value="${value ? DatetimeFormatters.formatMinute(localValue) : ''}" size="2" maxlength="2" tabindex="1"
/>:<input type="text" name="${property}_second" id="${property}_second" aria-labelledby="${property}" title="second" value="${value ? DatetimeFormatters.formatSecond(localValue) : ''}" size="2" maxlength="2" tabindex="1"
/>.<input type="text" name="${property}_nanosecond" id="${property}_nanosecond" aria-labelledby="${property}" title="nanosecond" value="${value ? DatetimeFormatters.formatNano(localValue) : ''}" size="9" maxlength="9" tabindex="1"
/>
