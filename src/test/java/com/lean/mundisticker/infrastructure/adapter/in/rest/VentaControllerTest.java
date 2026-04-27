package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lean.mundisticker.application.dto.venta.request.VentaRequest;
import com.lean.mundisticker.application.dto.venta.response.VentaResponse;
import com.lean.mundisticker.application.mapper.VentaMapper;
import com.lean.mundisticker.domain.model.Venta;
import com.lean.mundisticker.domain.port.in.venta.ConsultarVentaUseCase;
import com.lean.mundisticker.domain.port.in.venta.GestionarVentaUseCase;
import com.lean.mundisticker.domain.port.in.venta.PublicarVentaUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConsultarVentaUseCase consultarVentaUseCase;

    @MockitoBean
    private PublicarVentaUseCase publicarVentaUseCase;

    @MockitoBean
    private GestionarVentaUseCase gestionarVentaUseCase;

    @MockitoBean
    private VentaMapper ventaMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void debePublicarVenta() throws Exception {
        UUID emisorId = UUID.randomUUID();
        VentaRequest request = new VentaRequest(emisorId, null, List.of(1L), BigDecimal.TEN);
        Venta venta = new Venta(UUID.randomUUID(), emisorId, List.of(1L), BigDecimal.TEN);

        when(publicarVentaUseCase.abierta(eq(emisorId), any(), any())).thenReturn(venta);
        when(ventaMapper.toResponse(any())).thenReturn(new VentaResponse(venta.getId(), emisorId, null, List.of(1L), BigDecimal.TEN, "ABIERTA"));

        mockMvc.perform(post("/api/v1/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void debeCompletarVenta() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(patch("/api/v1/ventas/" + id + "/completar"))
                .andExpect(status().isOk());
    }

    @Test
    void debeListarAbiertas() throws Exception {
        mockMvc.perform(get("/api/v1/ventas/abiertas"))
                .andExpect(status().isOk());
    }
}
