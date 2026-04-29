package com.lean.mundisticker.infrastructure.adapter.out.persistence.entity;

import com.lean.mundisticker.domain.model.Venta.EstadoVenta;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emisor_id", nullable = false)
    private UsuarioEntity emisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_id")
    private UsuarioEntity receptor;

    @ElementCollection
    @CollectionTable(name = "venta_stickers", joinColumns = @JoinColumn(name = "venta_id"))
    @Column(name = "sticker_id")
    private List<Long> stickers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVenta estado;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}
