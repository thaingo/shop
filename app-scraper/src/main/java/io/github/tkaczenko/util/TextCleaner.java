package io.github.tkaczenko.util;

public final class TextCleaner {
    public static String cleanText(String text) {
        return removeIncorrectWhitespace(normalizeWhitespaceCharacters(text))
                .trim();
    }

    public static String normalizeUid(String uid) {
        return uid
                .replace(String.valueOf((char) 160), " ")
                .replaceAll("\\s+", "")
                .replace("/", "_")
                .replace("?", "")
                .replace("#", "")
                .replace("&", "")
                .replace("%", "")
                .replace("\"", "")
                .replace("+", "")
                .replace("*", "")
                .replace("|", "");
    }

    public static String normalizeWhitespaceCharacters(String text) {
        return text.replace(String.valueOf((char) 160), " ");
    }

    public static String removeIncorrectWhitespace(String text) {
        return text.replaceAll("\\u00a0", "");
    }
}
