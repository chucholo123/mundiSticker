package com.lean.mundisticker.domain.model;

import org.locationtech.jts.geom.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad de Dominio que representa a un Usuario.
 * Aplicando DDD: Se eliminan setters genéricos por métodos con intención de negocio.
 */
public class Usuario {
    private UUID id;
    private String nombre;
    private String contrasena;
    private Point ubicacion;
    private List<Sticker> stickers;

    public Usuario() {
        this.stickers = new ArrayList<>();
    }

    public Usuario(UUID id, String nombre, String contrasena, Point ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.ubicacion = ubicacion;
        this.stickers = new ArrayList<>();
    }

    // Métodos de Dominio (Intención de Negocio)
    public void cambiarNombre(String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nuevoNombre;
    }

    public void cambiarContraseña(String nuevaContrasena) {
        if (nuevaContrasena == null || nuevaContrasena.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        this.contrasena = nuevaContrasena;
    }

    public void agregarSticker(Sticker sticker) {
        if (sticker == null) {
            throw new IllegalArgumentException("El sticker no puede ser nulo");
        }
        this.stickers.add(sticker);
    }

    public void eliminarSticker(Sticker sticker) {
        this.stickers.remove(sticker);
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

    public List<Sticker> getStickers() {
        return new ArrayList<>(stickers); // Retornamos copia para proteger encapsulamiento
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stickersCount=" + stickers.size() +
                '}';
    }
}
