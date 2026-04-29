package com.lean.mundisticker.infrastructure.adapter.out.persistence;

import com.lean.mundisticker.domain.model.Intercambio;
import com.lean.mundisticker.domain.model.Intercambio.EstadoIntercambio;
import com.lean.mundisticker.domain.port.out.IntercambioRepository;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.IntercambioEntity;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper.IntercambioPersistenceMapper;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.repository.JpaIntercambioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IntercambioPersistenceAdapter implements IntercambioRepository {

    private final JpaIntercambioRepository repository;
    private final IntercambioPersistenceMapper mapper;

    @Override
    public Intercambio save(Intercambio intercambio) {
        IntercambioEntity entity = mapper.toEntity(intercambio);
        IntercambioEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Intercambio> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Intercambio> findByUsuarioEmisorId(UUID usuarioId) {
        return repository.findByEmisorId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Intercambio> findByUsuarioReceptorId(UUID usuarioId) {
        return repository.findByReceptorId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Intercambio> findAbiertos() {
        return repository.findByEstado(EstadoIntercambio.ABIERTO).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
