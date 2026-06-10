package com.project.util;

import com.project.exception.IncorrectInputException;

import java.util.regex.Pattern;

public final class ValidationUtil {
    private static final int NAME_MAX_LENGTH = 40;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ ]+$");

    private ValidationUtil() {
    }

    public static void validateNamesForUnique(String firstPlayerName, String secondPlayerName) {
        if (firstPlayerName.equalsIgnoreCase(secondPlayerName)) {
            throw new IncorrectInputException("Player can't play against themselves");
        }
    }

    public static void validateName(String name) {
        validateParameter(name);
        if (name.strip().length() > NAME_MAX_LENGTH) {
            throw new IncorrectInputException("The name must be less than " + NAME_MAX_LENGTH + " characters");
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IncorrectInputException
                    ("Incorrect name format (only Latin and Cyrillic letters and spaces are allowed)");
        }
    }

    public static void validatePage(int page, int defaultPage) {
        if (page < defaultPage) {
            throw new IncorrectInputException("Incorrect number format (a natural number is expected)");
        }
    }

    public static void validateParameter(String parameter) {
        if (parameter == null) {
            throw new IncorrectInputException("The expected parameter is missing");
        }
        if (parameter.isBlank()) {
            throw new IncorrectInputException("The expected parameter is empty");
        }
    }
}
