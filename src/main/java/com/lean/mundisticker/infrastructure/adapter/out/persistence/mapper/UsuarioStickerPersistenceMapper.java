package com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper;

import com.lean.mundisticker.domain.model.UsuarioSticker;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.UsuarioStickerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UsuarioStickerPersistenceMapper {

    public UsuarioSticker toDomain(UsuarioStickerEntity entity) {
        if (entity == null) return null;
        return new UsuarioSticker(
            entity.getId(),
            entity.getSticker() != null ? entity.getSticker().getId() : null,
            entity.getUsuario() != null ? entity.getUsuario().getId() : null,
            entity.getTipo(),
            entity.getCantidad()
        );
    }

    @Mapping(target = "usuario.id", source = "idUsuario")
    @Mapping(target = "sticker.id", source = "idSticker")
    public abstract UsuarioStickerEntity toEntity(UsuarioSticker domain);
}
