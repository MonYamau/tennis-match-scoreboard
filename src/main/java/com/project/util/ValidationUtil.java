package com.project.util;

import com.project.exception.IncorrectInputException;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class ValidationUtil {
    private final int DEFAULT_UNIT = 1;
    private final int NAME_MAX_LENGTH = 40;
    private final Pattern RUSSIAN_NAME_PATTERN = Pattern.compile("^[а-яА-ЯёЁ ]+$");
    private final Pattern ENGLISH_NAME_PATTERN = Pattern.compile("^[a-zA-Z ]+$");

    public void validateNamesForUnique(String firstPlayerName, String secondPlayerName) {
        if (firstPlayerName.equalsIgnoreCase(secondPlayerName)) {
            throw new IncorrectInputException("Player can't play against themselves");
        }
    }

    public void validateName(String name) {
        validateParameter(name);
        if (name.strip().length() > NAME_MAX_LENGTH) {
            throw new IncorrectInputException("The name must be less than " + NAME_MAX_LENGTH + " characters");
        }
        if (!RUSSIAN_NAME_PATTERN.matcher(name).matches() && !ENGLISH_NAME_PATTERN.matcher(name).matches()) {
            throw new IncorrectInputException
                    ("Incorrect name format (only Latin or Cyrillic letters and spaces are allowed)");
        }
    }

    public void validateNaturalNumber(int value) {
        if (value < DEFAULT_UNIT) {
            throw new IncorrectInputException("Incorrect number format (a natural number is expected)");
        }
    }

    public void validateParameter(String parameter) {
        if (parameter == null) {
            throw new IncorrectInputException("The expected parameter is missing");
        }
        if (parameter.isBlank()) {
            throw new IncorrectInputException("The expected parameter is empty");
        }
    }
}
