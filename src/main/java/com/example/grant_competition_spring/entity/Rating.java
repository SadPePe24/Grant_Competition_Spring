package com.example.grant_competition_spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Table(name = "ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"expert_id", "application_id"})
})
@Data
public class Rating
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rating;

    @ManyToOne
    @JoinColumn(name = "expert_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rating_expert"))
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rating_application"))
    @JsonBackReference
    private GrantApplication application;

    @Column(nullable = false)
    @Min(value = 1, message = "Оценка должна быть не меньше 1")
    @Max(value = 5, message = "Оценка должна быть не больше 5")
    private Integer score;
}
