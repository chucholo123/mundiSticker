package com.lean.mundisticker.infrastructure.adapter.out.persistence.repository;

import com.lean.mundisticker.domain.model.Intercambio.EstadoIntercambio;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.IntercambioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaIntercambioRepository extends JpaRepository<IntercambioEntity, UUID> {
    List<IntercambioEntity> findByEmisorId(UUID emisorId);
    List<IntercambioEntity> findByReceptorId(UUID receptorId);
    List<IntercambioEntity> findByEstado(EstadoIntercambio estado);
}
