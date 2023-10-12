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

        cliente.setData(LocalDate.now());

        pagamentoRepository.save(cliente.getPagamento());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        cliente.setHoraEntrada(LocalTime.now().format(formatter));

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

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        cliente.setHoraSaida(LocalTime.now().format(formatter));

        clienteServices.saida(cliente);

        cliente.getPagamento().setData(LocalDate.now().format(formatterDate));
        pagamentoRepository.save(cliente.getPagamento());

        return ResponseEntity.status(HttpStatus.OK).body("\t\t\t\t\t\t\t\t\t--------- RECIBO (PARKING 82) ---------\n\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\tDATA: " + LocalDate.now().format(formatterDate) +
                "\n\n\t\t\t\t\tCLIENTE: " + cliente.getNome() +
                "\t\t\t\t\t\tVEÍCULO: " + cliente.getVeiculo() +
                "\n\t\t\t\t\tPLACA: " + cliente.getPlaca() +
                "\t\t\t\t\t\t\t\t\tVAGA: " + cliente.getVagaCliente().getQuantidadeDeVagas() +
                "\n\t\t\t\t\tENTRADA: " + cliente.getHoraEntrada() +
                "\t\t\t\t\t\t\t\t\tSAÍDA: " + cliente.getHoraSaida() +
                "\n\n\n\n\t\t\t\t\t\t\t\t\t\t\t\tPERMANÊNCIA: " + cliente.getPeriodo() +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t Á PAGAR: R$ " + cliente.getPagamento().getPagamento() +
                "\n\n\t\t\t\tCNPJ: 99.107.370/0001-90 - Contato (82) 98162-1126 - E-mail parking82@contato.com" +
                "\n\t\t\t\t\t\t\t\tPaking 82 - Soluções em estacionamentos ©");
    }

}
