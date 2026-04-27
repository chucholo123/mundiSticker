package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.model.Sticker;
import com.lean.mundisticker.domain.port.out.StickerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StickerServiceTest {

    @Mock
    private StickerRepository stickerRepository;

    @InjectMocks
    private StickerService stickerService;

    private Sticker stickerPrueba;

    @BeforeEach
    void setUp() {
        stickerPrueba = new Sticker(1L, "ARG 10", "Argentina");
    }

    @Test
    void debeConsultarPorId() {
        when(stickerRepository.findById(1L)).thenReturn(Optional.of(stickerPrueba));

        Optional<Sticker> resultado = stickerService.porId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("ARG 10", resultado.get().getNumero());
    }

    @Test
    void debeConsultarTodos() {
        when(stickerRepository.findAll()).thenReturn(List.of(stickerPrueba));

        List<Sticker> resultado = stickerService.todos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void debeConsultarPorSeleccion() {
        String seleccion = "Argentina";
        when(stickerRepository.findBySeleccion(seleccion)).thenReturn(List.of(stickerPrueba));

        List<Sticker> resultado = stickerService.porSeleccion(seleccion);

        assertFalse(resultado.isEmpty());
        assertEquals(seleccion, resultado.get(0).getSeleccion());
    }
}
