package com.example.grant_competition_spring.server;

import com.example.grant_competition_spring.dto.response.FinalizationResultResponse;
import com.example.grant_competition_spring.entity.GrantApplication;
import com.example.grant_competition_spring.service.FinalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finalization")
@RequiredArgsConstructor
public class FinalizationController
{
    private final FinalizationService finalizationService;

    @PostMapping("/run")
    public ResponseEntity<FinalizationResultResponse> finalize(@RequestParam double fund,
                                                               @RequestParam double minRating) {
        FinalizationResultResponse result = finalizationService.finalizeGrants(fund, minRating);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.ok(Map.of("finalized", finalizationService.isFinalizationComplete()));
    }
}
