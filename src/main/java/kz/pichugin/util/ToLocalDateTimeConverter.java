package kz.pichugin.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ToLocalDateTimeConverter {
    public static LocalDateTime convert(Timestamp dateToConvert) {
        return new Timestamp(dateToConvert.getTime()).toLocalDateTime();
    }
}