package com.lean.mundisticker.domain.port.in.usuarioSticker;

import com.lean.mundisticker.domain.model.UsuarioSticker;
import java.util.UUID;

public interface GestionarInventarioUseCase {
    UsuarioSticker agregarSticker(UUID usuarioId, Long stickerId, UsuarioSticker.TipoSticker tipo, int cantidad);
    void eliminarSticker(UUID usuarioStickerId);
    void actualizarCantidad(UUID usuarioStickerId, int nuevaCantidad);
}
