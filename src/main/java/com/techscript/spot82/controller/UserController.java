package com.techscript.spot82.controller;

import java.util.List;

import com.techscript.spot82.entities.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techscript.spot82.services.UsuarioServices;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios")
public class UserController {

    private UsuarioServices usuarioServices;

    @GetMapping
    public ResponseEntity<List<Usuario>> list() {
        return ResponseEntity.ok().body(usuarioServices.listar());
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServices.save(usuario));

    }

}