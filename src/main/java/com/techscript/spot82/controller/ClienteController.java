package com.techscript.spot82.controller;

import com.techscript.spot82.configuracao.GravarDados;
import com.techscript.spot82.entities.Cliente;
import com.techscript.spot82.exceptions.ClienteExceptions;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import com.techscript.spot82.services.ClienteServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteServices clienteServices;

    private VagaRepository vagaRepository;

    private PagamentoRepository pagamentoRepository;

    private GravarDados gravarDados;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {

        List<Cliente> clientes = clienteServices.list();

        if (clientes.isEmpty()) {
            throw new ClienteExceptions("Não há clientes no momento.");
        }

        return ResponseEntity.ok().body(clienteServices.list());
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid Cliente cliente, BindingResult result) throws ExecutionException, InterruptedException {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().build();

        }

        gravarDados.gravarDadosDoUsuario(cliente, "clientes");

        clienteServices.save(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<?> finalizar(@PathVariable String placa) {

        Cliente cliente = clienteServices.findByPlate(placa);

        if (cliente == null) {
            throw new ClienteExceptions("Placa inexistente no sistema! Verifique e tente novamente.");
        }

        clienteServices.recibo(cliente);
        clienteServices.saida(cliente);

        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

}
