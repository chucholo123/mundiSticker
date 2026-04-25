package com.lean.mundisticker.domain.model;

import java.util.UUID;

public class Sticker {
    private UUID id;
    private String numero;
    private String seleccion;

    public Sticker() {}

    public Sticker(UUID id, String numero, String seleccion) {
        this.id = id;
        this.numero = numero;
        this.seleccion = seleccion;
    }

    public UUID getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getSeleccion() {
        return seleccion;
    }
}
