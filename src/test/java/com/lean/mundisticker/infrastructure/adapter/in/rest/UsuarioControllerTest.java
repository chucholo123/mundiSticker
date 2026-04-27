package com.lean.mundisticker.infrastructure.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lean.mundisticker.application.dto.usuario.request.UsuarioRequest;
import com.lean.mundisticker.application.dto.usuario.response.UsuarioResponse;
import com.lean.mundisticker.application.mapper.GeometryMapper;
import com.lean.mundisticker.application.mapper.UsuarioMapper;
import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.port.in.usuario.ActualizarPerfilUsuarioUseCase;
import com.lean.mundisticker.domain.port.in.usuario.ConsultarUsuarioUseCase;
import com.lean.mundisticker.domain.port.in.usuario.RegistrarUsuarioUseCase;
import com.lean.mundisticker.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrarUsuarioUseCase registrarUsuarioUseCase;

    @MockitoBean
    private ConsultarUsuarioUseCase consultarUsuarioUseCase;

    @MockitoBean
    private ActualizarPerfilUsuarioUseCase actualizarPerfilUsuarioUseCase;

    @MockitoBean
    private UsuarioMapper usuarioMapper;

    @MockitoBean
    private GeometryMapper geometryMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID usuarioId;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        GeometryFactory factory = new GeometryFactory();
        usuario = new Usuario(usuarioId, "Juan", "pass123456", factory.createPoint(new Coordinate(0, 0)));
    }

    @Test
    void debeRegistrarUsuario() throws Exception {
        UsuarioRequest request = new UsuarioRequest("Juan", "pass123456", 0.0, 0.0);
        UsuarioResponse response = new UsuarioResponse(usuarioId, "Juan", 0.0, 0.0);

        when(usuarioMapper.toDomain(any())).thenReturn(usuario);
        when(registrarUsuarioUseCase.ejecutar(any())).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void debeObtenerUsuarioPorId() throws Exception {
        UsuarioResponse response = new UsuarioResponse(usuarioId, "Juan", 0.0, 0.0);
        
        when(consultarUsuarioUseCase.porId(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        mockMvc.perform(get("/api/v1/usuarios/" + usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void debeRetornar404SiUsuarioNoExiste() throws Exception {
        when(consultarUsuarioUseCase.porId(usuarioId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/usuarios/" + usuarioId))
                .andExpect(status().isNotFound());
    }
}
