package com.lean.mundisticker.domain.port.in.usuarioSticker;

import com.lean.mundisticker.domain.model.UsuarioSticker;
import java.util.List;
import java.util.UUID;

public interface ConsultarInventarioUseCase {
    List<UsuarioSticker> porUsuario(UUID usuarioId);
    List<UUID> usuariosConStickerRepetido(Long stickerId);
}
