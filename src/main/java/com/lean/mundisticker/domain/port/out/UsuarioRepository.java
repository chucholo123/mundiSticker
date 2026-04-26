package com.lean.mundisticker.domain.port.out;

import com.lean.mundisticker.domain.model.Usuario;
import org.locationtech.jts.geom.Point;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    List<Usuario> findNearby(Point location, double distanceInMeters);
    Optional<Usuario> findByNombre(String nombre);
}
