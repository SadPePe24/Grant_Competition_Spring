package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.AuthTokenRepository;
import com.example.grant_competition_spring.dao.ExpertRepository;
import com.example.grant_competition_spring.dao.GrantApplicationRepository;
import com.example.grant_competition_spring.dao.ParticipantRepository;
import com.example.grant_competition_spring.dto.request.GrantApplicationCreateRequest;
import com.example.grant_competition_spring.entity.AuthToken;
import com.example.grant_competition_spring.entity.Expert;
import com.example.grant_competition_spring.entity.GrantApplication;
import com.example.grant_competition_spring.entity.Participant;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EqualsAndHashCode
public class GrantApplicationService
{
    private final GrantApplicationRepository grantApplicationRepository;
    private final AuthTokenRepository authTokenRepository;
    private final ParticipantRepository participantRepository;
    private final ExpertRepository expertRepository;

    public GrantApplication createApplication(String token, GrantApplicationCreateRequest request)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        if (!authToken.getUserType().equals("PARTICIPANT")) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        Participant participant = participantRepository.findByLogin(authToken.getLogin())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        GrantApplication grantApplication = new GrantApplication();
        grantApplication.setTitle(request.getTitle());
        grantApplication.setDescription(request.getDescription());
        grantApplication.setDirections(request.getDirections());
        grantApplication.setRequestAmount(request.getRequestedAmount());
        grantApplication.setParticipant(participant);

        return grantApplicationRepository.save(grantApplication);
    }

    public void deleteApplication(String token, Long applicationId)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        if (!authToken.getUserType().equals("PARTICIPANT")) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        GrantApplication grantApplication = grantApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_APPLICATION));

        Participant participant = participantRepository.findByLogin(authToken.getLogin())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (grantApplication.getParticipant().getId() != (participant.getId())) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        grantApplicationRepository.delete(grantApplication);
    }

    public List<GrantApplication> getMyApplications(String token) {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        if (!authToken.getUserType().equals("PARTICIPANT")) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        Participant participant = participantRepository.findByLogin(authToken.getLogin())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        return grantApplicationRepository.findByParticipant(participant);
    }

    public List<GrantApplication> getApplicationsForExpert(String token)
    {
        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_TOKEN));

        if (!authToken.getUserType().equals("EXPERT")) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        Expert expert = expertRepository.findByLogin(authToken.getLogin())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        List<String> expertDirections = expert.getDirections();
        List<GrantApplication> allApplications = grantApplicationRepository.findAll();

        return allApplications.stream()
                .filter(app -> app.getDirections().stream()
                        .anyMatch(expertDirections::contains))
                .toList();
    }
}
