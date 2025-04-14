package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.AuthTokenRepository;
import com.example.grant_competition_spring.dao.GrantApplicationRepository;
import com.example.grant_competition_spring.dao.ParticipantRepository;
import com.example.grant_competition_spring.dto.request.ParticipantRegisterRequest;
import com.example.grant_competition_spring.entity.GrantApplication;
import com.example.grant_competition_spring.entity.Participant;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class ParticipantService extends BaseAuthService<Participant>
{
    private final ParticipantRepository participantRepository;
    private final GrantApplicationRepository grantApplicationRepository;

    public ParticipantService(ParticipantRepository participantRepository, GrantApplicationRepository grantApplicationRepository , AuthTokenRepository authTokenRepository)
    {
        super(authTokenRepository);
        this.participantRepository = participantRepository;
        this.grantApplicationRepository = grantApplicationRepository;
    }

    public Participant registerParticipant(ParticipantRegisterRequest request)
    {
        if (participantRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new ApplicationException(ErrorCode.USER_ALREADY_EXISTS);
        }
        Participant participant = new Participant();
        participant.setFirstName(request.getFirstName());
        participant.setLastName(request.getLastName());
        participant.setCompanyName(request.getCompanyName());
        participant.setLogin(request.getLogin());
        participant.setPassword(request.getPassword());
        return participantRepository.save(participant);
    }

    public String login(String login, String password)
    {
        Participant participant = participantRepository.findByLogin(login)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        return super.login(participant, password);
    }

    public void logout(String token)
    {
        super.logout(token);
    }


    public void delete(String token)
    {
        super.delete(token);
    }

    @Override
    protected String getLogin(Participant user)
    {
        return user.getLogin();
    }

    @Override
    protected String getPassword(Participant user)
    {
        return user.getPassword();
    }

    @Override
    protected void deleteUserFromDb(String login)
    {
        Participant participant = participantRepository.findByLogin(login)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        participantRepository.delete(participant);
    }

    @Override
    protected String getUserType()
    {
        return "PARTICIPANT";
    }

    @Override
    protected void deleteRelatedEntities(String token)
    {
        List<GrantApplication> applications = grantApplicationRepository.findByParticipantLogin(token);
        grantApplicationRepository.deleteAll(applications);
    }
}
