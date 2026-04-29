package com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper;

import com.lean.mundisticker.domain.model.Venta;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class VentaPersistenceMapper {

    public Venta toDomain(VentaEntity entity) {
        if (entity == null) return null;
        
        Venta venta = new Venta(
            entity.getId(),
            entity.getEmisor() != null ? entity.getEmisor().getId() : null,
            entity.getReceptor() != null ? entity.getReceptor().getId() : null,
            entity.getStickers(),
            entity.getPrecio()
        );
        venta.cambiarEstado(entity.getEstado());
        return venta;
    }

    @Mapping(target = "emisor.id", source = "idUsuarioEmisor")
    @Mapping(target = "receptor.id", source = "idUsuarioReceptor")
    public abstract VentaEntity toEntity(Venta domain);
}
