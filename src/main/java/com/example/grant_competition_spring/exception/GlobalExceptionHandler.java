package com.example.grant_competition_spring.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.grant_competition_spring.dto.response.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e)
    {
        HttpStatus status = mapErrorCodeToStatus(e.getErrorCode());
        return ResponseEntity.status(status).body(new ErrorResponse(e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(new ErrorResponse(null, errorMessages));
    }



    private HttpStatus mapErrorCodeToStatus(ErrorCode errorCode)
    {
        return switch (errorCode) {
            case INVALID_CREDENTIALS, INVALID_TOKEN -> HttpStatus.UNAUTHORIZED; //401
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND; // 404
            case USER_ALREADY_EXISTS -> HttpStatus.CONFLICT; //409
            case INVALID_SCORE -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.BAD_REQUEST; //400
        };
    }
}
