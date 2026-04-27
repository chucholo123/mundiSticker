package com.lean.mundisticker.application.dto.intercambio.response;

import java.util.List;
import java.util.UUID;

public record IntercambioResponse(
    UUID id,
    UUID idUsuarioEmisor,
    UUID idUsuarioReceptor,
    List<Long> stickersEmisor,
    List<Long> stickersReceptor,
    String estado
) {}
