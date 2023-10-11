package com.parking82.api.respository;

import com.parking82.api.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRespository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> email(String email);

}
