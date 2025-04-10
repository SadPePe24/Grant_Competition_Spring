package com.example.grant_competition_spring.server;

import com.example.grant_competition_spring.dto.request.ExpertRegisterRequest;
import com.example.grant_competition_spring.entity.Expert;
import com.example.grant_competition_spring.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experts")
@RequiredArgsConstructor
public class ExpertController
{
    private final ExpertService expertService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ExpertRegisterRequest request) {
        Expert expert = expertService.register(request);
        return ResponseEntity.ok(expert);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
        Expert expert = expertService.login(login, password);
        return ResponseEntity.ok(expert);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String login) {
        expertService.delete(login);
        return ResponseEntity.ok("Эксперт удалён");
    }
}
