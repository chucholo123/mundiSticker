package com.lean.mundisticker.application.dto.sticker.response;

public record StickerResponse(
    Long id,
    String numero,
    String nombre,
    String seleccion,
    String imagenUrl
) {}
