package com.lean.mundisticker.application.service;

import com.lean.mundisticker.domain.model.Usuario;
import com.lean.mundisticker.domain.model.Venta;
import com.lean.mundisticker.domain.port.out.UsuarioRepository;
import com.lean.mundisticker.domain.port.out.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private VentaService ventaService;

    private UUID vendedorId;
    private UUID compradorId;
    private Venta ventaAbierta;

    @BeforeEach
    void setUp() {
        vendedorId = UUID.randomUUID();
        compradorId = UUID.randomUUID();
        ventaAbierta = new Venta(UUID.randomUUID(), vendedorId, List.of(1L), BigDecimal.TEN);
    }

    @Test
    void debePublicarVentaAbierta() {
        when(ventaRepository.save(any(Venta.class))).thenAnswer(i -> i.getArguments()[0]);

        Venta resultado = ventaService.abierta(vendedorId, List.of(1L), BigDecimal.TEN);

        assertNotNull(resultado);
        assertEquals(Venta.EstadoVenta.ABIERTA, resultado.getEstado());
        verify(ventaRepository).save(any(Venta.class));
    }

    @Test
    void debeComprarVenta() {
        UUID ventaId = ventaAbierta.getId();
        when(ventaRepository.findById(ventaId)).thenReturn(Optional.of(ventaAbierta));

        ventaService.comprar(ventaId, compradorId);

        assertEquals(Venta.EstadoVenta.PENDIENTE, ventaAbierta.getEstado());
        assertEquals(compradorId, ventaAbierta.getIdUsuarioReceptor());
        verify(ventaRepository).save(ventaAbierta);
    }

    @Test
    void debeCompletarVenta() {
        Venta ventaPendiente = new Venta(UUID.randomUUID(), vendedorId, compradorId, List.of(1L), BigDecimal.TEN);
        Usuario vendedor = mock(Usuario.class);
        Usuario comprador = mock(Usuario.class);
        when(vendedor.getId()).thenReturn(vendedorId);
        when(comprador.getId()).thenReturn(compradorId);

        when(ventaRepository.findById(ventaPendiente.getId())).thenReturn(Optional.of(ventaPendiente));
        when(usuarioRepository.findById(vendedorId)).thenReturn(Optional.of(vendedor));
        when(usuarioRepository.findById(compradorId)).thenReturn(Optional.of(comprador));

        ventaService.completar(ventaPendiente.getId());

        assertEquals(Venta.EstadoVenta.COMPLETADA, ventaPendiente.getEstado());
        verify(ventaRepository).save(ventaPendiente);
        verify(usuarioRepository).save(vendedor);
        verify(usuarioRepository).save(comprador);
    }
}
