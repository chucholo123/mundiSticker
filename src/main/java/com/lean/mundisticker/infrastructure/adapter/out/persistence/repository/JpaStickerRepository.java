package com.lean.mundisticker.infrastructure.adapter.out.persistence.repository;

import com.lean.mundisticker.infrastructure.adapter.out.persistence.entity.StickerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaStickerRepository extends JpaRepository<StickerEntity, Long> {
    List<StickerEntity> findBySeleccion(String seleccion);
}
