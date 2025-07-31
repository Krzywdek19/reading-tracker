package com.krzywdek19.readingTacker.auth;

import com.krzywdek19.readingTacker.auth.dto.LoginUserDto;
import com.krzywdek19.readingTacker.auth.dto.RegisterUserDto;
import com.krzywdek19.readingTacker.auth.dto.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authService.register(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.ok(authService.login(loginUserDto));
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (jwtService.isTokenValidAndRefersToRealResource(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
