package com.parking82.api.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.parking82.api.respository.PagamentoRepository;
import com.parking82.api.respository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parking82.api.entities.Cliente;
import com.parking82.api.services.ClienteServices;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    private ClienteServices clienteServices;

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public ClienteController(ClienteServices clienteServices) {
        this.clienteServices = clienteServices;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok().body(clienteServices.list());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {

        cliente.setData(LocalDate.now());
        vagaRepository.save(cliente.getVaga());
        pagamentoRepository.save(cliente.getPagamento());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        cliente.setHoraEntrada(LocalTime.now().format(formatter));

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteServices.save(cliente));

    }

    @DeleteMapping("/fechar/{placa}")
    public ResponseEntity<?> finalizar(@PathVariable String placa) {

        Cliente cliente = clienteServices.findByPlate(placa);

        if(cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado. Por favor, verifique a placa informada");
        }

        clienteServices.recibo(cliente);

        LocalDate data = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "HH:mm");

        cliente.setHoraSaida(LocalTime.now().format(formatter));

        clienteServices.saida(cliente);

        cliente.getPagamento().setData(LocalDate.now().format(formatterDate));
        pagamentoRepository.save(cliente.getPagamento());

        return ResponseEntity.status(HttpStatus.OK).body("\t\t\t\t\t\t\t\t\t--------- RECIBO (PARKING 82) ---------\n\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\tDATA: " + LocalDate.now().format(formatterDate) +
                "\n\n\t\t\t\t\tCLIENTE: " + cliente.getNome() +
                "\t\t\t\t\t\tVEÍCULO: " + cliente.getVeiculo() +
                "\n\t\t\t\t\tPLACA: " + cliente.getPlaca() +
                "\t\t\t\t\t\t\t\t\tVAGA: " + cliente.getVaga().getSpot() +
                "\n\t\t\t\t\tENTRADA: " + cliente.getHoraEntrada() +
                "\t\t\t\t\t\t\t\t\tSAÍDA: " + cliente.getHoraSaida() +
                "\n\n\n\n\t\t\t\t\t\t\t\t\t\t\t\tPERMANÊNCIA: " + cliente.getPeriodo() +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t Á PAGAR: R$ "+ cliente.getPagamento().getPagamento() +
                "\n\n\t\t\t\tCNPJ: 99.107.370/0001-90 - Contato (82) 98162-1126 - E-mail parking82@contato.com" +
                "\n\t\t\t\t\t\t\t\tPaking 82 - Soluções em estacionamentos ©");
    }

}
