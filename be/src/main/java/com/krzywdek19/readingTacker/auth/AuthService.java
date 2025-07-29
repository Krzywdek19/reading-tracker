package com.krzywdek19.readingTacker.auth;

import com.krzywdek19.readingTacker.auth.dto.response.AuthResponse;
import com.krzywdek19.readingTacker.auth.dto.LoginUserDto;
import com.krzywdek19.readingTacker.auth.dto.RegisterUserDto;

public interface AuthService {
    AuthResponse login(LoginUserDto loginUserDto);
    AuthResponse register(RegisterUserDto registerUserDto);
}
