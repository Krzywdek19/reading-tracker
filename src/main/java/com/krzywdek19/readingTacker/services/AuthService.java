package com.krzywdek19.auth.services;

import com.krzywdek19.auth.dto.response.AuthResponse;
import com.krzywdek19.auth.dto.LoginUserDto;
import com.krzywdek19.auth.dto.RegisterUserDto;

public interface AuthService {
    AuthResponse login(LoginUserDto loginUserDto);
    AuthResponse register(RegisterUserDto registerUserDto);
}
