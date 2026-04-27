package com.lean.mundisticker.domain.port.in.usuario;

import com.lean.mundisticker.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {
    Usuario ejecutar(Usuario usuario);
}
