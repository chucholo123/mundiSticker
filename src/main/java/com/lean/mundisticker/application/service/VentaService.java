package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.exception.EntidadNoEncontradaException;
import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.model.Venta;
import com.lean.mundisticker.domain.port.in.venta.ConsultarVentaUseCase;
import com.lean.mundisticker.domain.port.in.venta.GestionarVentaUseCase;
import com.lean.mundisticker.domain.port.in.venta.PublicarVentaUseCase;
import com.lean.mundisticker.domain.port.out.UsuarioRepository;
import com.lean.mundisticker.domain.port.out.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VentaService implements ConsultarVentaUseCase, PublicarVentaUseCase, GestionarVentaUseCase {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Venta abierta(UUID vendedorId, List<Long> stickers, BigDecimal precio) {
        Venta venta = new Venta(UUID.randomUUID(), vendedorId, stickers, precio);
        return ventaRepository.save(venta);
    }

    @Override
    public Venta directa(UUID vendedorId, UUID compradorId, List<Long> stickers, BigDecimal precio) {
        Venta venta = new Venta(UUID.randomUUID(), vendedorId, compradorId, stickers, precio);
        return ventaRepository.save(venta);
    }

    @Override
    public void comprar(UUID ventaId, UUID compradorId) {
        Venta venta = obtenerVenta(ventaId);
        venta.comprar(compradorId);
        ventaRepository.save(venta);
    }

    @Override
    public void completar(UUID ventaId) {
        Venta venta = obtenerVenta(ventaId);
        Usuario vendedor = usuarioRepository.findById(venta.getIdUsuarioEmisor())
                .orElseThrow(() -> new EntidadNoEncontradaException("Vendedor no encontrado"));
        Usuario comprador = usuarioRepository.findById(venta.getIdUsuarioReceptor())
                .orElseThrow(() -> new EntidadNoEncontradaException("Comprador no encontrado"));

        venta.completar(vendedor, comprador);
        ventaRepository.save(venta);

        usuarioRepository.save(vendedor);
        usuarioRepository.save(comprador);
    }

    @Override
    public void cancelar(UUID ventaId) {
        Venta venta = obtenerVenta(ventaId);
        venta.cambiarEstado(Venta.EstadoVenta.CANCELADA);
        ventaRepository.save(venta);
    }

    @Override
    public Optional<Venta> porId(UUID id) {
        return ventaRepository.findById(id);
    }

    @Override
    public List<Venta> abiertas() {
        return ventaRepository.findAbiertas();
    }

    @Override
    public List<Venta> porUsuario(UUID usuarioId) {
        return ventaRepository.findByUsuarioEmisorId(usuarioId);
    }

    private Venta obtenerVenta(UUID id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Venta no encontrada"));
    }
}
