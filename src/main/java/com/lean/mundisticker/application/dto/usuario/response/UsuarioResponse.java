package com.lean.mundisticker.application.dto.usuario.response;

import java.util.UUID;

public record UsuarioResponse(
    UUID id,
    String nombre,
    Double latitud,
    Double longitud
) {}
