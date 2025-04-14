package com.example.grant_competition_spring.server;

import com.example.grant_competition_spring.dto.request.ParticipantRegisterRequest;
import com.example.grant_competition_spring.dto.response.LoginResponse;
import com.example.grant_competition_spring.dto.response.SuccessResponse;
import com.example.grant_competition_spring.entity.Participant;
import com.example.grant_competition_spring.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController
{
    private final ParticipantService participantService;

    @PostMapping("/register")
    public ResponseEntity<?> registerParticipant(@RequestBody @Valid ParticipantRegisterRequest request)
    {
        Participant participant = participantService.registerParticipant(request);
        return ResponseEntity.ok(participant);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password)
    {
        String token = participantService.login(login, password);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String token)
    {
        participantService.logout(token);
        return ResponseEntity.ok(new SuccessResponse("Участник успешно вышел из системы"));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> delete (@RequestParam String token) {
        participantService.delete(token);
        return ResponseEntity.ok(new SuccessResponse("Участник успешно удалён"));
    }
}
