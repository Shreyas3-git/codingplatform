package com.crio.codingplateform.utils;

public enum ErrorCode
{
    USER_NOT_FOUND("User with ID '%s' not found."),
    USER_ALREADY_EXISTS("User with ID '%s' already exists."),
    INVALID_SCORE("Invalid score for user with ID '%s'.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage(long userId) {
        return String.format(message, userId);
    }
}
