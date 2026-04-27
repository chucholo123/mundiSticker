package com.lean.mundisticker.domain.port.out;

import com.lean.mundisticker.domain.model.UsuarioSticker;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioStickerRepository {
    UsuarioSticker save(UsuarioSticker usuarioSticker);
    Optional<UsuarioSticker> findById(UUID id);
    void delete(UUID id);
    List<UsuarioSticker> findByUsuarioId(UUID usuarioId);
    List<UsuarioSticker> findByStickerIdAndTipo(Long stickerId, UsuarioSticker.TipoSticker tipo);
    // Para el matching: buscar usuarios que tengan lo que yo necesito
    List<UUID> findUsuariosConStickerRepetido(Long stickerId);
}
