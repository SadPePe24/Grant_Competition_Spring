package com.example.grant_competition_spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpertRatingResponse
{
    private Long applicationId;
    private Integer score;
}
