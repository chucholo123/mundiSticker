package com.lean.mundisticker.application.mapper;

import com.lean.mundisticker.application.dto.venta.response.VentaResponse;
import com.lean.mundisticker.domain.model.Venta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VentaMapper {
    VentaResponse toResponse(Venta venta);
}
