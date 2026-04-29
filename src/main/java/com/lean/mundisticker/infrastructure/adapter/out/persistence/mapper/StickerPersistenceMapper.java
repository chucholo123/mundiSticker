package com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper;

import com.lean.mundisticker.domain.model.Sticker;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.StickerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class StickerPersistenceMapper {

    public Sticker toDomain(StickerEntity entity) {
        if (entity == null) return null;
        return new Sticker(
            entity.getId(),
            entity.getNombre(),
            entity.getSeleccion()
        );
    }

    @Mapping(target = "nombre", source = "numero")
    public abstract StickerEntity toEntity(Sticker domain);
}
