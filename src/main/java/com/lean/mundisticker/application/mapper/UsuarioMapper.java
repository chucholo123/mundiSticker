package com.lean.mundisticker.application.mapper;

import com.lean.mundisticker.application.dto.usuario.request.UsuarioRequest;
import com.lean.mundisticker.application.dto.usuario.response.UsuarioResponse;
import com.lean.mundisticker.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {

    @Autowired
    protected GeometryMapper geometryMapper;

    @Mapping(target = "ubicacion", expression = "java(geometryMapper.toPoint(request.latitud(), request.longitud()))")
    @Mapping(target = "id", ignore = true)
    public abstract Usuario toDomain(UsuarioRequest request);

    @Mapping(target = "latitud", expression = "java(geometryMapper.toLatitud(usuario.getUbicacion()))")
    @Mapping(target = "longitud", expression = "java(geometryMapper.toLongitud(usuario.getUbicacion()))")
    public abstract UsuarioResponse toResponse(Usuario usuario);
}
