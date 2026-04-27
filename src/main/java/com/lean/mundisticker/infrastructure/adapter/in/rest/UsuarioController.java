package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.lean.mundisticker.application.dto.usuario.request.UsuarioRequest;
import com.lean.mundisticker.application.dto.usuario.response.UsuarioResponse;
import com.lean.mundisticker.application.mapper.GeometryMapper;
import com.lean.mundisticker.application.mapper.UsuarioMapper;
import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.port.in.usuario.ActualizarPerfilUsuarioUseCase;
import com.lean.mundisticker.domain.port.in.usuario.ConsultarUsuarioUseCase;
import com.lean.mundisticker.domain.port.in.usuario.RegistrarUsuarioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final ConsultarUsuarioUseCase consultarUsuarioUseCase;
    private final ActualizarPerfilUsuarioUseCase actualizarPerfilUsuarioUseCase;
    private final UsuarioMapper usuarioMapper;
    private final GeometryMapper geometryMapper;

    // PUBLICO
    @PostMapping
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toDomain(request);
        Usuario guardado = registrarUsuarioUseCase.ejecutar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toResponse(guardado));
    }

    // PROTEGIDO
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable UUID id) {
        return consultarUsuarioUseCase.porId(id)
                .map(usuarioMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PROTEGIDO
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<UsuarioResponse> obtenerPorNombre(@PathVariable String nombre) {
        return consultarUsuarioUseCase.porNombre(nombre)
                .map(usuarioMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PROTEGIDO
    @GetMapping("/cercanos")
    public ResponseEntity<List<UsuarioResponse>> buscarCercanos(
            @RequestParam Double latitud,
            @RequestParam Double longitud,
            @RequestParam(defaultValue = "5000") double distancia) {
        List<UsuarioResponse> cercanos = consultarUsuarioUseCase.cercanos(
                geometryMapper.toPoint(latitud, longitud), distancia)
                .stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cercanos);
    }

    // PROTEGIDO
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioRequest request) {
        actualizarPerfilUsuarioUseCase.ejecutar(id, request.nombre(), request.contrasena(), 
                geometryMapper.toPoint(request.latitud(), request.longitud()));
        return ResponseEntity.noContent().build();
    }
}
