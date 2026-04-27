package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.exception.EntidadNoEncontradaException;
import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.port.out.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private Usuario usuarioPrueba;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        usuarioPrueba = new Usuario(
                usuarioId,
                "Test User",
                "password123",
                geometryFactory.createPoint(new Coordinate(0, 0))
        );
    }

    @Test
    void debeRegistrarUsuario() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioPrueba);

        Usuario resultado = usuarioService.ejecutar(usuarioPrueba);

        assertNotNull(resultado);
        assertEquals("encodedPassword", resultado.getContrasena());
        verify(passwordEncoder).encode("password123");
        verify(usuarioRepository, times(1)).save(usuarioPrueba);
    }

    @Test
    void debeConsultarPorId() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioPrueba));

        Optional<Usuario> resultado = usuarioService.porId(usuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(usuarioPrueba.getNombre(), resultado.get().getNombre());
    }

    @Test
    void debeConsultarPorNombre() {
        String nombre = "Test User";
        when(usuarioRepository.findByNombre(nombre)).thenReturn(Optional.of(usuarioPrueba));

        Optional<Usuario> resultado = usuarioService.porNombre(nombre);

        assertTrue(resultado.isPresent());
        assertEquals(nombre, resultado.get().getNombre());
    }

    @Test
    void debeListarCercanos() {
        Point punto = geometryFactory.createPoint(new Coordinate(1, 1));
        double distancia = 1000.0;
        when(usuarioRepository.findNearby(punto, distancia)).thenReturn(List.of(usuarioPrueba));

        List<Usuario> resultado = usuarioService.cercanos(punto, distancia);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void debeActualizarPerfilUsuario() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioPrueba));
        
        String nuevoNombre = "Nombre Actualizado";
        String nuevaContrasena = "nuevaContrasena123";
        Point nuevaUbicacion = geometryFactory.createPoint(new Coordinate(5, 5));

        usuarioService.ejecutar(usuarioId, nuevoNombre, nuevaContrasena, nuevaUbicacion);

        assertEquals(nuevoNombre, usuarioPrueba.getNombre());
        assertEquals(nuevaContrasena, usuarioPrueba.getContrasena());
        assertEquals(nuevaUbicacion, usuarioPrueba.getUbicacion());
        verify(usuarioRepository).save(usuarioPrueba);
    }

    @Test
    void debeLanzarExcepcionAlActualizarUsuarioInexistente() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(EntidadNoEncontradaException.class, () -> 
                usuarioService.ejecutar(usuarioId, "Nombre", "pass123456", null));
    }
}
