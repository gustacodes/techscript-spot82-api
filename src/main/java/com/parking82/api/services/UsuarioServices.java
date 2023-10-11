package com.parking82.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parking82.api.entities.Usuario;
import com.parking82.api.respository.UsuarioRespository;

@Service
public class UsuarioServices {

    private UsuarioRespository usuarioRespository;

    public UsuarioServices(UsuarioRespository usuarioRespository) {
        this.usuarioRespository = usuarioRespository;
    }

    public Usuario save(Usuario usuario) {
        return usuarioRespository.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRespository.findAll();
    }
    
}
