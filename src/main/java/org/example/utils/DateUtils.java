package org.example.utils;


import org.example.exception.AppException;
import org.example.exception.BaseErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.example.exception.BaseErrorEnum.BAD_DATE_FORMAT;
import static org.example.exception.BaseErrorEnum.DATE_PARSE_EXCEPTION;


public class DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
    private static final String REPORT_DATE_FORMAT = "dd-MM-yyyy hh:mm:ss a";
    private static final String DMS_DB_DATE_FORMAT = "dd-MM-yyyy HH:mm a";
    private static final String LOCAL_ZONE = "Asia/Kolkata";
    private static final String CAMPAIGN_START_END_DATE_FORMAT = "dd-MM-yyyy HH:mm";
    private static final ZoneId ZONE = ZoneId.of(LOCAL_ZONE);

    private DateUtils() {
        throw new UnsupportedOperationException("Support only static methods.");
    }

    public static long currentTimeStampInEpochMillis() {
        return currentTimeStampInstant().toEpochMilli();
    }

    public static Long convertDateToEpochTimeStamp(Date aDate) {
        if (NullSafeUtils.isAmNullOrEmpty(aDate)) {
            return null;
        }
        return aDate.toInstant().toEpochMilli();
    }

    public static Date currentDate() {
        return Date.from(currentTimeStampInstant());
    }

    public static Date currentDateOnlyNoTime() {
        return floorDate(currentDate());
    }

    public static String currentDateInDmsFormatForReport() {
        return dateInDmsFormatForReport(currentDate());
    }

    public static String currentDateInDmsFormatForDB() {
        return dateInDmsFormatForDB(currentDate());
    }

    public static String dateInDmsFormatForReport(Date date) {
        return convertDateToString(date, REPORT_DATE_FORMAT);
    }

    public static String dateInDmsFormatForDB(Date date) {
        return convertDateToString(date, DMS_DB_DATE_FORMAT);
    }

    public static String convertDateToString(Date date, String format) {
        if (NullSafeUtils.isAmNullOrEmpty(date)) {
            return null;
        }
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZONE);
        return ldt.format(createFormatter(format));
    }

    public static LocalDateTime convertUtilDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZONE);
    }

    public static Date convertLocalDateTimeToUtilDate(LocalDateTime localDateTime) {
        try {
            return Date.from(localDateTime.atZone(ZONE).toInstant());
        } catch (Exception e) {
            throw new AppException(BaseErrorEnum.DATE_CONVERT_EXCEPTION);
        }
    }

    public static Date floorDate(Date sourceDate) {
        Calendar calendar = Calendar.getInstance();
        if (sourceDate != null) {
            calendar.setTime(sourceDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            sourceDate = calendar.getTime();
        }
        return sourceDate;
    }

    public static Date ceilDate(Date sourceDate) {
        Calendar calendar = Calendar.getInstance();
        if (sourceDate != null) {
            calendar.setTime(sourceDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            sourceDate = calendar.getTime();
        }
        return sourceDate;
    }

    public static Timestamp floorDateInTimestamp(Date sourceDate) {
        return Timestamp.from(floorDate(sourceDate).toInstant());
    }

    public static Timestamp ceilDateInTimestamp(Date sourceDate) {
        return Timestamp.from(ceilDate(sourceDate).toInstant());
    }

    public static Date stringToDateOnlyFormat(String dateString, String format) {
        if (StringUtils.isBlank(dateString) || StringUtils.isBlank(format)) {
            throw new AppException(BAD_DATE_FORMAT);
        }
        try {
            LocalDate localDate = LocalDate.parse(dateString, createFormatter(format));
            LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
            return convertLocalDateTimeToUtilDate(localDateTime);
        } catch (Exception e) {
            throw new AppException(DATE_PARSE_EXCEPTION);
        }

    }

    public static Date stringToDateFormat(String dateString, String format) {
        if (StringUtils.isBlank(dateString) || StringUtils.isBlank(format)) {
            throw new AppException(BAD_DATE_FORMAT);
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, createFormatter(format));
            return convertLocalDateTimeToUtilDate(localDateTime);
        } catch (Exception e) {
            throw new AppException(DATE_PARSE_EXCEPTION);
        }
    }

    public static int findNoOfDaysDiffBetweenTwoDates(Date start, Date end) {
        if (NullSafeUtils.isAmNullOrEmpty(start) || NullSafeUtils.isAmNullOrEmpty(start))
            throw new AppException(BAD_DATE_FORMAT);
        return (int) TimeUnit.DAYS.convert(
                end.getTime() - start.getTime()
                , TimeUnit.MILLISECONDS);
    }

    public static Date addDaysInDate(Date source, int noOfDays) {
        if (NullSafeUtils.isAmNullOrEmpty(source))
            throw new AppException(BAD_DATE_FORMAT);
        return org.apache.commons.lang3.time.DateUtils.addDays(source, noOfDays);
    }

    public static boolean isTheseDatesEqualsOrBefore(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return false;
        } else {
            int compDay = secondDate.compareTo(firstDate);
            return compDay <= 0;
        }
    }

    public static boolean isBothDateOfSameDate(final Date firstDate,
                                               final Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return false;
        } else {
            int compDay = floorDate(firstDate).compareTo(floorDate(secondDate));
            if (compDay == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isTheseDatesEqualsOrAfter(Date gDate, Date lDate) {
        if (gDate == null || lDate == null) {
            return false;
        } else {
            int compDay = lDate.compareTo(gDate);
            return compDay >= 0;
        }
    }

    public static boolean isMeBetweenTheseTwoDates(Date meDate, Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null || meDate == null) {
            return false;
        } else {
            int compDay1 = meDate.compareTo(firstDate);
            int compDay2 = meDate.compareTo(secondDate);
            if (compDay1 >= 0 && compDay2 <= 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static String getDateInAnotherTimeZone(Date date, TimeZone timeZone, String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        DateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    public static long currentDateAndTimeInMillis() {
        return currentTimeStampInEpochMillis();
    }

    public static java.sql.Date convertUtilDateToSqlDate(long timeInMills) {
        return new java.sql.Date(timeInMills);
    }

    public static boolean amIFutureDate(Timestamp aDate) {
        int diff = findNoOfDaysDiffBetweenTwoDates(currentDate(), aDate);
        return diff > 0;
    }

    public static Date subtractDaysFromCurrentDate(int noOfDays) {
        return addDaysInDate(DateUtils.currentDate(), -noOfDays);

    }

    public static Long convertDmsReportDateStringToEpochSeconds(String dateString) {
        return convertDateStringToEpochSeconds(dateString, REPORT_DATE_FORMAT);
    }

    public static Long convertDateStringToEpochSeconds(String dateString, String formatString) {
        Date aDate = stringToDateFormat(dateString, formatString);
        return convertDateToEpochTimeStampInSeconds(aDate);
    }

    public static long currentTimeStampInEpochSeconds() {
        return currentTimeStampInstant().getEpochSecond();
    }

    public static Long convertDateToEpochTimeStampInSeconds(Date aDate) {
        if (NullSafeUtils.isAmNullOrEmpty(aDate)) {
            return null;
        }
        return aDate.toInstant().getEpochSecond();
    }

    public static Date convert24HourDateTo12HourDateFormat(String dateStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(CAMPAIGN_START_END_DATE_FORMAT);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(REPORT_DATE_FORMAT);
        LocalDateTime inputDate = LocalDateTime.parse(dateStr, inputFormatter);
        return stringToDateFormat(inputDate.format(outputFormatter),
                REPORT_DATE_FORMAT);

    }

    /*#################################### private methods ###################################*/
    private static DateTimeFormatter createFormatter(String format) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern(format))
                .toFormatter();
    }

    private static Instant currentTimeStampInstant() {
        return LocalDateTime.now().atZone(ZONE).toInstant();
    }

    public static long howManyDaysAgo(String dateInDbFormat) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(REPORT_DATE_FORMAT);
            LocalDate date1 = LocalDate.parse(dateInDbFormat, formatter);
            LocalDate date2 = LocalDate.now();

            return ChronoUnit.DAYS.between(date1, date2);
        } catch (Exception e) {
            log.error("Exception occurred while howManyDaysAgo find operation.", e);
        }
        return 0L;
    }
}
