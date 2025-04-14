package com.example.grant_competition_spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse
{
    private String error;
    private List<String> details;

    public ErrorResponse(String errorMassage)
    {
        this.error = errorMassage;
    }
}
