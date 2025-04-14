package com.example.grant_competition_spring.dao;

import com.example.grant_competition_spring.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long>
{
    Optional<Rating> findByExpertIdAndApplicationId(Long expertId, Long applicationId);
}
