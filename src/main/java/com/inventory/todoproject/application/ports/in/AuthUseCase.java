package com.inventory.todoproject.application.ports.in;

import com.inventory.todoproject.application.dto.request.auth.LoginRequest;
import com.inventory.todoproject.application.dto.response.AuthResponse;

public interface AuthUseCase {
    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(String refreshToken);
}
