package com.lean.mundisticker.domain.port.in.venta;

import java.util.UUID;

public interface GestionarVentaUseCase {
    void comprar(UUID ventaId, UUID compradorId);
    void completar(UUID ventaId);
    void cancelar(UUID ventaId);
}
