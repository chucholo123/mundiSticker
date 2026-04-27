package com.lean.mundisticker.domain.port.in.intercambio;

import java.util.List;
import java.util.UUID;

public interface GestionarIntercambioUseCase {
    void unirse(UUID intercambioId, UUID receptorId, List<Long> stickersOfrecidos);
    void aceptar(UUID intercambioId);
    void rechazar(UUID intercambioId);
    void cancelar(UUID intercambioId);
}
