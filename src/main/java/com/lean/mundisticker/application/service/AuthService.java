package com.lean.mundisticker.application.service;

import com.lean.mundisticker.application.dto.auth.AuthResponse;
import com.lean.mundisticker.application.dto.auth.LoginRequest;
import com.lean.mundisticker.domain.port.in.auth.LoginUseCase;
import com.lean.mundisticker.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.nombre(), request.contrasena())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.nombre());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
