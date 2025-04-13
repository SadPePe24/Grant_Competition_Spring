package com.example.grant_competition_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.grant_competition_spring.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        HttpStatus status = mapErrorCodeToStatus(e.getErrorCode());
        return ResponseEntity.status(status).body(new ErrorResponse(e.getErrorCode().getMessage()));
    }

    private HttpStatus mapErrorCodeToStatus(ErrorCode errorCode)
    {
        return switch (errorCode) {
            case INVALID_CREDENTIALS, INVALID_TOKEN -> HttpStatus.UNAUTHORIZED; //401
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND; // 404
            case USER_ALREADY_EXISTS -> HttpStatus.CONFLICT; //409
            default -> HttpStatus.BAD_REQUEST; //400
        };
    }
}
