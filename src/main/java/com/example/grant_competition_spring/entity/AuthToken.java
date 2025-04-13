package com.example.grant_competition_spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tokens")
@Data
public class AuthToken
{
    @Id
    private String token;

    private String login;
    private String userType;
}
