package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.lean.mundisticker.application.dto.sticker.response.StickerResponse;
import com.lean.mundisticker.application.mapper.StickerMapper;
import com.lean.mundisticker.domain.model.Sticker;
import com.lean.mundisticker.domain.port.in.sticker.ConsultarStickerUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StickerController.class)
class StickerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConsultarStickerUseCase consultarStickerUseCase;

    @MockitoBean
    private StickerMapper stickerMapper;

    @Test
    void debeListarTodosLosStickers() throws Exception {
        Sticker sticker = new Sticker(1L, "ARG 10", "Argentina");
        StickerResponse response = new StickerResponse(1L, "ARG 10", "Lionel Messi", "Argentina", "http://image.com");
        
        when(consultarStickerUseCase.todos()).thenReturn(List.of(sticker));
        when(stickerMapper.toResponse(sticker)).thenReturn(response);

        mockMvc.perform(get("/api/v1/stickers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numero").value("ARG 10"))
                .andExpect(jsonPath("$[0].nombre").value("Lionel Messi"));
    }

    @Test
    void debeObtenerStickerPorId() throws Exception {
        Long id = 1L;
        Sticker sticker = new Sticker(id, "ARG 10", "Argentina");
        StickerResponse response = new StickerResponse(id, "ARG 10", "Lionel Messi", "Argentina", "http://image.com");

        when(consultarStickerUseCase.porId(id)).thenReturn(Optional.of(sticker));
        when(stickerMapper.toResponse(sticker)).thenReturn(response);

        mockMvc.perform(get("/api/v1/stickers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("ARG 10"));
    }

    @Test
    void debeBuscarPorSeleccion() throws Exception {
        String seleccion = "Argentina";
        Sticker sticker = new Sticker(1L, "ARG 10", seleccion);
        StickerResponse response = new StickerResponse(1L, "ARG 10", "Lionel Messi", seleccion, "http://image.com");

        when(consultarStickerUseCase.porSeleccion(seleccion)).thenReturn(List.of(sticker));
        when(stickerMapper.toResponse(sticker)).thenReturn(response);

        mockMvc.perform(get("/api/v1/stickers/seleccion/" + seleccion))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].seleccion").value(seleccion));
    }
}
