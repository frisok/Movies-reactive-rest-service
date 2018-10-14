package nl.reactivemoviesrest.util;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * Util class for parsing and formatting Dates
 */

public class MoviesDateUtil {


    public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

    private MoviesDateUtil() {
    }


    /**
     * @param input String to be parsed
     * @return a Date corresponding to the input String if to the input String is parsible, else null,
     */
    public static final Date parse(final String input) {

        Date output = null;

        try {
            final String cleanedInput = input.contains("+") ? input.substring(0, input.indexOf("+")) : input;
            output = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS).parse(cleanedInput);
        } catch (final Exception e) {
        }

        return output;
    }

    /**
     * @param input Date to be formatted
     * @return Formatted string if input Date is not null, else empty String
     */
    public static final String format(final Date input) {
        return input == null ? "" : DateFormatUtils.format(input, YYYY_MM_DD_T_HH_MM_SS);
    }

    public static final String validateAndClearOfTimezone(final String input) {
        return format(parse(input));
    }

    /**
     *
     * @param input the date in the system';s timezone
     * @return nul if the input Date is null, else the ISO Date
     */
    public static Date convertToISODate(final Date input) {

        Date isoDate = null;

        if (input != null) {
            try {
                isoDate = ISO8601Utils.parse(ISO8601Utils.format(new Date()), new ParsePosition(0));
            } catch (ParseException e) {
                isoDate = null;
            }
        }

        return isoDate;
    }

}