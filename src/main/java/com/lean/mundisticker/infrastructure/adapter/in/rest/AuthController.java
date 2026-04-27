package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.lean.mundisticker.application.dto.auth.AuthResponse;
import com.lean.mundisticker.application.dto.auth.LoginRequest;
import com.lean.mundisticker.domain.port.in.auth.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUseCase.login(request));
    }
}
