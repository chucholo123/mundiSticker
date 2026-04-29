package com.lean.mundisticker.infrastructure.adapter.out.persistence.repository;

import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByNombre(String nombre);

    @Query(value = "SELECT * FROM usuarios u WHERE ST_DWithin(u.ubicacion, :location, :distance)", nativeQuery = true)
    List<UsuarioEntity> findNearby(@Param("location") Point location, @Param("distance") double distanceInMeters);
}
