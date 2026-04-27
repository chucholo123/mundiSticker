package com.lean.mundisticker.domain.model;

import com.lean.mundisticker.domain.exception.ReglaNegocioException;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Test
    void debeCrearUsuarioCorrectamente() {
        UUID id = UUID.randomUUID();
        String nombre = "Juan Perez";
        String contrasena = "password123";
        Point ubicacion = geometryFactory.createPoint(new Coordinate(-12.046374, -77.042793));

        Usuario usuario = new Usuario(id, nombre, contrasena, ubicacion);

        assertEquals(id, usuario.getId());
        assertEquals(nombre, usuario.getNombre());
        assertEquals(contrasena, usuario.getContrasena());
        assertEquals(ubicacion, usuario.getUbicacion());
    }

    @Test
    void debeCambiarNombreCorrectamente() {
        Usuario usuario = crearUsuarioBase();
        usuario.cambiarNombre("Nuevo Nombre");
        assertEquals("Nuevo Nombre", usuario.getNombre());
    }

    @Test
    void debeLanzarExcepcionAlCambiarNombrePorUnoVacio() {
        Usuario usuario = crearUsuarioBase();
        assertThrows(ReglaNegocioException.class, () -> usuario.cambiarNombre(""));
        assertThrows(ReglaNegocioException.class, () -> usuario.cambiarNombre(null));
    }

    @Test
    void debeCambiarContrasenaCorrectamente() {
        Usuario usuario = crearUsuarioBase();
        usuario.cambiarContraseña("nuevaContrasena123");
        assertEquals("nuevaContrasena123", usuario.getContrasena());
    }

    @Test
    void debeLanzarExcepcionAlCambiarContrasenaCorta() {
        Usuario usuario = crearUsuarioBase();
        assertThrows(ReglaNegocioException.class, () -> usuario.cambiarContraseña("1234567"));
    }

    @Test
    void debeActualizarUbicacionCorrectamente() {
        Usuario usuario = crearUsuarioBase();
        Point nuevaUbicacion = geometryFactory.createPoint(new Coordinate(10.0, 10.0));
        usuario.actualizarUbicacion(nuevaUbicacion);
        assertEquals(nuevaUbicacion, usuario.getUbicacion());
    }

    private Usuario crearUsuarioBase() {
        return new Usuario(
                UUID.randomUUID(),
                "Usuario Base",
                "password123",
                geometryFactory.createPoint(new Coordinate(0, 0))
        );
    }
}
