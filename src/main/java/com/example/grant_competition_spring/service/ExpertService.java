package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.AuthTokenRepository;
import com.example.grant_competition_spring.dao.ExpertRepository;
import com.example.grant_competition_spring.dto.request.ExpertRegisterRequest;
import com.example.grant_competition_spring.entity.Expert;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class ExpertService extends BaseAuthService<Expert>
{
    private final ExpertRepository expertRepository;
    private final RatingService ratingService;

    public ExpertService(ExpertRepository expertRepository, AuthTokenRepository authTokenRepository, RatingService ratingService)
    {
        super(authTokenRepository);
        this.expertRepository = expertRepository;
        this.ratingService = ratingService;
    }

    public Expert register(ExpertRegisterRequest request)
    {
        if (expertRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new ApplicationException(ErrorCode.USER_ALREADY_EXISTS);
        }
        Expert expert = new Expert();
        expert.setFirstName(request.getFirstName());
        expert.setLastName(request.getLastName());
        expert.setDirections(request.getDirections());
        expert.setLogin(request.getLogin());
        expert.setPassword(request.getPassword());
        return expertRepository.save(expert);
    }

    public String login(String login, String password)
    {
        Expert expert = expertRepository.findByLogin(login)
                .orElseThrow(() -> (new RuntimeException("Неверный логин или пароль")));
        return super.login(expert, password);
    }

    public void logout(String token)
    {
        super.logout(token);
    }


    public void delete(String token)
    {
        super.delete(token);
    }

    public void giveRating(String token, Long applicationId, int score)
    {
        ratingService.giveRating(token, applicationId, score);
    }



    @Override
    protected String getLogin(Expert user)
    {
        return user.getLogin();
    }

    @Override
    protected String getPassword(Expert user)
    {
        return user.getPassword();
    }

    @Override
    protected void deleteUserFromDb(String login)
    {
        Expert expert = expertRepository.findByLogin(login)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        expertRepository.delete(expert);
    }

    @Override
    protected String getUserType()
    {
        return "EXPERT";
    }
}
