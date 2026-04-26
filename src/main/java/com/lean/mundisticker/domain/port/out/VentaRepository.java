package com.lean.mundisticker.domain.port.out;

import com.lean.mundisticker.domain.model.Venta;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VentaRepository {
    Venta save(Venta venta);
    Optional<Venta> findById(UUID id);
    List<Venta> findByUsuarioEmisorId(UUID usuarioId);
    List<Venta> findAbiertas();
}
