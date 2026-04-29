package com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper;

import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioPersistenceMapper {
    Usuario toDomain(UsuarioEntity entity);
    UsuarioEntity toEntity(Usuario domain);
}
