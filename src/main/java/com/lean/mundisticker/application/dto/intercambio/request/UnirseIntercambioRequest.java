package com.lean.mundisticker.application.dto.intercambio.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record UnirseIntercambioRequest(
    @NotEmpty(message = "Debe ofrecer al menos un sticker para unirse al intercambio")
    List<Long> stickersOfrecidos
) {}
