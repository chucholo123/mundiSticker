package com.lean.mundisticker.domain.port.out;

import com.lean.mundisticker.domain.model.Sticker;
import java.util.List;
import java.util.Optional;

public interface StickerRepository {
    Optional<Sticker> findById(Long id);
    List<Sticker> findAll();
    List<Sticker> findBySeleccion(String seleccion);
}
