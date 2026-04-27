package com.lean.mundisticker.domain.port.in.usuario;

import com.lean.mundisticker.domain.model.Usuario;
import org.locationtech.jts.geom.Point;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultarUsuarioUseCase {
    Optional<Usuario> porId(UUID id);
    Optional<Usuario> porNombre(String nombre);
    List<Usuario> cercanos(Point ubicacion, double distanciaMetros);
}
