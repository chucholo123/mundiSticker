package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.lean.mundisticker.application.dto.intercambio.request.IntercambioRequest;
import com.lean.mundisticker.application.dto.intercambio.request.UnirseIntercambioRequest;
import com.lean.mundisticker.application.dto.intercambio.response.IntercambioResponse;
import com.lean.mundisticker.application.mapper.IntercambioMapper;
import com.lean.mundisticker.domain.model.Intercambio;
import com.lean.mundisticker.domain.port.in.intercambio.ConsultarIntercambioUseCase;
import com.lean.mundisticker.domain.port.in.intercambio.GestionarIntercambioUseCase;
import com.lean.mundisticker.domain.port.in.intercambio.ProponerIntercambioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/intercambios")
@RequiredArgsConstructor
public class IntercambioController {

    private final ConsultarIntercambioUseCase consultarIntercambioUseCase;
    private final ProponerIntercambioUseCase proponerIntercambioUseCase;
    private final GestionarIntercambioUseCase gestionarIntercambioUseCase;
    private final IntercambioMapper intercambioMapper;

    @PostMapping
    public ResponseEntity<IntercambioResponse> proponer(@Valid @RequestBody IntercambioRequest request) {
        Intercambio guardado;
        if (request.idUsuarioReceptor() == null) {
            guardado = proponerIntercambioUseCase.abierta(request.idUsuarioEmisor(), 
                    request.stickersOfrecidos(), request.stickersBuscados());
        } else {
            guardado = proponerIntercambioUseCase.directa(request.idUsuarioEmisor(), 
                    request.idUsuarioReceptor(), request.stickersOfrecidos(), request.stickersBuscados());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(intercambioMapper.toResponse(guardado));
    }

    @PostMapping("/{id}/unirse")
    public ResponseEntity<Void> unirse(
            @PathVariable UUID id,
            @RequestParam UUID receptorId,
            @Valid @RequestBody UnirseIntercambioRequest request) {
        gestionarIntercambioUseCase.unirse(id, receptorId, request.stickersOfrecidos());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/aceptar")
    public ResponseEntity<Void> aceptar(@PathVariable UUID id) {
        gestionarIntercambioUseCase.aceptar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<Void> rechazar(@PathVariable UUID id) {
        gestionarIntercambioUseCase.rechazar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable UUID id) {
        gestionarIntercambioUseCase.cancelar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntercambioResponse> obtenerPorId(@PathVariable UUID id) {
        return consultarIntercambioUseCase.porId(id)
                .map(intercambioMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/abiertos")
    public ResponseEntity<List<IntercambioResponse>> listarAbiertos() {
        List<IntercambioResponse> abiertos = consultarIntercambioUseCase.abiertos()
                .stream()
                .map(intercambioMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(abiertos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<IntercambioResponse>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<IntercambioResponse> porUsuario = consultarIntercambioUseCase.porUsuario(usuarioId)
                .stream()
                .map(intercambioMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(porUsuario);
    }
}
