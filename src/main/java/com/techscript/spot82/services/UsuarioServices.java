package com.techscript.spot82.services;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.techscript.spot82.entities.Usuario;
import com.techscript.spot82.respository.UsuarioRespository;

@Service
@AllArgsConstructor
public class UsuarioServices {

    private UsuarioRespository usuarioRespository;

    public Usuario save(Usuario usuario) {
        return usuarioRespository.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRespository.findAll();
    }
    
}
