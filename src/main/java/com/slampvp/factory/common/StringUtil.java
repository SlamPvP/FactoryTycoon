package com.slampvp.factory.common;

import java.util.Collection;

public class StringUtil {
    public static <T extends Collection<? super String>> T copyPartialMatches(String token, Iterable<String> originals, T collection)
            throws UnsupportedOperationException, IllegalArgumentException {
        if (token.length() == 1 && Integer.toHexString(token.charAt(0) | 0x10000).substring(1).equals("0000")) {
            token = "";
        }

        for (String string : originals) {
            if (startsWithIgnoreCase(string, token)) {
                collection.add(string);
            }
        }
        return collection;
    }

    private static boolean startsWithIgnoreCase(String string, String prefix) throws IllegalArgumentException, NullPointerException {
        return string.length() >= prefix.length() && string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}
