package com.lean.mundisticker.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StickerTest {

    @Test
    void debeCrearStickerCorrectamente() {
        Long id = 1L;
        String nombre = "Messi";
        String seleccion = "Argentina";

        Sticker sticker = new Sticker(id, nombre, seleccion);

        assertEquals(id, sticker.getId());
        assertEquals(nombre, sticker.getNumero());
        assertEquals(seleccion, sticker.getSeleccion());
    }
}
