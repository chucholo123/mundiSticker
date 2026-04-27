package com.lean.mundisticker.domain.model;

import com.lean.mundisticker.domain.exception.ReglaNegocioException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class VentaTest {

    @Test
    void debeCrearVentaAbiertaCorrectamente() {
        UUID id = UUID.randomUUID();
        UUID emisorId = UUID.randomUUID();
        List<Long> stickers = List.of(1L, 2L);
        BigDecimal precio = new BigDecimal("10.50");

        Venta venta = new Venta(id, emisorId, stickers, precio);

        assertEquals(id, venta.getId());
        assertEquals(emisorId, venta.getIdUsuarioEmisor());
        assertEquals(stickers, venta.getStickers());
        assertEquals(precio, venta.getPrecio());
        assertEquals(Venta.EstadoVenta.ABIERTA, venta.getEstado());
    }

    @Test
    void debeLanzarExcepcionSiPrecioEsNegativo() {
        assertThrows(ReglaNegocioException.class, () -> 
            new Venta(UUID.randomUUID(), UUID.randomUUID(), List.of(1L), new BigDecimal("-1.00")));
    }

    @Test
    void debeCrearVentaDirectaCorrectamente() {
        UUID id = UUID.randomUUID();
        UUID emisorId = UUID.randomUUID();
        UUID receptorId = UUID.randomUUID();
        List<Long> stickers = List.of(1L);
        BigDecimal precio = new BigDecimal("5.00");

        Venta venta = new Venta(id, emisorId, receptorId, stickers, precio);

        assertEquals(Venta.EstadoVenta.PENDIENTE, venta.getEstado());
        assertEquals(receptorId, venta.getIdUsuarioReceptor());
    }

    @Test
    void debeComprarVentaAbierta() {
        Venta venta = new Venta(UUID.randomUUID(), UUID.randomUUID(), List.of(1L), BigDecimal.TEN);
        UUID compradorId = UUID.randomUUID();

        venta.comprar(compradorId);

        assertEquals(Venta.EstadoVenta.PENDIENTE, venta.getEstado());
        assertEquals(compradorId, venta.getIdUsuarioReceptor());
    }

    @Test
    void noDebeComprarSuPropiaVenta() {
        UUID emisorId = UUID.randomUUID();
        Venta venta = new Venta(UUID.randomUUID(), emisorId, List.of(1L), BigDecimal.TEN);
        
        assertThrows(ReglaNegocioException.class, () -> venta.comprar(emisorId));
    }
}
