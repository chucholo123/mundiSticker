package com.lean.mundisticker.domain.port.in.usuario;

import org.locationtech.jts.geom.Point;
import java.util.UUID;

public interface ActualizarPerfilUsuarioUseCase {
    void ejecutar(UUID id, String nombre, String contrasena, Point ubicacion);
}
