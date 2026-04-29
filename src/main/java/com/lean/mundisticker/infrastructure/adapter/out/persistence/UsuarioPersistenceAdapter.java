package com.lean.mundisticker.infrastructure.adapter.out.persistence;

import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.port.out.UsuarioRepository;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.mapper.UsuarioPersistenceMapper;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository repository;
    private final UsuarioPersistenceMapper mapper;

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        UsuarioEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> findNearby(Point location, double distanceInMeters) {
        return repository.findNearby(location, distanceInMeters).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> findByNombre(String nombre) {
        return repository.findByNombre(nombre).map(mapper::toDomain);
    }
}
