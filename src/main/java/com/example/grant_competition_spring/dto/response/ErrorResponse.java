package com.example.grant_competition_spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse
{
    private String code;
    private String message;
}
