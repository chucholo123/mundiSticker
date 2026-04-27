package com.lean.mundisticker.application.dto.inventario.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UsuarioStickerRequest(
    @NotNull(message = "El ID del sticker es obligatorio")
    Long idSticker,

    @NotNull(message = "El tipo de sticker (REPETIDA/FALTANTE) es obligatorio")
    String tipo,

    @Min(value = 1, message = "La cantidad mínima es 1")
    int cantidad
) {}
