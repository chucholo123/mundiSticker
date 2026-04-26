package com.lean.mundisticker.domain.model;

import com.lean.mundisticker.domain.exception.ReglaNegocioException;
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

    public Intercambio(UUID id, UUID idUsuarioEmisor, List<Long> stickersEmisor, List<Long> stickersDeseados) {
        validarStickersEmisor(stickersEmisor);
        this.id = id;
        this.idUsuarioEmisor = idUsuarioEmisor;
        this.stickersEmisor = new ArrayList<>(stickersEmisor);
        this.stickersReceptor = stickersDeseados != null ? new ArrayList<>(stickersDeseados) : new ArrayList<>();
        this.estado = EstadoIntercambio.ABIERTO;
    }

    public Intercambio(UUID id, UUID idUsuarioEmisor, UUID idUsuarioReceptor, List<Long> stickersEmisor, List<Long> stickersReceptor) {
        validarStickersEmisor(stickersEmisor);
        if (idUsuarioReceptor == null) {
            throw new ReglaNegocioException("El receptor no puede ser nulo en un intercambio directo");
        }
        if (stickersReceptor == null || stickersReceptor.isEmpty()) {
            throw new ReglaNegocioException("Debe especificar qué stickers desea del receptor");
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
            throw new ReglaNegocioException("Solo se puede unir a un intercambio en estado ABIERTO");
        }
        if (idUsuarioReceptor.equals(this.idUsuarioEmisor)) {
            throw new ReglaNegocioException("No puedes unirte a tu propio intercambio");
        }

        if (stickersPropuestos != null && !stickersPropuestos.isEmpty()) {
            this.stickersReceptor = new ArrayList<>(stickersPropuestos);
        } else if (this.stickersReceptor.isEmpty()) {
            throw new ReglaNegocioException("Debes proponer stickers para completar el intercambio");
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
            if (stickersEmisor.size() <= 1) throw new ReglaNegocioException("El emisor debe ofrecer al menos un sticker");
            stickersEmisor.remove(idSticker);
        } else {
            stickersReceptor.remove(idSticker);
        }
    }

    private void validarStickersEmisor(List<Long> stickers) {
        if (stickers == null || stickers.isEmpty()) {
            throw new ReglaNegocioException("El emisor debe ofrecer al menos un sticker");
        }
    }

    private void validarParticipacionOcupada(UUID idUsuario) {
        if (!idUsuario.equals(idUsuarioEmisor) && (idUsuarioReceptor == null || !idUsuario.equals(idUsuarioReceptor))) {
            throw new ReglaNegocioException("El usuario no tiene permisos sobre este intercambio");
        }
    }

    public void aceptar(Usuario emisor, Usuario receptor) {
        if (this.estado != EstadoIntercambio.PENDIENTE) {
            throw new ReglaNegocioException("Solo se puede aceptar un intercambio en estado PENDIENTE");
        }
        if (!emisor.getId().equals(this.idUsuarioEmisor) || !receptor.getId().equals(this.idUsuarioReceptor)) {
            throw new ReglaNegocioException("Los usuarios proporcionados no coinciden con los participantes del intercambio");
        }

        this.stickersEmisor.forEach(emisor::completarEntregaSticker);
        this.stickersReceptor.forEach(emisor::completarRecepcionSticker);
        this.stickersReceptor.forEach(receptor::completarEntregaSticker);
        this.stickersEmisor.forEach(receptor::completarRecepcionSticker);

        this.estado = EstadoIntercambio.ACEPTADO;
    }

    public void cambiarEstado(EstadoIntercambio nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public UUID getId() { return id; }
    public UUID getIdUsuarioEmisor() { return idUsuarioEmisor; }
    public UUID getIdUsuarioReceptor() { return idUsuarioReceptor; }
    public List<Long> getStickersEmisor() { return new ArrayList<>(stickersEmisor); }
    public List<Long> getStickersReceptor() { return new ArrayList<>(stickersReceptor); }
    public EstadoIntercambio getEstado() { return estado; }
}
