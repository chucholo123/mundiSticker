package com.lean.mundisticker.domain.port.in.sticker;

import com.lean.mundisticker.domain.model.Sticker;
import java.util.List;
import java.util.Optional;

public interface ConsultarStickerUseCase {
    Optional<Sticker> porId(Long id);
    List<Sticker> todos();
    List<Sticker> porSeleccion(String seleccion);
}
