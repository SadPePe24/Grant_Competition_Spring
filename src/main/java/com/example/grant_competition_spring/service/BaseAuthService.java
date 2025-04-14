package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.AuthTokenRepository;
import com.example.grant_competition_spring.entity.AuthToken;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class BaseAuthService<T>
{
    private final AuthTokenRepository authTokenRepository;

    protected abstract String getLogin(T user);

    protected abstract String getPassword(T user);

    protected abstract void deleteUserFromDb(String login);

    protected abstract String getUserType();

    public String login(T user, String password)
    {
        if (!getPassword(user).equals(password)) {
            throw new ApplicationException(ErrorCode.INVALID_CREDENTIALS);
        }
        authTokenRepository.findByLogin(getLogin(user))
                .ifPresent(authTokenRepository::delete);

        String token = UUID.randomUUID().toString();
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setLogin(getLogin(user));
        authToken.setUserType(getUserType());
        authTokenRepository.save(authToken);
        return token;
    }

    public void logout(String token)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));
                authTokenRepository.delete(authToken);
    }

    @Transactional
    public void delete(String token)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        deleteRelatedEntities(authToken.getLogin());
        deleteUserFromDb(authToken.getLogin());
        authTokenRepository.delete(authToken);
    }

    protected void deleteRelatedEntities(String token) {

    }
}
