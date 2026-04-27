package com.lean.mundisticker.application.mapper;

import com.lean.mundisticker.application.dto.inventario.request.UsuarioStickerRequest;
import com.lean.mundisticker.application.dto.inventario.response.UsuarioStickerResponse;
import com.lean.mundisticker.domain.model.UsuarioSticker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioStickerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    UsuarioSticker toDomain(UsuarioStickerRequest request);

    UsuarioStickerResponse toResponse(UsuarioSticker usuarioSticker);
}
