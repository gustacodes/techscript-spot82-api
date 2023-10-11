package com.parking82.api.services;

import com.parking82.api.entities.Usuario;
import com.parking82.api.respository.UsuarioRespository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    private final UsuarioRespository usuarioRespository;

    public UserDetailService(UsuarioRespository usuarioRespository) {
        this.usuarioRespository = usuarioRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> user = usuarioRespository.email(email);

        if (user.isPresent()) {
            return user.get();
        }

        throw new UsernameNotFoundException("No user found for " + email + ".");
    }
}
