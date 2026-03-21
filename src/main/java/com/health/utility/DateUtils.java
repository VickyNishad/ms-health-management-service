package com.health.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    public static LocalDate getNextOrSame(DayOfWeek dayOfWeek) {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(dayOfWeek));
    }

    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
}
