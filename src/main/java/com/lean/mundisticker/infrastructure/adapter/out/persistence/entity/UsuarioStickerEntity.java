package com.lean.mundisticker.infrastructure.adapter.out.persistence.entity;

import com.lean.mundisticker.domain.model.UsuarioSticker.TipoSticker;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "usuario_stickers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioStickerEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sticker_id", nullable = false)
    private StickerEntity sticker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSticker tipo;

    @Column(nullable = false)
    private int cantidad;
}
