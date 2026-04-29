package com.lean.mundisticker.infrastructure.adapter.out.persistence.repository;

import com.lean.mundisticker.domain.model.Venta.EstadoVenta;
import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaVentaRepository extends JpaRepository<VentaEntity, UUID> {
    List<VentaEntity> findByEmisorId(UUID emisorId);
    List<VentaEntity> findByEstado(EstadoVenta estado);
}
