package com.example.grant_competition_spring.dao;

import com.example.grant_competition_spring.entity.AuthToken;
import org.antlr.v4.runtime.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, String>
{
    Optional<AuthToken> findByToken(String token);

    Optional<AuthToken> findByLogin(String login);
}
