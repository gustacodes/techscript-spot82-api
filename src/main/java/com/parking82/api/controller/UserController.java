package com.parking82.api.controller;

import java.util.List;

import com.parking82.api.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.parking82.api.services.UsuarioServices;

@RestController
@RequestMapping("/usuario")
public class UserController {

    private UsuarioServices usuarioServices;

    @Autowired
    private PasswordEncoder encoder;

    public UserController(UsuarioServices usuarioServices) {
        this.usuarioServices = usuarioServices;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> list() {
        return ResponseEntity.ok().body(usuarioServices.listar());
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {

        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.setConfirmaPassword(encoder.encode(usuario.getConfirmaPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServices.save(usuario));

    }

}