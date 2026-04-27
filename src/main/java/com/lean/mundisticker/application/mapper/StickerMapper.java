package com.lean.mundisticker.application.mapper;

import com.lean.mundisticker.application.dto.sticker.response.StickerResponse;
import com.lean.mundisticker.domain.model.Sticker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    StickerResponse toResponse(Sticker sticker);
}
