package com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper;

import com.lean.mundisticker.domain.model.Intercambio;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.IntercambioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class IntercambioPersistenceMapper {

    public Intercambio toDomain(IntercambioEntity entity) {
        if (entity == null) return null;

        Intercambio intercambio = new Intercambio(
            entity.getId(),
            entity.getEmisor() != null ? entity.getEmisor().getId() : null,
            entity.getReceptor() != null ? entity.getReceptor().getId() : null,
            entity.getStickersEmisor(),
            entity.getStickersReceptor()
        );
        intercambio.cambiarEstado(entity.getEstado());
        return intercambio;
    }

    @Mapping(target = "emisor.id", source = "idUsuarioEmisor")
    @Mapping(target = "receptor.id", source = "idUsuarioReceptor")
    public abstract IntercambioEntity toEntity(Intercambio domain);
}
