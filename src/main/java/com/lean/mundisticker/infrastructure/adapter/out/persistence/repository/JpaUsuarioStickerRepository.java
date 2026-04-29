package com.lean.mundisticker.infrastructure.adapter.out.persistence.repository;

import com.lean.mundisticker.domain.model.UsuarioSticker.TipoSticker;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.UsuarioStickerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUsuarioStickerRepository extends JpaRepository<UsuarioStickerEntity, UUID> {
    List<UsuarioStickerEntity> findByUsuarioId(UUID usuarioId);
    List<UsuarioStickerEntity> findByStickerIdAndTipo(Long stickerId, TipoSticker tipo);
    Optional<UsuarioStickerEntity> findByUsuarioIdAndStickerIdAndTipo(UUID usuarioId, Long stickerId, TipoSticker tipo);

    @Query("SELECT us.usuario.id FROM UsuarioStickerEntity us WHERE us.sticker.id = :stickerId AND us.tipo = 'REPETIDA'")
    List<UUID> findUsuariosConStickerRepetido(@Param("stickerId") Long stickerId);
}
