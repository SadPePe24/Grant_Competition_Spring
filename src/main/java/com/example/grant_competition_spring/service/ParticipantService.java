package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.ParticipantRepository;
import com.example.grant_competition_spring.dto.request.ParticipantRegisterRequest;
import com.example.grant_competition_spring.entity.Participant;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantService
{
    private final ParticipantRepository participantRepository;

    public Participant registerParticipant(ParticipantRegisterRequest request)
    {
        if (participantRepository.findByLogin(request.getLogin()).isPresent())
        {
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

    public void delete(String login)
    {
        Participant participant = participantRepository.findByLogin(login)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        participantRepository.delete(participant);
    }

    public Participant login(String login, String password) {
        return participantRepository.findByLogin(login)
                .filter(p -> p.getPassword().equals(password))
                .orElseThrow(() ->(new RuntimeException("Неверный логин или пароль")));
    }
}
