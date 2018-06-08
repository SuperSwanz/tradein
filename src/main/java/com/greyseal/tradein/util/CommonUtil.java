package com.greyseal.tradein.util;

import java.net.URI;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CommonUtil {
    protected static final DecimalFormat DEC_FORMAT2 = new DecimalFormat(".##");
    protected static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("ddMyyyyHHmmss");

    public static String toURI(final String delimiter, final String... urls) {
        final String url = String.join(delimiter, urls);
        final URI uri = URI.create(url);
        return uri.toString();
    }

    public static long getRandom(final int length) {
        final Random random = new Random();
        final char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public static String getBSERandom() {
        return getDate(null);
    }

    public static LocalDateTime getDate() {
        return LocalDateTime.now();
    }

    public static String getDate(final DateTimeFormatter formatter) {
        return getDate().format(formatter != null ? formatter : DT_FORMATTER);
    }

    public static float formatFloat(final float f) {
        return Float.valueOf(DEC_FORMAT2.format(f));
    }

    public static float toFloat(final String toParse) {
        try {
            return formatFloat(Float.parseFloat(toParse.replace(",", "")));
        } catch (NumberFormatException nx) {
            nx.printStackTrace();
        }
        return 0;
    }

    public static int toInt(final String toParse) {
        try {
            return Integer.parseInt(toParse);
        } catch (NumberFormatException nx) {
            nx.printStackTrace();
        }
        return 0;
    }


    public static long toLong(final String toParse) {
        try {
            return Long.parseLong(toParse);
        } catch (NumberFormatException nx) {
            nx.printStackTrace();
        }
        return 0;
    }

}