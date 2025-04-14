package com.example.grant_competition_spring.server;

import com.example.grant_competition_spring.dto.request.GrantApplicationCreateRequest;
import com.example.grant_competition_spring.dto.response.SuccessResponse;
import com.example.grant_competition_spring.entity.GrantApplication;
import com.example.grant_competition_spring.service.GrantApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class GrantApplicationController
{
    private final GrantApplicationService grantApplicationService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createApplication(@RequestParam String token, @RequestBody @Valid GrantApplicationCreateRequest request)
    {
        GrantApplication grantApplication = grantApplicationService.createApplication(token, request);
        return ResponseEntity.ok(new SuccessResponse("Заявка успешно создана"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse> deleteApplication(@RequestParam String token, @RequestParam Long applicationId)
    {
        grantApplicationService.deleteApplication(token, applicationId);
        return ResponseEntity.ok(new SuccessResponse("Заявка успешно удалена"));
    }

    @GetMapping("/my")
    public ResponseEntity<List<GrantApplication>> getMyApplications(@RequestParam String token)
    {
        List<GrantApplication> applications = grantApplicationService.getMyApplications(token);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/expert/directions")
    public ResponseEntity<List<GrantApplication>> getApplicationsForExpert(@RequestParam String token)
    {
        List<GrantApplication> applications = grantApplicationService.getApplicationsForExpert(token);
        return ResponseEntity.ok(applications);
    }
}

