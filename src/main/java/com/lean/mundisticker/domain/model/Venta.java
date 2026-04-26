package com.lean.mundisticker.domain.model;

import com.lean.mundisticker.domain.exception.ReglaNegocioException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Venta {
    private UUID id;
    private UUID idUsuarioEmisor;
    private UUID idUsuarioReceptor;
    private List<Long> stickers;
    private EstadoVenta estado;
    private BigDecimal precio;

    public enum EstadoVenta {
        ABIERTA,
        PENDIENTE,
        COMPLETADA,
        CANCELADA
    }

    public Venta(UUID id, UUID idUsuarioEmisor, List<Long> stickers, BigDecimal precio) {
        validarStickers(stickers);
        validarPrecio(precio);
        this.id = id;
        this.idUsuarioEmisor = idUsuarioEmisor;
        this.stickers = new ArrayList<>(stickers);
        this.precio = precio;
        this.estado = EstadoVenta.ABIERTA;
    }

    public Venta(UUID id, UUID idUsuarioEmisor, UUID idUsuarioReceptor, List<Long> stickers, BigDecimal precio) {
        validarStickers(stickers);
        validarPrecio(precio);
        if (idUsuarioReceptor == null) {
            throw new ReglaNegocioException("El receptor no puede ser nulo en una venta directa");
        }
        if (idUsuarioReceptor.equals(idUsuarioEmisor)) {
            throw new ReglaNegocioException("No puedes realizar una venta directa a ti mismo");
        }
        this.id = id;
        this.idUsuarioEmisor = idUsuarioEmisor;
        this.idUsuarioReceptor = idUsuarioReceptor;
        this.stickers = new ArrayList<>(stickers);
        this.precio = precio;
        this.estado = EstadoVenta.PENDIENTE;
    }

    public void comprar(UUID idUsuarioComprador) {
        if (this.estado != EstadoVenta.ABIERTA) {
            throw new ReglaNegocioException("Esta venta ya no está disponible para compra abierta");
        }
        if (idUsuarioComprador.equals(this.idUsuarioEmisor)) {
            throw new ReglaNegocioException("No puedes comprar tu propia venta");
        }
        this.idUsuarioReceptor = idUsuarioComprador;
        this.estado = EstadoVenta.PENDIENTE;
    }

    public void agregarSticker(Long idSticker) {
        if (this.estado != EstadoVenta.ABIERTA) {
            throw new ReglaNegocioException("No se pueden modificar los stickers una vez iniciada la transacción");
        }
        this.stickers.add(idSticker);
    }

    public void eliminarSticker(Long idSticker) {
        if (this.estado != EstadoVenta.ABIERTA) {
            throw new ReglaNegocioException("No se pueden modificar los stickers una vez iniciada la transacción");
        }
        if (this.stickers.size() <= 1) {
            throw new ReglaNegocioException("La venta debe contener al menos un sticker");
        }
        this.stickers.remove(idSticker);
    }

    public void modificarPrecio(BigDecimal nuevoPrecio) {
        if (this.estado != EstadoVenta.ABIERTA) {
            throw new ReglaNegocioException("No se puede modificar el precio una vez iniciada la transacción");
        }
        validarPrecio(nuevoPrecio);
        this.precio = nuevoPrecio;
    }

    private void validarStickers(List<Long> stickers) {
        if (stickers == null || stickers.isEmpty()) {
            throw new ReglaNegocioException("La venta debe incluir al menos un sticker");
        }
    }

    private void validarPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new ReglaNegocioException("El precio no puede ser negativo");
        }
    }

    public void completar(Usuario vendedor, Usuario comprador) {
        if (this.estado != EstadoVenta.PENDIENTE) {
            throw new ReglaNegocioException("Solo se puede completar una venta en estado PENDIENTE");
        }
        if (!vendedor.getId().equals(this.idUsuarioEmisor) || !comprador.getId().equals(this.idUsuarioReceptor)) {
            throw new ReglaNegocioException("Los usuarios proporcionados no coinciden con los participantes de la venta");
        }

        this.stickers.forEach(vendedor::completarEntregaSticker);
        this.stickers.forEach(comprador::completarRecepcionSticker);

        this.estado = EstadoVenta.COMPLETADA;
    }

    public void cambiarEstado(EstadoVenta nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public UUID getId() { return id; }
    public UUID getIdUsuarioEmisor() { return idUsuarioEmisor; }
    public UUID getIdUsuarioReceptor() { return idUsuarioReceptor; }
    public List<Long> getStickers() { return new ArrayList<>(stickers); }
    public EstadoVenta getEstado() { return estado; }
    public BigDecimal getPrecio() { return precio; }
}
