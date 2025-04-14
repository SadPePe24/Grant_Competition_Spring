package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.AuthTokenRepository;
import com.example.grant_competition_spring.dao.ExpertRepository;
import com.example.grant_competition_spring.dao.GrantApplicationRepository;
import com.example.grant_competition_spring.dao.RatingRepository;
import com.example.grant_competition_spring.dto.request.RatingRequest;
import com.example.grant_competition_spring.entity.AuthToken;
import com.example.grant_competition_spring.entity.Expert;
import com.example.grant_competition_spring.entity.GrantApplication;
import com.example.grant_competition_spring.entity.Rating;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService
{
    private final RatingRepository ratingRepository;
    private final AuthTokenRepository authTokenRepository;
    private final ExpertRepository expertRepository;
    private final GrantApplicationRepository grantApplicationRepository;

    public void giveRating(String token, Long applicationId, int score)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        if (!authToken.getUserType().equals("EXPERT")) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        Expert expert = expertRepository.findByLogin(authToken.getLogin())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        GrantApplication grantApplication = grantApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_APPLICATION));

        boolean isCompetent = grantApplication.getDirections().stream()
                .anyMatch(expert.getDirections()::contains);
        if (!isCompetent) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        ratingRepository.findByExpertIdAndApplicationId(expert.getId(), grantApplication.getId())
                .ifPresent(rating -> {
                    throw new ApplicationException(ErrorCode.ALREADY_RATED);
                });

        Rating rating = new Rating();
        rating.setExpert(expert);
        rating.setExpert(expert);
        rating.setApplication(grantApplication);
        rating.setScore(score);
        ratingRepository.save(rating);

        updateApplicationRating(grantApplication);
    }

    private void updateApplicationRating(GrantApplication grantApplication) {
        List<Rating> ratings = ratingRepository.findAll()
                .stream().filter(rating -> rating.getApplication().getId() == grantApplication.getId())
                .toList();

        double average = ratings.stream()
                .mapToInt(Rating::getScore)
                .average().orElse(0.0);

        grantApplication.setRating(average);
        grantApplicationRepository.save(grantApplication);
    }

}
