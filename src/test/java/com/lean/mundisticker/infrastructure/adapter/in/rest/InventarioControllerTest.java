package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lean.mundisticker.application.dto.inventario.request.UsuarioStickerRequest;
import com.lean.mundisticker.application.dto.inventario.response.UsuarioStickerResponse;
import com.lean.mundisticker.application.mapper.UsuarioStickerMapper;
import com.lean.mundisticker.domain.model.UsuarioSticker;
import com.lean.mundisticker.domain.port.in.usuarioSticker.ConsultarInventarioUseCase;
import com.lean.mundisticker.domain.port.in.usuarioSticker.GestionarInventarioUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConsultarInventarioUseCase consultarInventarioUseCase;

    @MockitoBean
    private GestionarInventarioUseCase gestionarInventarioUseCase;

    @MockitoBean
    private UsuarioStickerMapper usuarioStickerMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void debeObtenerInventarioPorUsuario() throws Exception {
        UUID usuarioId = UUID.randomUUID();
        UsuarioSticker us = new UsuarioSticker(UUID.randomUUID(), 1L, usuarioId, UsuarioSticker.TipoSticker.REPETIDA, 2);
        UsuarioStickerResponse response = new UsuarioStickerResponse(us.getId(), 1L, usuarioId, "REPETIDA", 2);

        when(consultarInventarioUseCase.porUsuario(usuarioId)).thenReturn(List.of(us));
        when(usuarioStickerMapper.toResponse(us)).thenReturn(response);

        mockMvc.perform(get("/api/v1/inventario/usuario/" + usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSticker").value(1));
    }

    @Test
    void debeAgregarStickerAlInventario() throws Exception {
        UUID usuarioId = UUID.randomUUID();
        UsuarioStickerRequest request = new UsuarioStickerRequest(1L, "REPETIDA", 2);
        UsuarioSticker us = new UsuarioSticker(UUID.randomUUID(), 1L, usuarioId, UsuarioSticker.TipoSticker.REPETIDA, 2);
        UsuarioStickerResponse response = new UsuarioStickerResponse(us.getId(), 1L, usuarioId, "REPETIDA", 2);

        when(gestionarInventarioUseCase.agregarSticker(eq(usuarioId), eq(1L), any(), eq(2))).thenReturn(us);
        when(usuarioStickerMapper.toResponse(us)).thenReturn(response);

        mockMvc.perform(post("/api/v1/inventario/usuario/" + usuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    void debeEliminarStickerDelInventario() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/inventario/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void debeActualizarCantidad() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(patch("/api/v1/inventario/" + id)
                .param("cantidad", "10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void debeBuscarUsuariosConRepetido() throws Exception {
        Long stickerId = 1L;
        UUID usuarioId = UUID.randomUUID();
        when(consultarInventarioUseCase.usuariosConStickerRepetido(stickerId)).thenReturn(List.of(usuarioId));

        mockMvc.perform(get("/api/v1/inventario/repetido/" + stickerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(usuarioId.toString()));
    }
}
