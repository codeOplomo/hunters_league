package org.anas.hunters_league.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompetitionCodeGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String generateCode(String location, LocalDateTime date) {
        return location + "-" + date.format(DATE_FORMATTER);
    }
}