package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.ExpertRepository;
import com.example.grant_competition_spring.dto.request.ExpertRegisterRequest;
import com.example.grant_competition_spring.entity.Expert;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertService
{
    private final ExpertRepository expertRepository;

    public Expert register(ExpertRegisterRequest request)
    {
        if (expertRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new ApplicationException(ErrorCode.USER_ALREADY_EXISTS);
        }
        Expert expert = new Expert();
        expert.setFirstname(request.getFirstname());
        expert.setLastname(request.getLastname());
        expert.setDirections(request.getDirections());
        expert.setLogin(request.getLogin());
        expert.setPassword(request.getPassword());
        return expertRepository.save(expert);
    }

    public void delete(String login)
    {
        Expert expert = expertRepository.findByLogin(login)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        expertRepository.delete(expert);
    }

    public Expert login(String login, String password)
    {
        return expertRepository.findByLogin(login)
                .filter(e -> e.getPassword().equals(password))
                .orElseThrow(() ->(new RuntimeException("Неверный логин или пароль")));
    }
}
