package com.lean.mundisticker.domain.port.in.venta;

import com.lean.mundisticker.domain.model.Venta;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultarVentaUseCase {
    Optional<Venta> porId(UUID id);
    List<Venta> abiertas();
    List<Venta> porUsuario(UUID usuarioId);
}
