package com.example.itunessearch.model;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    public static final String DATE_FORMAT_8 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private SimpleDateFormat localDateFormat;
    private static DateUtils instance;

    private DateUtils() {
        // do nothing
    }

    public static DateUtils getInstance() {
        if (instance == null) {
            instance = new DateUtils();
        }
        return instance;
    }

    @Nullable
    public Date convertUTCDateStringToDateObject(String utcDateInString, String currentDateFormat) {
        Date date = null;
        try {
            SimpleDateFormat utcDateFormat = getLocalDateFormatter(currentDateFormat);
            date = utcDateFormat.parse(utcDateInString);
        } catch (Exception e) {
            // do nothing
        }
        return date;
    }

    private SimpleDateFormat getLocalDateFormatter(String format) {
        if (localDateFormat == null || !format.equals(localDateFormat.toPattern())) {
            localDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            localDateFormat.setTimeZone(TimeZone.getDefault());
        }
        return localDateFormat;
    }
}
