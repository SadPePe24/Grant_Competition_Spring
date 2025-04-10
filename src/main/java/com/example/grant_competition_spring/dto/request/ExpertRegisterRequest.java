package com.example.grant_competition_spring.dto.request;

import lombok.Data;

@Data
public class ExpertRegisterRequest
{
    private String firstname;
    private String lastname;
    private String directions; // Через запятую
    private String login;
    private String password;
}
