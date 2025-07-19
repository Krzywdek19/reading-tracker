package com.krzywdek19.readingTacker.auth;

import com.krzywdek19.readingTacker.auth.dto.LoginUserDto;
import com.krzywdek19.readingTacker.auth.dto.RegisterUserDto;
import com.krzywdek19.readingTacker.auth.dto.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authService.register(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.ok(authService.login(loginUserDto));
    }
}
