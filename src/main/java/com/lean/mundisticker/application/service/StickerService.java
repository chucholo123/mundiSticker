package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.model.Sticker;
import com.lean.mundisticker.domain.port.in.sticker.ConsultarStickerUseCase;
import com.lean.mundisticker.domain.port.out.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StickerService implements ConsultarStickerUseCase {

    private final StickerRepository stickerRepository;

    @Override
    public Optional<Sticker> porId(Long id) {
        return stickerRepository.findById(id);
    }

    @Override
    public List<Sticker> todos() {
        return stickerRepository.findAll();
    }

    @Override
    public List<Sticker> porSeleccion(String seleccion) {
        return stickerRepository.findBySeleccion(seleccion);
    }
}
