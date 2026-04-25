package com.lean.mundisticker.domain.model;

import java.util.UUID;

public class Sticker {
    private Long id;
    private String nombre;
    private String seleccion;

    public Sticker() {}

    public Sticker(Long id, String nombre, String seleccion) {
        this.id = id;
        this.nombre = nombre;
        this.seleccion = seleccion;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return nombre;
    }

    public String getSeleccion() {
        return seleccion;
    }
}
