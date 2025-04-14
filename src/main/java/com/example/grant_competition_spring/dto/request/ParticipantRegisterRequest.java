package com.example.grant_competition_spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParticipantRegisterRequest
{
    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    @NotBlank(message = "Фамилия не должна быть пустой")
    private String lastName;

    @NotBlank(message = "Название компании не должно быть пустым")
    private String companyName;

    @NotBlank(message = "Логин не должен быть пустым")
    private String login;

    @NotBlank(message = "Пароль не должен быть пустым")
    // @Size(min = 6, message = "Пароль должен быть минимум 6 символов")
    private String password;
}
