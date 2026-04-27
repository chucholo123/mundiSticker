package com.lean.mundisticker.domain.model;

import com.lean.mundisticker.domain.exception.ReglaNegocioException;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class IntercambioTest {

    @Test
    void debeCrearIntercambioAbiertoCorrectamente() {
        UUID id = UUID.randomUUID();
        UUID emisorId = UUID.randomUUID();
        List<Long> ofrecidos = List.of(1L, 2L);
        List<Long> buscados = List.of(3L);

        Intercambio intercambio = new Intercambio(id, emisorId, ofrecidos, buscados);

        assertEquals(id, intercambio.getId());
        assertEquals(Intercambio.EstadoIntercambio.ABIERTO, intercambio.getEstado());
        assertEquals(2, intercambio.getStickersEmisor().size());
    }

    @Test
    void debeLanzarExcepcionSiNoHayStickersOfrecidos() {
        assertThrows(ReglaNegocioException.class, () -> 
            new Intercambio(UUID.randomUUID(), UUID.randomUUID(), List.of(), List.of(3L)));
    }

    @Test
    void debeCrearIntercambioDirectoCorrectamente() {
        UUID id = UUID.randomUUID();
        UUID emisorId = UUID.randomUUID();
        UUID receptorId = UUID.randomUUID();
        List<Long> ofrecidos = List.of(1L);
        List<Long> solicitados = List.of(3L);

        Intercambio intercambio = new Intercambio(id, emisorId, receptorId, ofrecidos, solicitados);

        assertEquals(Intercambio.EstadoIntercambio.PENDIENTE, intercambio.getEstado());
        assertEquals(receptorId, intercambio.getIdUsuarioReceptor());
    }

    @Test
    void debeUnirseAIntercambioAbierto() {
        Intercambio intercambio = new Intercambio(UUID.randomUUID(), UUID.randomUUID(), List.of(1L), null);
        UUID receptorId = UUID.randomUUID();
        List<Long> ofrecidosReceptor = List.of(3L);

        intercambio.unirseAlIntercambio(receptorId, ofrecidosReceptor);

        assertEquals(Intercambio.EstadoIntercambio.PENDIENTE, intercambio.getEstado());
        assertEquals(receptorId, intercambio.getIdUsuarioReceptor());
        assertEquals(ofrecidosReceptor, intercambio.getStickersReceptor());
    }

    @Test
    void noDebeUnirseASuPropioIntercambio() {
        UUID emisorId = UUID.randomUUID();
        Intercambio intercambio = new Intercambio(UUID.randomUUID(), emisorId, List.of(1L), null);
        
        assertThrows(ReglaNegocioException.class, () -> 
            intercambio.unirseAlIntercambio(emisorId, List.of(3L)));
    }
}
