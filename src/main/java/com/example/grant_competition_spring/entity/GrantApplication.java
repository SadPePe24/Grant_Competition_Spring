package com.example.grant_competition_spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Participant participant;

    @Column(name = "rating")
    private double rating = 0.0;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rating> ratings;

    @Column(name = "approved")
    private boolean approved = false;
}
