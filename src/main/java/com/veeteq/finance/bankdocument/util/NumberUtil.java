package com.veeteq.finance.bankdocument.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberUtil {
    private final static Locale locale = new Locale("pl","PL");
    
    public static BigDecimal convertTo(String amountTxt) {
        try {
            amountTxt = amountTxt.replaceAll("\\s?", "");
            
            DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(locale);
            decimalSymbols.setDecimalSeparator(',');
            decimalSymbols.setGroupingSeparator('.'); 

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(locale);
            formatter.setNegativePrefix("­");
            formatter.setDecimalFormatSymbols(decimalSymbols);

            Number amount = formatter.parse(amountTxt.trim());
            return new BigDecimal(amount.doubleValue()).setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigDecimal convertTo(String amountTxt, char decimalSeparator, char groupingSeparator, String negativePrefix) {
        try {
            DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(locale);
            decimalSymbols.setDecimalSeparator(decimalSeparator);
            decimalSymbols.setGroupingSeparator(groupingSeparator); 

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(locale);
            formatter.setNegativePrefix(negativePrefix);
            formatter.setDecimalFormatSymbols(decimalSymbols);

            Number amount = formatter.parse(amountTxt.trim());
            return new BigDecimal(amount.doubleValue()).setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
