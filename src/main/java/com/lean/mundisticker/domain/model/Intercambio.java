package com.lean.mundisticker.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Intercambio {
    private UUID id;
    private UUID idUsuarioEmisor;
    private UUID idUsuarioReceptor;
    private List<Long> stickersEmisor;
    private List<Long> stickersReceptor;
    private EstadoIntercambio estado;

    public enum EstadoIntercambio {
        ABIERTO,
        PENDIENTE,
        ACEPTADO,
        RECHAZADO,
        CANCELADO
    }

    // Constructor para Oferta Pública (con o sin stickers deseados)
    public Intercambio(UUID id, UUID idUsuarioEmisor, List<Long> stickersEmisor, List<Long> stickersDeseados) {
        validarStickersEmisor(stickersEmisor);
        this.id = id;
        this.idUsuarioEmisor = idUsuarioEmisor;
        this.stickersEmisor = new ArrayList<>(stickersEmisor);
        this.stickersReceptor = stickersDeseados != null ? new ArrayList<>(stickersDeseados) : new ArrayList<>();
        this.estado = EstadoIntercambio.ABIERTO;
    }

    // Constructor para Intercambio Directo a un Usuario
    public Intercambio(UUID id, UUID idUsuarioEmisor, UUID idUsuarioReceptor, List<Long> stickersEmisor, List<Long> stickersReceptor) {
        validarStickersEmisor(stickersEmisor);
        if (idUsuarioReceptor == null) {
            throw new IllegalArgumentException("El receptor no puede ser nulo en un intercambio directo");
        }
        if (stickersReceptor == null || stickersReceptor.isEmpty()) {
            throw new IllegalArgumentException("Debe especificar qué stickers desea del receptor");
        }
        this.id = id;
        this.idUsuarioEmisor = idUsuarioEmisor;
        this.idUsuarioReceptor = idUsuarioReceptor;
        this.stickersEmisor = new ArrayList<>(stickersEmisor);
        this.stickersReceptor = new ArrayList<>(stickersReceptor);
        this.estado = EstadoIntercambio.PENDIENTE;
    }

    public void unirseAlIntercambio(UUID idUsuarioReceptor, List<Long> stickersPropuestos) {
        if (this.estado != EstadoIntercambio.ABIERTO) {
            throw new IllegalStateException("Solo se puede unir a un intercambio en estado ABIERTO");
        }
        if (idUsuarioReceptor.equals(this.idUsuarioEmisor)) {
            throw new IllegalArgumentException("No puedes unirte a tu propio intercambio");
        }

        // Si el emisor ya había pedido stickers específicos, el receptor debe cumplirlos
        // O si el receptor propone algo diferente, se actualiza la lista (según lógica de negocio)
        if (stickersPropuestos != null && !stickersPropuestos.isEmpty()) {
            this.stickersReceptor = new ArrayList<>(stickersPropuestos);
        } else if (this.stickersReceptor.isEmpty()) {
            throw new IllegalArgumentException("Debes proponer stickers para completar el intercambio");
        }

        this.idUsuarioReceptor = idUsuarioReceptor;
        this.estado = EstadoIntercambio.PENDIENTE;
    }

    public void agregarSticker(UUID idUsuario, Long idSticker) {
        validarParticipacionOcupada(idUsuario);
        if (idUsuario.equals(idUsuarioEmisor)) {
            stickersEmisor.add(idSticker);
        } else {
            stickersReceptor.add(idSticker);
        }
    }

    public void eliminarSticker(UUID idUsuario, Long idSticker) {
        validarParticipacionOcupada(idUsuario);
        if (idUsuario.equals(idUsuarioEmisor)) {
            if (stickersEmisor.size() <= 1) throw new IllegalStateException("El emisor debe ofrecer al menos un sticker");
            stickersEmisor.remove(idSticker);
        } else {
            // El receptor puede estar vaciando si aún es una oferta abierta y el emisor quita lo que pide
            stickersReceptor.remove(idSticker);
        }
    }

    private void validarStickersEmisor(List<Long> stickers) {
        if (stickers == null || stickers.isEmpty()) {
            throw new IllegalArgumentException("El emisor debe ofrecer al menos un sticker");
        }
    }

    private void validarParticipacionOcupada(UUID idUsuario) {
        // En estado ABIERTO, el receptor aún puede ser null
        if (!idUsuario.equals(idUsuarioEmisor) && (idUsuarioReceptor == null || !idUsuario.equals(idUsuarioReceptor))) {
            throw new IllegalArgumentException("El usuario no tiene permisos sobre este intercambio");
        }
    }

    public void cambiarEstado(EstadoIntercambio nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getIdUsuarioEmisor() { return idUsuarioEmisor; }
    public UUID getIdUsuarioReceptor() { return idUsuarioReceptor; }
    public List<Long> getStickersEmisor() { return new ArrayList<>(stickersEmisor); }
    public List<Long> getStickersReceptor() { return new ArrayList<>(stickersReceptor); }
    public EstadoIntercambio getEstado() { return estado; }
}