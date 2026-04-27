package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.lean.mundisticker.application.dto.sticker.response.StickerResponse;
import com.lean.mundisticker.application.mapper.StickerMapper;
import com.lean.mundisticker.domain.port.in.sticker.ConsultarStickerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/stickers")
@RequiredArgsConstructor
public class StickerController {

    private final ConsultarStickerUseCase consultarStickerUseCase;
    private final StickerMapper stickerMapper;

    @GetMapping
    public ResponseEntity<List<StickerResponse>> listarTodos() {
        List<StickerResponse> stickers = consultarStickerUseCase.todos()
                .stream()
                .map(stickerMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(stickers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerResponse> obtenerPorId(@PathVariable Long id) {
        return consultarStickerUseCase.porId(id)
                .map(stickerMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/seleccion/{seleccion}")
    public ResponseEntity<List<StickerResponse>> buscarPorSeleccion(@PathVariable String seleccion) {
        List<StickerResponse> stickers = consultarStickerUseCase.porSeleccion(seleccion)
                .stream()
                .map(stickerMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(stickers);
    }
}
