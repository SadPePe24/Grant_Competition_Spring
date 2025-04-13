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

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "directions")
    private String directions; // Через запятую

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
