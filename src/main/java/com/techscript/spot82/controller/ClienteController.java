package com.techscript.spot82.controller;

import com.techscript.spot82.entities.Cliente;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.Status;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import com.techscript.spot82.services.ClienteServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteServices clienteServices;

    private VagaRepository vagaRepository;

    private PagamentoRepository pagamentoRepository;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok().body(clienteServices.list());
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {

        Cliente clt = clienteServices.save(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(clt);

    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<?> finalizar(@PathVariable String placa) {

        Cliente cliente = clienteServices.findByPlate(placa);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado. Por favor, verifique a placa informada");
        }

        clienteServices.recibo(cliente);
        clienteServices.saida(cliente);

        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

}
