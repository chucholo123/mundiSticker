package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lean.mundisticker.application.dto.intercambio.request.IntercambioRequest;
import com.lean.mundisticker.application.dto.intercambio.request.UnirseIntercambioRequest;
import com.lean.mundisticker.application.dto.intercambio.response.IntercambioResponse;
import com.lean.mundisticker.application.mapper.IntercambioMapper;
import com.lean.mundisticker.domain.model.Intercambio;
import com.lean.mundisticker.domain.port.in.intercambio.ConsultarIntercambioUseCase;
import com.lean.mundisticker.domain.port.in.intercambio.GestionarIntercambioUseCase;
import com.lean.mundisticker.domain.port.in.intercambio.ProponerIntercambioUseCase;
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

@WebMvcTest(IntercambioController.class)
class IntercambioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConsultarIntercambioUseCase consultarIntercambioUseCase;

    @MockitoBean
    private ProponerIntercambioUseCase proponerIntercambioUseCase;

    @MockitoBean
    private GestionarIntercambioUseCase gestionarIntercambioUseCase;

    @MockitoBean
    private IntercambioMapper intercambioMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void debeProponerIntercambio() throws Exception {
        UUID emisorId = UUID.randomUUID();
        IntercambioRequest request = new IntercambioRequest(emisorId, null, List.of(1L), List.of(2L));
        Intercambio intercambio = new Intercambio(UUID.randomUUID(), emisorId, List.of(1L), List.of(2L));

        when(proponerIntercambioUseCase.abierta(eq(emisorId), any(), any())).thenReturn(intercambio);
        when(intercambioMapper.toResponse(any())).thenReturn(new IntercambioResponse(intercambio.getId(), emisorId, null, List.of(1L), List.of(2L), "ABIERTO"));

        mockMvc.perform(post("/api/v1/intercambios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void debeUnirseAIntercambio() throws Exception {
        UUID id = UUID.randomUUID();
        UUID receptorId = UUID.randomUUID();
        UnirseIntercambioRequest request = new UnirseIntercambioRequest(List.of(10L, 11L));

        mockMvc.perform(post("/api/v1/intercambios/" + id + "/unirse")
                .param("receptorId", receptorId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void debeAceptarIntercambio() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(patch("/api/v1/intercambios/" + id + "/aceptar"))
                .andExpect(status().isOk());
    }

    @Test
    void debeListarAbiertos() throws Exception {
        mockMvc.perform(get("/api/v1/intercambios/abiertos"))
                .andExpect(status().isOk());
    }
}
