package nl.reactivemoviesrest.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

}