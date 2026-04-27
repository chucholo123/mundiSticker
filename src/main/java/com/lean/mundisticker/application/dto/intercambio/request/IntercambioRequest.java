package com.lean.mundisticker.application.dto.intercambio.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record IntercambioRequest(
    @NotNull(message = "El emisor es obligatorio")
    UUID idUsuarioEmisor,

    // Puede ser nulo para intercambios "al aire" (abiertos)
    UUID idUsuarioReceptor,

    @NotEmpty(message = "Debe ofrecer al menos un sticker")
    List<Long> stickersOfrecidos,

    // Puede ser vacío o nulo si es un intercambio abierto sin requisitos específicos aún
    List<Long> stickersBuscados
) {}
