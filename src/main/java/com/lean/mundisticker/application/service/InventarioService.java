package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.model.UsuarioSticker;
import com.lean.mundisticker.domain.port.in.usuarioSticker.ConsultarInventarioUseCase;
import com.lean.mundisticker.domain.port.in.usuarioSticker.GestionarInventarioUseCase;
import com.lean.mundisticker.domain.port.out.UsuarioStickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InventarioService implements ConsultarInventarioUseCase, GestionarInventarioUseCase {

    private final UsuarioStickerRepository usuarioStickerRepository;

    @Override
    public List<UsuarioSticker> porUsuario(UUID usuarioId) {
        return usuarioStickerRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<UUID> usuariosConStickerRepetido(Long stickerId) {
        return usuarioStickerRepository.findUsuariosConStickerRepetido(stickerId);
    }

    @Override
    public UsuarioSticker agregarSticker(UUID usuarioId, Long stickerId, UsuarioSticker.TipoSticker tipo, int cantidad) {
        UsuarioSticker nuevo = new UsuarioSticker(UUID.randomUUID(), stickerId, usuarioId, tipo, cantidad);
        return usuarioStickerRepository.save(nuevo);
    }

    @Override
    public void eliminarSticker(UUID usuarioStickerId) {
        usuarioStickerRepository.delete(usuarioStickerId);
    }

    @Override
    public void actualizarCantidad(UUID usuarioStickerId, int nuevaCantidad) {
        usuarioStickerRepository.findById(usuarioStickerId).ifPresent(us -> {
            us.actualizarCantidad(nuevaCantidad);
            usuarioStickerRepository.save(us);
        });
    }
}