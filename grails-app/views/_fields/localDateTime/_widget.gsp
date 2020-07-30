<%@ page import="edu.ucar.grails.DatetimeFormatters"
%><input type="hidden" name="${property}" value="struct"
/><input type="text" name="${property}_year" id="${property}_year" aria-labelledby="${property}" title="year" value="${value ? value.format(DatetimeFormatters.YEAR) : ''}" size="4" maxlength="4" tabindex="1"
/>-<input type="text" name="${property}_month" id="${property}_month" aria-labelledby="${property}" title="month" value="${value ? value.format(DatetimeFormatters.MONTH) : ''}" size="2" maxlength="2" tabindex="1"
/>-<input type="text" name="${property}_day" id="${property}_day" aria-labelledby="${property}" title="day" value="${value ? value.format(DatetimeFormatters.DAY) : ''}" size="2" maxlength="2" tabindex="1"
/>&nbsp;&nbsp;&nbsp;<input type="text" name="${property}_hour" id="${property}_hour" aria-labelledby="${property}" title="hour" value="${value ? value.format(DatetimeFormatters.HOUR) : ''}" size="2" maxlength="2" tabindex="1"
/>:<input type="text" name="${property}_minute" id="${property}_minute" aria-labelledby="${property}" title="minute" value="${value ? value.format(DatetimeFormatters.MINUTE) : ''}" size="2" maxlength="2" tabindex="1"
/>:<input type="text" name="${property}_second" id="${property}_second" aria-labelledby="${property}" title="second" value="${value ? value.format(DatetimeFormatters.SECOND) : ''}" size="2" maxlength="2" tabindex="1"
/>
