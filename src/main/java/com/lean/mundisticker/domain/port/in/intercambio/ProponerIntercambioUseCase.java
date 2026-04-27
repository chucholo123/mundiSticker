package com.lean.mundisticker.domain.port.in.intercambio;

import com.lean.mundisticker.domain.model.Intercambio;
import java.util.List;
import java.util.UUID;

public interface ProponerIntercambioUseCase {
    Intercambio abierta(UUID emisorId, List<Long> stickersOfrecidos, List<Long> stickersBuscados);
    Intercambio directa(UUID emisorId, UUID receptorId, List<Long> stickersOfrecidos, List<Long> stickersSolicitados);
}
