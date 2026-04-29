package com.lean.mundisticker.infrastructure.adapter.out.persistence;

import com.lean.mundisticker.domain.model.Venta;
import com.lean.mundisticker.domain.model.Venta.EstadoVenta;
import com.lean.mundisticker.domain.port.out.VentaRepository;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.VentaEntity;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper.VentaPersistenceMapper;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.repository.JpaVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VentaPersistenceAdapter implements VentaRepository {

    private final JpaVentaRepository repository;
    private final VentaPersistenceMapper mapper;

    @Override
    public Venta save(Venta venta) {
        VentaEntity entity = mapper.toEntity(venta);
        VentaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Venta> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Venta> findByUsuarioEmisorId(UUID usuarioId) {
        return repository.findByEmisorId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Venta> findAbiertas() {
        return repository.findByEstado(EstadoVenta.ABIERTA).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
