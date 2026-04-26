package com.lean.mundisticker.domain.port.out;

import com.lean.mundisticker.domain.model.Intercambio;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IntercambioRepository {
    Intercambio save(Intercambio intercambio);
    Optional<Intercambio> findById(UUID id);
    List<Intercambio> findByUsuarioEmisorId(UUID usuarioId);
    List<Intercambio> findByUsuarioReceptorId(UUID usuarioId);
    List<Intercambio> findAbiertos();
}
