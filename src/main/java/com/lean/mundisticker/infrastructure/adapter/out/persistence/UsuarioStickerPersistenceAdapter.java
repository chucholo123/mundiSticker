package com.lean.mundisticker.infrastructure.adapter.out.persistence;

import com.lean.mundisticker.domain.model.UsuarioSticker;
import com.lean.mundisticker.domain.model.UsuarioSticker.TipoSticker;
import com.lean.mundisticker.domain.port.out.UsuarioStickerRepository;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.UsuarioStickerEntity;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper.UsuarioStickerPersistenceMapper;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.repository.JpaUsuarioStickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioStickerPersistenceAdapter implements UsuarioStickerRepository {

    private final JpaUsuarioStickerRepository repository;
    private final UsuarioStickerPersistenceMapper mapper;

    @Override
    public UsuarioSticker save(UsuarioSticker usuarioSticker) {
        UsuarioStickerEntity entity = mapper.toEntity(usuarioSticker);
        UsuarioStickerEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<UsuarioSticker> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<UsuarioSticker> findByUsuarioId(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioSticker> findByStickerIdAndTipo(Long stickerId, TipoSticker tipo) {
        return repository.findByStickerIdAndTipo(stickerId, tipo).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<UUID> findUsuariosConStickerRepetido(Long stickerId) {
        return repository.findUsuariosConStickerRepetido(stickerId);
    }
}
