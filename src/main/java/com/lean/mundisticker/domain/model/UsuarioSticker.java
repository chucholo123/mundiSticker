package com.lean.mundisticker.domain.model;

import java.util.UUID;

public class UsuarioSticker {
    private UUID id;
    private Long idSticker;
    private UUID idUsuario;
    private TipoSticker tipo;
    private int cantidad;

    public enum TipoSticker {
        REPETIDA,
        FALTANTE
    }

    public UsuarioSticker(UUID id, Long idSticker, UUID idUsuario, TipoSticker tipo, int cantidad) {
        this.id = id;
        this.idSticker = idSticker;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public void incrementarCantidad(){
        this.cantidad += 1;
    }

    public void decrementarCantidad(){
        if (this.cantidad == 0) throw new IllegalArgumentException("No puedes disminuir la cantidad de stickers");
        this.cantidad -= 1;
    }

    public void actualizarCantidad(int nuevaCantidad) {
        if (nuevaCantidad < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa");
        this.cantidad = nuevaCantidad;
    }

    public UUID getId() {
        return id;
    }

    public Long getIdSticker() {
        return idSticker;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public TipoSticker getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }
}
