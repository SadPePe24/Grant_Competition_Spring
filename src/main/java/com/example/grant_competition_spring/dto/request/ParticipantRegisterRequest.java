package com.example.grant_competition_spring.dto.request;

import lombok.Data;

@Data
public class ParticipantRegisterRequest
{
    private String firstName;
    private String lastName;
    private String companyName;
    private String login;
    private String password;
}
