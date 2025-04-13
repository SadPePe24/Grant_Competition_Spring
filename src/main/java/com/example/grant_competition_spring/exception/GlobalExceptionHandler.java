package com.example.grant_competition_spring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.grant_competition_spring.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                e.getErrorCode().name(),
                e.getErrorCode().getMessage()
        ));
    }
}
