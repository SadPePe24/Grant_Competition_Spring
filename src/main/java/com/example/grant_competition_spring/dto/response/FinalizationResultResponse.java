package com.example.grant_competition_spring.dto.response;

import com.example.grant_competition_spring.entity.GrantApplication;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FinalizationResultResponse
{
    private double remainingFund;
    private List<GrantApplication> fundedApplications;
}
