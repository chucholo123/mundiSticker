package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.lean.mundisticker.application.dto.venta.request.VentaRequest;
import com.lean.mundisticker.application.dto.venta.response.VentaResponse;
import com.lean.mundisticker.application.mapper.VentaMapper;
import com.lean.mundisticker.domain.model.Venta;
import com.lean.mundisticker.domain.port.in.venta.ConsultarVentaUseCase;
import com.lean.mundisticker.domain.port.in.venta.GestionarVentaUseCase;
import com.lean.mundisticker.domain.port.in.venta.PublicarVentaUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final ConsultarVentaUseCase consultarVentaUseCase;
    private final PublicarVentaUseCase publicarVentaUseCase;
    private final GestionarVentaUseCase gestionarVentaUseCase;
    private final VentaMapper ventaMapper;

    @PostMapping
    public ResponseEntity<VentaResponse> publicar(@Valid @RequestBody VentaRequest request) {
        Venta guardada;
        if (request.idUsuarioReceptor() == null) {
            guardada = publicarVentaUseCase.abierta(request.idUsuarioEmisor(), 
                    request.stickers(), request.precio());
        } else {
            guardada = publicarVentaUseCase.directa(request.idUsuarioEmisor(), 
                    request.idUsuarioReceptor(), request.stickers(), request.precio());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaMapper.toResponse(guardada));
    }

    @PostMapping("/{id}/comprar")
    public ResponseEntity<Void> comprar(@PathVariable UUID id, @RequestParam UUID compradorId) {
        gestionarVentaUseCase.comprar(id, compradorId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<Void> completar(@PathVariable UUID id) {
        gestionarVentaUseCase.completar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable UUID id) {
        gestionarVentaUseCase.cancelar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerPorId(@PathVariable UUID id) {
        return consultarVentaUseCase.porId(id)
                .map(ventaMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/abiertas")
    public ResponseEntity<List<VentaResponse>> listarAbiertas() {
        List<VentaResponse> abiertas = consultarVentaUseCase.abiertas()
                .stream()
                .map(ventaMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(abiertas);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VentaResponse>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<VentaResponse> porUsuario = consultarVentaUseCase.porUsuario(usuarioId)
                .stream()
                .map(ventaMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(porUsuario);
    }
}
