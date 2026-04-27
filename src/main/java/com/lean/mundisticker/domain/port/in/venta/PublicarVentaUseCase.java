package com.lean.mundisticker.domain.port.in.venta;

import com.lean.mundisticker.domain.model.Venta;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PublicarVentaUseCase {
    Venta abierta(UUID vendedorId, List<Long> stickers, BigDecimal precio);
    Venta directa(UUID vendedorId, UUID compradorId, List<Long> stickers, BigDecimal precio);
}
