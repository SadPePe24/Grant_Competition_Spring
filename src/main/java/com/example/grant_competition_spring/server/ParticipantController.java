package com.example.grant_competition_spring.server;

import com.example.grant_competition_spring.dto.request.ParticipantRegisterRequest;
import com.example.grant_competition_spring.entity.Participant;
import com.example.grant_competition_spring.service.ParticipantService;
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
    public ResponseEntity<?> registerParticipant(@RequestBody ParticipantRegisterRequest request)
    {
        Participant participant = participantService.registerParticipant(request);
        return ResponseEntity.ok(participant);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password)
    {
        Participant participant = participantService.login(login, password);
        return ResponseEntity.ok(participant);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete (@RequestParam String login) {
        participantService.delete(login);
        return ResponseEntity.ok("Участник удалён");
    }


}
