package com.example.grant_competition_spring.exception;

public class ApplicationException extends RuntimeException
{
    private final ErrorCode errorCode;

    public ApplicationException (ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getErrorCode()
    {
        return errorCode.getCode();
    }

    public String getErrorMassage() {
        return errorCode.getMessage();
    }
}
