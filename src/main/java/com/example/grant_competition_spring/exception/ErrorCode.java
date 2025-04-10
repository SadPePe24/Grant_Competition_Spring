package com.example.grant_competition_spring.exception;

public enum ErrorCode
{
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found with the provided login"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid login or password"),
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "An unexpected error occurred"),
    USER_ALREADY_LOGGED_IN("USER_ALREADY_LOGGED_IN", "User already logged in"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists"),
    WRONG_JSON("WRONG_JSON", "Wrong JSON format"),
    INVALID_INPUT("INVALID_INPUT", "Invalid input"),
    GRANT_APPLICATION_NOT_FOUND("GRANT_APPLICATION_NOT_FOUND", "Grant application not found"),
    ACCESS_DENIED("ACCESS_DENIED", "Access denied"),
    APPLICATION_NOT_FOUND("APPLICATION_NOT_FOUND", "Application not found"),
    RATING_NOT_FOUND("RATING_NOT_FOUND", "Rating not found");

    private final String code;
    private final String message;

    ErrorCode(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public String getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }
    }
