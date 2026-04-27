package com.lean.mundisticker.application.dto.inventario.response;

import java.util.UUID;

public record UsuarioStickerResponse(
    UUID id,
    Long idSticker,
    UUID idUsuario,
    String tipo,
    int cantidad
) {}
