package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.exception.EntidadNoEncontradaException;
import com.lean.mundisticker.domain.model.Intercambio;
import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.port.in.intercambio.ConsultarIntercambioUseCase;
import com.lean.mundisticker.domain.port.in.intercambio.GestionarIntercambioUseCase;
import com.lean.mundisticker.domain.port.in.intercambio.ProponerIntercambioUseCase;
import com.lean.mundisticker.domain.port.out.IntercambioRepository;
import com.lean.mundisticker.domain.port.out.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class IntercambioService implements ConsultarIntercambioUseCase, ProponerIntercambioUseCase, GestionarIntercambioUseCase {

    private final IntercambioRepository intercambioRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Intercambio abierta(UUID emisorId, List<Long> stickersOfrecidos, List<Long> stickersBuscados) {
        Intercambio intercambio = new Intercambio(UUID.randomUUID(), emisorId, stickersOfrecidos, stickersBuscados);
        return intercambioRepository.save(intercambio);
    }

    @Override
    public Intercambio directa(UUID emisorId, UUID receptorId, List<Long> stickersOfrecidos, List<Long> stickersSolicitados) {
        Intercambio intercambio = new Intercambio(UUID.randomUUID(), emisorId, receptorId, stickersOfrecidos, stickersSolicitados);
        return intercambioRepository.save(intercambio);
    }

    @Override
    public void unirse(UUID intercambioId, UUID receptorId, List<Long> stickersOfrecidos) {
        Intercambio intercambio = obtenerIntercambio(intercambioId);
        intercambio.unirseAlIntercambio(receptorId, stickersOfrecidos);
        intercambioRepository.save(intercambio);
    }

    @Override
    public void aceptar(UUID intercambioId) {
        Intercambio intercambio = obtenerIntercambio(intercambioId);
        Usuario emisor = usuarioRepository.findById(intercambio.getIdUsuarioEmisor())
                .orElseThrow(() -> new EntidadNoEncontradaException("Emisor no encontrado"));
        Usuario receptor = usuarioRepository.findById(intercambio.getIdUsuarioReceptor())
                .orElseThrow(() -> new EntidadNoEncontradaException("Receptor no encontrado"));

        intercambio.aceptar(emisor, receptor);
        intercambioRepository.save(intercambio);

        usuarioRepository.save(emisor);
        usuarioRepository.save(receptor);
    }

    @Override
    public void rechazar(UUID intercambioId) {
        Intercambio intercambio = obtenerIntercambio(intercambioId);
        intercambio.cambiarEstado(Intercambio.EstadoIntercambio.RECHAZADO);
        intercambioRepository.save(intercambio);
    }

    @Override
    public void cancelar(UUID intercambioId) {
        Intercambio intercambio = obtenerIntercambio(intercambioId);
        intercambio.cambiarEstado(Intercambio.EstadoIntercambio.CANCELADO);
        intercambioRepository.save(intercambio);
    }

    @Override
    public Optional<Intercambio> porId(UUID id) {
        return intercambioRepository.findById(id);
    }

    @Override
    public List<Intercambio> abiertos() {
        return intercambioRepository.findAbiertos();
    }

    @Override
    public List<Intercambio> porUsuario(UUID usuarioId) {
        return intercambioRepository.findByUsuarioEmisorId(usuarioId);
    }

    private Intercambio obtenerIntercambio(UUID id) {
        return intercambioRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Intercambio no encontrado"));
    }
}
