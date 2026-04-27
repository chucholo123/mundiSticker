package com.lean.mundisticker.domain.port.in.auth;

import com.lean.mundisticker.application.dto.auth.AuthResponse;
import com.lean.mundisticker.application.dto.auth.LoginRequest;

public interface LoginUseCase {
    AuthResponse login(LoginRequest request);
}
