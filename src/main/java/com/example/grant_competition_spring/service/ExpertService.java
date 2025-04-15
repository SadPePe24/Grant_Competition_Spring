package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.AuthTokenRepository;
import com.example.grant_competition_spring.dao.ExpertRepository;
import com.example.grant_competition_spring.dao.GrantApplicationRepository;
import com.example.grant_competition_spring.dao.RatingRepository;
import com.example.grant_competition_spring.dto.request.ExpertRegisterRequest;
import com.example.grant_competition_spring.dto.request.RatingRequest;
import com.example.grant_competition_spring.dto.response.ExpertRatingResponse;
import com.example.grant_competition_spring.entity.AuthToken;
import com.example.grant_competition_spring.entity.Expert;
import com.example.grant_competition_spring.entity.GrantApplication;
import com.example.grant_competition_spring.entity.Rating;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ExpertService extends BaseAuthService<Expert>
{
    private final ExpertRepository expertRepository;
    private final RatingRepository ratingRepository;
    private final GrantApplicationRepository grantApplicationRepository;
    private final AuthTokenRepository authTokenRepository;


    public ExpertService(ExpertRepository expertRepository,
                         AuthTokenRepository authTokenRepository,
                         RatingRepository ratingRepository,
                         GrantApplicationRepository grantApplicationRepository, AuthTokenRepository authTokenRepository1)
    {
        super(authTokenRepository);
        this.expertRepository = expertRepository;
        this.ratingRepository = ratingRepository;
        this.grantApplicationRepository = grantApplicationRepository;
        this.authTokenRepository = authTokenRepository1;
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
                .orElseThrow(() -> (new ApplicationException(ErrorCode.INVALID_CREDENTIALS)));
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

    public void giveRating(String token, RatingRequest request)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        if (!authToken.getUserType().equals("EXPERT")) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        Expert expert = expertRepository.findByLogin(authToken.getLogin())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        GrantApplication grantApplication = grantApplicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_APPLICATION));

        boolean isCompetent = grantApplication.getDirections().stream()
                .anyMatch(expert.getDirections()::contains);
        if (!isCompetent) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        Optional<Rating> existingRating = ratingRepository.findByExpertIdAndApplicationId(
                expert.getId(), request.getApplicationId());

        Rating rating = existingRating.orElseGet(Rating::new);
        rating.setExpert(expert);
        rating.setApplication(grantApplication);
        rating.setScore(request.getScore());
        ratingRepository.save(rating);

        updateApplicationRating(grantApplication);
    }

    private void updateApplicationRating(GrantApplication grantApplication)
    {
        List<Rating> ratings = ratingRepository.findAll()
                .stream()
                .filter(rating -> rating.getApplication().getId() == (grantApplication.getId()))
                .toList();

        double average = ratings.stream()
                .mapToInt(Rating::getScore)
                .average().orElse(0.0);

        BigDecimal rounded = BigDecimal.valueOf(average).setScale(2, BigDecimal.ROUND_HALF_UP);
        grantApplication.setRating(rounded.doubleValue());
        grantApplicationRepository.save(grantApplication);
    }

    public void deleteRating(String token, Long applicationId)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        if (!authToken.getUserType().equals("EXPERT")) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        Expert expert = expertRepository.findByLogin(authToken.getLogin())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        GrantApplication application = grantApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_APPLICATION));

        Rating rating = ratingRepository.findByExpertIdAndApplicationId(expert.getId(), application.getId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.RATING_NOT_FOUND));

        ratingRepository.delete(rating);
        updateApplicationRating(application);
    }

    public List<ExpertRatingResponse> getAllRatings(String token)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        if (!authToken.getUserType().equals("EXPERT")) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        Expert expert = expertRepository.findByLogin(authToken.getLogin())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        List<Rating> ratings = ratingRepository.findByExpertId(expert.getId());

        return ratings.stream()
                .map(r -> new ExpertRatingResponse(r.getApplication().getId(), r.getScore()))
                .toList();
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

        List<Rating> ratings = expert.getRatings();

        Set<GrantApplication> affectedApplications = ratings.stream()
                        .map(Rating::getApplication)
                        .collect(Collectors.toSet());

        expertRepository.delete(expert);
        for (GrantApplication app : affectedApplications) {
            updateApplicationRating(app);
        }
    }

    @Override
    protected String getUserType()
    {
        return "EXPERT";
    }
}
