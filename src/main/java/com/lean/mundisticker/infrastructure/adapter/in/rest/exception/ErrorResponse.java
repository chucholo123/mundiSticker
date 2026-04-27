package com.lean.mundisticker.infrastructure.adapter.in.rest.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    List<String> details
) {}
