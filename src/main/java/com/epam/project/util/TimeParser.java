package com.epam.project.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Parse Time from JSP DateTime-local to TimeParser
 */
public class TimeParser {


    public static String parseTimeFromJsp(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
