package com.techscript.spot82.controller;

import com.techscript.spot82.entities.Estacionamento;
import com.techscript.spot82.services.EstacionamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/estacionamentos")
public class EstacionamentoController {

    private EstacionamentoService service;

    @PostMapping
    public ResponseEntity<Estacionamento> salvar(@RequestBody Estacionamento estacionamento) {

        service.salvar(estacionamento);

        return ResponseEntity.status(HttpStatus.CREATED).body(estacionamento);
    }

}
