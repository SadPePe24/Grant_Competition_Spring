package com.example.grant_competition_spring.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "applications")
@Data
public class GrantApplication
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "application_directions", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "direction")
    private List<String> directions;

    @Column(name = "request_amount")
    private double requestAmount;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false, foreignKey = @ForeignKey(name = "fk_applications_participant"))
    private Participant participant;

    @Column(name = "rating")
    private double rating = 0.0;
}
