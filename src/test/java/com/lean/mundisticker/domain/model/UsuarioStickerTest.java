package com.lean.mundisticker.domain.model;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioStickerTest {

    @Test
    void debeCrearUsuarioStickerCorrectamente() {
        UUID id = UUID.randomUUID();
        Long idSticker = 1L;
        UUID idUsuario = UUID.randomUUID();
        UsuarioSticker.TipoSticker tipo = UsuarioSticker.TipoSticker.REPETIDA;
        int cantidad = 2;

        UsuarioSticker us = new UsuarioSticker(id, idSticker, idUsuario, tipo, cantidad);

        assertEquals(id, us.getId());
        assertEquals(idSticker, us.getIdSticker());
        assertEquals(idUsuario, us.getIdUsuario());
        assertEquals(tipo, us.getTipo());
        assertEquals(cantidad, us.getCantidad());
    }

    @Test
    void debeIncrementarCantidad() {
        UsuarioSticker us = new UsuarioSticker(UUID.randomUUID(), 1L, UUID.randomUUID(), UsuarioSticker.TipoSticker.REPETIDA, 1);
        us.incrementarCantidad();
        assertEquals(2, us.getCantidad());
    }

    @Test
    void debeDecrementarCantidad() {
        UsuarioSticker us = new UsuarioSticker(UUID.randomUUID(), 1L, UUID.randomUUID(), UsuarioSticker.TipoSticker.REPETIDA, 1);
        us.decrementarCantidad();
        assertEquals(0, us.getCantidad());
    }

    @Test
    void debeLanzarExcepcionAlDecrementarBajoCero() {
        UsuarioSticker us = new UsuarioSticker(UUID.randomUUID(), 1L, UUID.randomUUID(), UsuarioSticker.TipoSticker.REPETIDA, 0);
        assertThrows(IllegalArgumentException.class, us::decrementarCantidad);
    }

    @Test
    void debeActualizarCantidad() {
        UsuarioSticker us = new UsuarioSticker(UUID.randomUUID(), 1L, UUID.randomUUID(), UsuarioSticker.TipoSticker.REPETIDA, 1);
        us.actualizarCantidad(5);
        assertEquals(5, us.getCantidad());
    }

    @Test
    void debeLanzarExcepcionAlActualizarCantidadNegativa() {
        UsuarioSticker us = new UsuarioSticker(UUID.randomUUID(), 1L, UUID.randomUUID(), UsuarioSticker.TipoSticker.REPETIDA, 1);
        assertThrows(IllegalArgumentException.class, () -> us.actualizarCantidad(-1));
    }
}
