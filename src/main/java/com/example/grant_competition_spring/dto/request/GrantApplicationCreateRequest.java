package com.example.grant_competition_spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class GrantApplicationCreateRequest
{
    @NotBlank(message = "Название заявки не должно быть пустым")
    private String title;

    @NotBlank(message = "Описание заявки не должно быть пустым")
    private String description;

    @NotEmpty(message = "Необходимо указать хотя бы одно направление")
    private List<@NotBlank(message = "Направление не должно быть пустым") String> directions;

    @NotNull(message = "Запрашиваемая сумма обязательна")
    @Positive(message = "Запрашиваемая сумма должна быть положительным числом")
    private Double requestedAmount;
}
