package com.lean.mundisticker.application.service;

import com.lean.mundisticker.application.dto.auth.AuthResponse;
import com.lean.mundisticker.application.dto.auth.LoginRequest;
import com.lean.mundisticker.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void debeHacerLoginExitosamente() {
        LoginRequest request = new LoginRequest("juan", "password123");
        UserDetails userDetails = mock(UserDetails.class);
        String token = "mocked-jwt-token";

        when(userDetailsService.loadUserByUsername("juan")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(token);

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals(token, response.token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(userDetails);
    }
}
