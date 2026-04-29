package com.lean.mundisticker.infrastructure.adapter.out.persistence.entity;

import com.lean.mundisticker.domain.model.Intercambio.EstadoIntercambio;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "intercambios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntercambioEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emisor_id", nullable = false)
    private UsuarioEntity emisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_id")
    private UsuarioEntity receptor;

    @ElementCollection
    @CollectionTable(name = "intercambio_stickers_emisor", joinColumns = @JoinColumn(name = "intercambio_id"))
    @Column(name = "sticker_id")
    private List<Long> stickersEmisor;

    @ElementCollection
    @CollectionTable(name = "intercambio_stickers_receptor", joinColumns = @JoinColumn(name = "intercambio_id"))
    @Column(name = "sticker_id")
    private List<Long> stickersReceptor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoIntercambio estado;
}
