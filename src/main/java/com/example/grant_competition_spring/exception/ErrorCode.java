package com.example.grant_competition_spring.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode
{
    INVALID_CREDENTIALS("Неверный логин или пароль"),
    INVALID_TOKEN("Неверный токен"),
    USER_ALREADY_EXISTS("Пользователь с таким логином уже существует"),
    USER_NOT_FOUND("Пользователь не найден"),
    ACCESS_DENIED("Доступ запрещён"),
    INVALID_APPLICATION("Неверный ID заявки"),
    ALREADY_RATED("Данный эксперт уже оценивал эту заявку"),
    INVALID_SCORE("Оценка должна быть от 1 до 5"),
    RATING_NOT_FOUND("Оценка не найдена");

    private final String message;
    }
