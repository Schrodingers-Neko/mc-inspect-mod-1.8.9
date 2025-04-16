package neko.McInspects.client.utils;

import java.util.regex.Pattern;

public class TextUtils {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)(§[0-9A-FK-ORZ])");
    private static final Pattern STRIP_SYMBOL_PATTERN = Pattern.compile("(?i)[^a-zA-Z0-9 \\p{Punct}]");

    public static String stripSymbolAndTrim(final String input) {
        return STRIP_SYMBOL_PATTERN.matcher(stripColorAndTrim(input)).replaceAll("").trim();
    }

    public static String stripColorAndTrim(final String input) {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("").trim();
    }
}
