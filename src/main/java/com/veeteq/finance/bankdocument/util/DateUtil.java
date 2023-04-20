package com.veeteq.finance.bankdocument.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DateUtil {
    private static final String DATE_PATTERN_A = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER_A = DateTimeFormatter.ofPattern(DATE_PATTERN_A);
    
    
    private static final String DATE_PATTERN_B = "d.MM.yyyy";
    private static final DateTimeFormatter DATE_FORMATTER_B = DateTimeFormatter.ofPattern(DATE_PATTERN_B);

    private static Set<DateTimeFormatter> formatters = new HashSet<DateTimeFormatter>(Arrays.asList(DATE_FORMATTER_A, DATE_FORMATTER_B));
    
    public static LocalDate parse(String dateString) {

      Iterator<DateTimeFormatter> iterator = formatters.iterator();
      while (iterator.hasNext()) {
        DateTimeFormatter formatter = iterator.next();
        try {
          return formatter.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
          continue;
        }
      }
      return null;
    }

    public static String format(LocalDate localDate) {
        if (localDate == null)
            return null;
        return DATE_FORMATTER_A.format(localDate);
    }

}
