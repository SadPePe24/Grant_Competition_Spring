package com.example.grant_competition_spring.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest
{
    @NotNull(message = "ID заявки обязателен")
    private Long applicationId; // ID заявки, которую оцениваем

    @NotNull(message = "Оценка обязательна")
    @Min(value = 1, message = "Оценка должна быть минимум 1")
    @Max(value = 5, message = "Оценка должна быть максимум 5")
    private Integer score; // Оценка от 1 до 5
}
