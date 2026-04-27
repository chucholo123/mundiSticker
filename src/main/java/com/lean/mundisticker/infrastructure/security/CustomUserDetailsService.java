package com.lean.mundisticker.infrastructure.security;

import com.lean.mundisticker.domain.port.out.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByNombre(username)
                .map(u -> new User(
                        u.getNombre(),
                        u.getContrasena(),
                        Collections.emptyList()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
