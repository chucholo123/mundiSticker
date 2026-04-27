package com.lean.mundisticker.application.dto.venta.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record VentaRequest(
    @NotNull(message = "El emisor es obligatorio")
    UUID idUsuarioEmisor,

    // Puede ser nulo para ventas abiertas al público
    UUID idUsuarioReceptor,

    @NotEmpty(message = "La venta debe incluir al menos un sticker")
    List<Long> stickers,

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    BigDecimal precio
) {}
