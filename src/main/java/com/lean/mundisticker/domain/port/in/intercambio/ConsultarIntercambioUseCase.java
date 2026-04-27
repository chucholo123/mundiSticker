package com.lean.mundisticker.domain.port.in.intercambio;

import com.lean.mundisticker.domain.model.Intercambio;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultarIntercambioUseCase {
    Optional<Intercambio> porId(UUID id);
    List<Intercambio> abiertos();
    List<Intercambio> porUsuario(UUID usuarioId);
}
