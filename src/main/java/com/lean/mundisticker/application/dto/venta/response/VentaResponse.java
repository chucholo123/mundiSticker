package com.lean.mundisticker.application.dto.venta.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record VentaResponse(
    UUID id,
    UUID idUsuarioEmisor,
    UUID idUsuarioReceptor,
    List<Long> stickers,
    BigDecimal precio,
    String estado
) {}
