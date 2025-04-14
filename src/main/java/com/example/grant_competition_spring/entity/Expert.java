package com.example.grant_competition_spring.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "experts")
@Data
public class Expert
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ElementCollection
    @CollectionTable(name = "expert_directions", joinColumns = @JoinColumn(name = "expert_id"))
    @Column(name = "direction")
    private List<String> directions; // Через запятую

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
