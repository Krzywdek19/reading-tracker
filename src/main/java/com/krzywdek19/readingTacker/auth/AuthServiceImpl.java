package com.krzywdek19.readingTacker.auth;

import com.krzywdek19.readingTacker.auth.dto.LoginUserDto;
import com.krzywdek19.readingTacker.auth.dto.response.AuthResponse;
import com.krzywdek19.readingTacker.auth.dto.RegisterUserDto;
import com.krzywdek19.readingTacker.auth.user.User;
import com.krzywdek19.readingTacker.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterUserDto registerUserDto) {
        var user = new User()
                .setEmail(registerUserDto.getEmail())
                .setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user = userRepository.save(user);

        return buildResponse(user);
    }

    public AuthResponse login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow();
        return buildResponse(user);
    }

    private AuthResponse buildResponse(UserDetails userDetails) {
        var token = jwtService.generateToken(userDetails);
        return new AuthResponse().setToken(token).setExpiresIn(jwtService.getExpiresIn());
    }
}
