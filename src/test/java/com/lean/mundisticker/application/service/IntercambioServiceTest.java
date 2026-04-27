package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.model.Intercambio;
import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.port.out.IntercambioRepository;
import com.lean.mundisticker.domain.port.out.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IntercambioServiceTest {

    @Mock
    private IntercambioRepository intercambioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private IntercambioService intercambioService;

    private UUID emisorId;
    private UUID receptorId;
    private Intercambio intercambioAbierto;

    @BeforeEach
    void setUp() {
        emisorId = UUID.randomUUID();
        receptorId = UUID.randomUUID();
        intercambioAbierto = new Intercambio(UUID.randomUUID(), emisorId, List.of(1L), null);
    }

    @Test
    void debeProponerIntercambioAbierto() {
        when(intercambioRepository.save(any(Intercambio.class))).thenAnswer(i -> i.getArguments()[0]);

        Intercambio resultado = intercambioService.abierta(emisorId, List.of(1L), List.of(2L));

        assertNotNull(resultado);
        assertEquals(Intercambio.EstadoIntercambio.ABIERTO, resultado.getEstado());
        verify(intercambioRepository).save(any(Intercambio.class));
    }

    @Test
    void debeUnirseAIntercambio() {
        UUID intercambioId = intercambioAbierto.getId();
        when(intercambioRepository.findById(intercambioId)).thenReturn(Optional.of(intercambioAbierto));

        intercambioService.unirse(intercambioId, receptorId, List.of(3L));

        assertEquals(Intercambio.EstadoIntercambio.PENDIENTE, intercambioAbierto.getEstado());
        assertEquals(receptorId, intercambioAbierto.getIdUsuarioReceptor());
        verify(intercambioRepository).save(intercambioAbierto);
    }

    @Test
    void debeAceptarIntercambio() {
        Intercambio intercambioPendiente = new Intercambio(UUID.randomUUID(), emisorId, receptorId, List.of(1L), List.of(3L));
        Usuario emisor = mock(Usuario.class);
        Usuario receptor = mock(Usuario.class);
        when(emisor.getId()).thenReturn(emisorId);
        when(receptor.getId()).thenReturn(receptorId);

        when(intercambioRepository.findById(intercambioPendiente.getId())).thenReturn(Optional.of(intercambioPendiente));
        when(usuarioRepository.findById(emisorId)).thenReturn(Optional.of(emisor));
        when(usuarioRepository.findById(receptorId)).thenReturn(Optional.of(receptor));

        intercambioService.aceptar(intercambioPendiente.getId());

        assertEquals(Intercambio.EstadoIntercambio.ACEPTADO, intercambioPendiente.getEstado());
        verify(intercambioRepository).save(intercambioPendiente);
        verify(usuarioRepository).save(emisor);
        verify(usuarioRepository).save(receptor);
    }
}
