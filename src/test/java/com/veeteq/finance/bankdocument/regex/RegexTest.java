package com.veeteq.finance.bankdocument.regex;

import com.veeteq.finance.bankdocument.util.DateUtil;
import com.veeteq.finance.bankdocument.util.NumberUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
  private final String dateRegex = "(\\d{4}-\\d{2}-\\d{2})";
  private final Pattern pattern = Pattern.compile(dateRegex);

  @Test
  public void testDateRegex01() {
    String strToTest = "Elektroniczne zestawienie operacji za okres od 2023-06-01 do 2023-06-30";
    Matcher matcher = pattern.matcher(strToTest);

    int i = 0;
    while (matcher.find()) {
      System.out.println(String.format("----- Iteration %d -----", ++i));

      System.out.println("Full match: " + matcher.group(0));
      System.out.println("Group count: " + matcher.groupCount());
      System.out.println("Group 1: " + matcher.group(1));
    }
  }

  @Test
  public void testDateRegex02() {
    String strToTest = "Elektroniczne zestawienie operacji za okres od 2023-06-01 do 2023-06-30";
    Matcher matcher = pattern.matcher(strToTest);

    if (matcher.find()) {} //skip first occurence
    if (matcher.find()) {
      LocalDate date = DateUtil.parse(matcher.group(1));
      System.out.println(date);
    }
  }

  @Test
  public void testMonetartyAmount() {
    String strToTest = "43 028,90 PLN";
    BigDecimal result = NumberUtil.convertTo(strToTest, ',', ' ', "-");
    System.out.println(result);
  }
}
