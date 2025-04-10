package com.example.grant_competition_spring.dao;

import com.example.grant_competition_spring.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert, Long>
{
    Optional<Expert> findByLogin(String login);
}
