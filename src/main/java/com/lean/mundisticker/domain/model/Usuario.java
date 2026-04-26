package com.lean.mundisticker.domain.model;

import com.lean.mundisticker.domain.exception.ReglaNegocioException;
import org.locationtech.jts.geom.Point;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nombre;
    private String contrasena;
    private Point ubicacion;

    public Usuario(UUID id, String nombre, String contrasena, Point ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.ubicacion = ubicacion;
    }

    public void completarRecepcionSticker(Long idSticker) {
        // Hook para consistencia transaccional: se ejecutará al completar un intercambio o venta
    }

    public void completarEntregaSticker(Long idSticker) {
        // Hook para consistencia transaccional: se ejecutará al completar un intercambio o venta
    }

    public void cambiarNombre(String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new ReglaNegocioException("El nombre no puede estar vacío");
        }
        this.nombre = nuevoNombre;
    }

    public void cambiarContraseña(String nuevaContrasena) {
        if (nuevaContrasena == null || nuevaContrasena.length() < 8) {
            throw new ReglaNegocioException("La contraseña debe tener al menos 8 caracteres");
        }
        this.contrasena = nuevaContrasena;
    }

    public void actualizarUbicacion(Point nuevaUbicacion) {
        this.ubicacion = nuevaUbicacion;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Point getUbicacion() {
        return ubicacion;
    }
}
