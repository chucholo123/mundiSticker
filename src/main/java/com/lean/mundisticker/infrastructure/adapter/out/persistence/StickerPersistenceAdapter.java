package com.lean.mundisticker.infrastructure.adapter.out.persistence;

import com.lean.mundisticker.domain.model.Sticker;
import com.lean.mundisticker.domain.port.out.StickerRepository;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.StickerEntity;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper.StickerPersistenceMapper;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.repository.JpaStickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StickerPersistenceAdapter implements StickerRepository {

    private final JpaStickerRepository repository;
    private final StickerPersistenceMapper mapper;

    @Override
    public Optional<Sticker> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Sticker> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sticker> findBySeleccion(String seleccion) {
        return repository.findBySeleccion(seleccion).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
