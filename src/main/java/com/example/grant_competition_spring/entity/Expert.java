package com.example.grant_competition_spring.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "experts")
@Data
public class Expert
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;
    private String lastname;
    private String directions; // Через запятую
    private String login;
    private String password;
}
