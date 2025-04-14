package com.example.grant_competition_spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


import java.util.List;

@Data
public class ExpertRegisterRequest
{
    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    @NotBlank(message = "Фамилия не должна быть пустой")
    private String lastName;

    @NotEmpty(message = "Направления не должны быть пустыми")
    private List<@NotBlank(message = "Направление не должно быть пустым") String> directions;

    @NotBlank(message = "Логин не должен быть пустым")
    private String login;

    @NotBlank(message = "Пароль не должен быть пустым")
    // @Size (min = ..., message = "Пароль должен состоять минимум из ... символов"
    private String password;
}
