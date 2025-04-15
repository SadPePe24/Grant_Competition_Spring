package com.example.grant_competition_spring.server;

import com.example.grant_competition_spring.dto.request.ExpertRegisterRequest;
import com.example.grant_competition_spring.dto.request.RatingRequest;
import com.example.grant_competition_spring.dto.response.LoginResponse;
import com.example.grant_competition_spring.dto.response.SuccessResponse;
import com.example.grant_competition_spring.entity.Expert;
import com.example.grant_competition_spring.service.ExpertService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experts")
@RequiredArgsConstructor
@Validated
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
    public ResponseEntity<SuccessResponse> giveRating(@RequestParam String token,
                                                      @RequestBody @Valid RatingRequest request)
    {
        expertService.giveRating(token, request);
        return ResponseEntity.ok(new SuccessResponse("Оценка успешно выставлена или обновлена"));
    }

    @DeleteMapping("/removerating")
    public ResponseEntity<SuccessResponse> deleteRating(@RequestParam String token,
                                                        @RequestParam Long applicationId) {
        expertService.deleteRating(token, applicationId);
        return ResponseEntity.ok(new SuccessResponse("Оценка удалена"));
    }

    @GetMapping("/rating")
    public ResponseEntity<?> getMyRating(@RequestParam String token)
    {
        return ResponseEntity.ok(expertService.getAllRatings(token));
    }


}
