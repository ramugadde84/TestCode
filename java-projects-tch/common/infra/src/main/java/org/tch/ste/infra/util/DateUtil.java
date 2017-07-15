/**
 * 
 */
package org.tch.ste.infra.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tch.ste.infra.constant.InfraConstants;

/**
 * Utility for Date Management.
 * 
 * @author Karthik.
 * 
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static final String UTC_TIME = "UTC";

    /**
     * Overriden private constructor.
     */
    private DateUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Fetches the current date with the time set as midnight.
     * 
     * @return Date - Thanks Stackoverflow.
     */
    public static Date getCurrentDate() {
        Date date = new Date(); // timestamp now
        Calendar cal = Calendar.getInstance(); // get calendar instance
        cal.setTime(date); // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
        cal.set(Calendar.MINUTE, 0); // set minute in hour
        cal.set(Calendar.SECOND, 0); // set second in minute
        cal.set(Calendar.MILLISECOND, 0); // set millis in second
        return cal.getTime();
    }

    /**
     * Fetches the current time.
     * 
     * @return Date - The current time.
     */
    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Returns the time in UTC.
     * 
     * @return Date - The utc time.
     */
    public static Date getUtcTime() {
        SimpleDateFormat utcFmt = new SimpleDateFormat(InfraConstants.STD_TIMESTAMP_FMT);
        utcFmt.setTimeZone(TimeZone.getTimeZone(UTC_TIME));
        SimpleDateFormat localFmt = new SimpleDateFormat(InfraConstants.STD_TIMESTAMP_FMT);
        try {
            return localFmt.parse(utcFmt.format(getCurrentTime()));
        } catch (ParseException e) {
            logger.warn("Error while attempting to fetch time in UTC", e);
        }
        return null;
    }

    /**
     * Format the date to a string.
     * 
     * @param date
     *            Date - The date.
     * @param format
     *            String - The format.
     * @return String - The formatted date.
     */
    public static String formatDate(Date date, String format) {
        if (date != null) {
            DateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Validates if the format is that of a timestamp YYYYMMDDHHMMSS.
     * 
     * 
     * @param timestamp
     *            String - The time.
     * @return boolean - True if matches the format.
     */
    public static boolean validateTimestampFormat(String timestamp) {
        boolean retVal = true;
        try {
            DateFormat formatter = new SimpleDateFormat(InfraConstants.STD_TIMESTAMP_FMT);
            formatter.setLenient(false);
            formatter.parse(timestamp);
        } catch (Throwable t) {
            retVal = false;
        }
        return retVal;
    }

    /**
     * Adds the number of days to the given date.
     * 
     * @param date
     *            Date - The date.
     * @param numDays
     *            int - The number of days.
     * @return Date - The adjusted date.
     */
    public static Date add(Date date, int numDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numDays);
        return calendar.getTime();
    }

    /**
     * Returns the current timestamp in the format YYYYMMDDHHMMSS.
     * 
     * @return String - The above timestamp string.
     */
    public static String getCurrentTimestamp() {
        return formatDate(getUtcTime(), InfraConstants.STD_TIMESTAMP_FMT);
    }

    /**
     * Checks if something is a valid month year in MMYY format.
     * 
     * @param date
     *            String - The date.
     * @return boolean - True or False.
     */
    public static boolean isValidMonthYear(String date) {
        boolean retVal = true;
        try {
            DateFormat formatter = new SimpleDateFormat(InfraConstants.STD_MM_YY_FORMAT);
            formatter.setLenient(false);
            formatter.parse(date);
        } catch (Throwable t) {
            retVal = false;
        }
        return retVal;
    }

    /**
     * Fetches Month & Year.
     * 
     * @param date
     *            String - The date.
     * @return String - The month year.
     */
    public static String getMonthYear(Date date) {
        DateFormat formatter = new SimpleDateFormat(InfraConstants.STD_MM_YY_FORMAT);
        return formatter.format(date);
    }

    /**
     * This method will add minutes or it will deduct minutes from time.
     * @param date - date .
     * @param minutes - minutes.
     * @return the date.
     */
    public static Date subtractTimeInMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.MINUTE, -minutes);
        Date changedDate = calendar.getTime();
        return changedDate;
    }

    /**
     * Returns the start time of Epoch.
     * 
     * @return Date - The epoch time.
     */
    public static Date getEpochStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        return calendar.getTime();
    }
}
