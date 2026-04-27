package com.lean.mundisticker.application.mapper;

import com.lean.mundisticker.application.dto.intercambio.response.IntercambioResponse;
import com.lean.mundisticker.domain.model.Intercambio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IntercambioMapper {
    IntercambioResponse toResponse(Intercambio intercambio);
}
