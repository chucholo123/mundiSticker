package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.lean.mundisticker.application.dto.inventario.request.UsuarioStickerRequest;
import com.lean.mundisticker.application.dto.inventario.response.UsuarioStickerResponse;
import com.lean.mundisticker.application.mapper.UsuarioStickerMapper;
import com.lean.mundisticker.domain.model.UsuarioSticker;
import com.lean.mundisticker.domain.port.in.usuarioSticker.ConsultarInventarioUseCase;
import com.lean.mundisticker.domain.port.in.usuarioSticker.GestionarInventarioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final ConsultarInventarioUseCase consultarInventarioUseCase;
    private final GestionarInventarioUseCase gestionarInventarioUseCase;
    private final UsuarioStickerMapper usuarioStickerMapper;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioStickerResponse>> obtenerInventario(@PathVariable UUID usuarioId) {
        List<UsuarioStickerResponse> inventario = consultarInventarioUseCase.porUsuario(usuarioId)
                .stream()
                .map(usuarioStickerMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(inventario);
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<UsuarioStickerResponse> agregarSticker(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody UsuarioStickerRequest request) {
        UsuarioSticker guardado = gestionarInventarioUseCase.agregarSticker(
                usuarioId,
                request.idSticker(),
                UsuarioSticker.TipoSticker.valueOf(request.tipo().toUpperCase()),
                request.cantidad()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioStickerMapper.toResponse(guardado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSticker(@PathVariable UUID id) {
        gestionarInventarioUseCase.eliminarSticker(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> actualizarCantidad(
            @PathVariable UUID id,
            @RequestParam int cantidad) {
        gestionarInventarioUseCase.actualizarCantidad(id, cantidad);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/repetido/{stickerId}")
    public ResponseEntity<List<UUID>> buscarUsuariosConRepetido(@PathVariable Long stickerId) {
        return ResponseEntity.ok(consultarInventarioUseCase.usuariosConStickerRepetido(stickerId));
    }
}
