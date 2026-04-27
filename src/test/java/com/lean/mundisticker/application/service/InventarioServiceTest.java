package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.model.UsuarioSticker;
import com.lean.mundisticker.domain.port.out.UsuarioStickerRepository;
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
class InventarioServiceTest {

    @Mock
    private UsuarioStickerRepository usuarioStickerRepository;

    @InjectMocks
    private InventarioService inventarioService;

    private UUID usuarioId;
    private UsuarioSticker usuarioSticker;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        usuarioSticker = new UsuarioSticker(UUID.randomUUID(), 1L, usuarioId, UsuarioSticker.TipoSticker.REPETIDA, 2);
    }

    @Test
    void debeConsultarPorUsuario() {
        when(usuarioStickerRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(usuarioSticker));

        List<UsuarioSticker> resultado = inventarioService.porUsuario(usuarioId);

        assertEquals(1, resultado.size());
        assertEquals(usuarioId, resultado.get(0).getIdUsuario());
    }

    @Test
    void debeBuscarUsuariosConStickerRepetido() {
        Long stickerId = 1L;
        when(usuarioStickerRepository.findUsuariosConStickerRepetido(stickerId)).thenReturn(List.of(usuarioId));

        List<UUID> resultado = inventarioService.usuariosConStickerRepetido(stickerId);

        assertEquals(1, resultado.size());
        assertEquals(usuarioId, resultado.get(0));
    }

    @Test
    void debeAgregarSticker() {
        when(usuarioStickerRepository.save(any(UsuarioSticker.class))).thenReturn(usuarioSticker);

        UsuarioSticker resultado = inventarioService.agregarSticker(usuarioId, 1L, UsuarioSticker.TipoSticker.REPETIDA, 2);

        assertNotNull(resultado);
        verify(usuarioStickerRepository).save(any(UsuarioSticker.class));
    }

    @Test
    void debeEliminarSticker() {
        UUID id = UUID.randomUUID();
        doNothing().when(usuarioStickerRepository).delete(id);

        inventarioService.eliminarSticker(id);

        verify(usuarioStickerRepository).delete(id);
    }

    @Test
    void debeActualizarCantidad() {
        UUID id = usuarioSticker.getId();
        when(usuarioStickerRepository.findById(id)).thenReturn(Optional.of(usuarioSticker));
        when(usuarioStickerRepository.save(any(UsuarioSticker.class))).thenReturn(usuarioSticker);

        inventarioService.actualizarCantidad(id, 10);

        assertEquals(10, usuarioSticker.getCantidad());
        verify(usuarioStickerRepository).save(usuarioSticker);
    }
}
