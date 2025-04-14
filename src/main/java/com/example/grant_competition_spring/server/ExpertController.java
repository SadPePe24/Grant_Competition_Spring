package com.example.grant_competition_spring.server;

import com.example.grant_competition_spring.dto.request.ExpertRegisterRequest;
import com.example.grant_competition_spring.dto.response.LoginResponse;
import com.example.grant_competition_spring.dto.response.SuccessResponse;
import com.example.grant_competition_spring.entity.Expert;
import com.example.grant_competition_spring.service.ExpertService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> register(@RequestBody @Valid ExpertRegisterRequest request) {
        Expert expert = expertService.register(request);
        return ResponseEntity.ok(expert);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
        String token = expertService.login(login, password);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String token)
    {
        expertService.logout(token);
        return ResponseEntity.ok(new SuccessResponse("Эксперт успешно вышел из системы"));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String token) {
        expertService.delete(token);
        return ResponseEntity.ok(new SuccessResponse("Эксперт удалён"));
    }

    @PostMapping("/giverating")
    public ResponseEntity<SuccessResponse> giverating(@RequestParam String token,
                                                      @RequestParam Long applicationId,
                                                      @RequestParam int score)
    {
        expertService.giveRating(token, applicationId, score);
        return ResponseEntity.ok(new SuccessResponse("Оценка успешно выставлена"));
    }
}
