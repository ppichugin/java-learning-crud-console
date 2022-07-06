package kz.pichugin.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToLocalDateTimeConverter {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static LocalDateTime convert(Timestamp dateToConvert) {
        return dateToConvert != null ? new Timestamp(dateToConvert.getTime()).toLocalDateTime() : null;
    }
}