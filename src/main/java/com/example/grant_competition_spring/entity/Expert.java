package com.example.grant_competition_spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String login;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rating> ratings;

}
