package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.exception.EntidadNoEncontradaException;
import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.port.in.usuario.ActualizarPerfilUsuarioUseCase;
import com.lean.mundisticker.domain.port.in.usuario.ConsultarUsuarioUseCase;
import com.lean.mundisticker.domain.port.in.usuario.RegistrarUsuarioUseCase;
import com.lean.mundisticker.domain.port.out.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService implements RegistrarUsuarioUseCase, ConsultarUsuarioUseCase, ActualizarPerfilUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario ejecutar(Usuario usuario) {
        usuario.cambiarContraseña(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> porId(UUID id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> porNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    @Override
    public List<Usuario> cercanos(Point ubicacion, double distanciaMetros) {
        return usuarioRepository.findNearby(ubicacion, distanciaMetros);
    }

    @Override
    public void ejecutar(UUID id, String nombre, String contrasena, Point ubicacion) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Usuario no encontrado"));
        
        if (nombre != null) usuario.cambiarNombre(nombre);
        if (contrasena != null) usuario.cambiarContraseña(contrasena);
        if (ubicacion != null) usuario.actualizarUbicacion(ubicacion);
        
        usuarioRepository.save(usuario);
    }
}
