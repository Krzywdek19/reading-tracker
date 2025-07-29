package com.krzywdek19.readingTacker.auth.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthResponse {
    private String token;
    private long expiresIn;
}
