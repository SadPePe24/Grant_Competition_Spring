package com.example.grant_competition_spring.dao;

import com.example.grant_competition_spring.entity.GrantApplication;
import com.example.grant_competition_spring.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrantApplicationRepository extends JpaRepository<GrantApplication, Long>
{
    List<GrantApplication> findByParticipantLogin(String participantLogin);

    List<GrantApplication> findByParticipant(Participant participant);
}

