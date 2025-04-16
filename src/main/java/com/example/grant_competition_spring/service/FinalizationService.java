package com.example.grant_competition_spring.service;

import com.example.grant_competition_spring.dao.GrantApplicationRepository;
import com.example.grant_competition_spring.dto.response.FinalizationResultResponse;
import com.example.grant_competition_spring.entity.GrantApplication;
import com.example.grant_competition_spring.exception.ApplicationException;
import com.example.grant_competition_spring.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FinalizationService
{
    private final GrantApplicationRepository grantApplicationRepository;

    private boolean isFinalized = false;

    public FinalizationResultResponse finalizeGrants(double totalFund, double minRating) {
        List<GrantApplication> allApplications = grantApplicationRepository.findAll();


        List<GrantApplication> eligible = allApplications.stream()
                .filter(app -> app.getRating() >= minRating)
                .toList();

        Random random = new Random();
        eligible = eligible.stream()
                .sorted(Comparator
                        .comparingDouble(GrantApplication::getRating).reversed()
                        .thenComparingDouble(GrantApplication::getRequestAmount)
                        .thenComparing(a -> random.nextInt()))
                .toList();

        double remainingFund = totalFund;
        for (GrantApplication app : eligible) {
            if (app.getRequestAmount() <= remainingFund) {
                app.setApproved(true); // если есть флаг
                remainingFund -= app.getRequestAmount();
            } else {
                app.setApproved(false); // отказано
            }
            grantApplicationRepository.save(app);
        }

        // Вернуть только те, кто получил финансирование
        List<GrantApplication> funded = eligible.stream()
                .filter(GrantApplication::isApproved)
                .toList();

        isFinalized = true;
        return new FinalizationResultResponse(remainingFund, funded);
    }

    public boolean isFinalizationComplete() {
        return isFinalized;
    }
}
