package com.parking82.api.services;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.parking82.api.entities.Vaga;
import com.parking82.api.respository.PagamentoRepository;
import com.parking82.api.respository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking82.api.entities.Cliente;
import com.parking82.api.respository.ClienteRepository;

@Service
public class ClienteServices {

    private ClienteRepository clienteRepository;

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public ClienteServices(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente save(Cliente cliente) {

        cliente.getVaga().setStatus("Ocupada");
        return clienteRepository.save(cliente);

    }

    public List<Cliente> list() {

        return clienteRepository.findAll();

    }

    public Cliente findByPlate(String placa) {

        return clienteRepository.findByPlate(placa);

    }

    public Vaga findById(Long id) {

        return vagaRepository.findById(id).get();

    }

    public Cliente saida(Cliente cliente) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime entrada = LocalTime.parse(cliente.getHoraEntrada().toString(), formatter);
        LocalTime saida = LocalTime.parse(cliente.getHoraSaida().toString(), formatter);

        Duration intervalo = Duration.between(entrada, saida);

        LocalTime localTime = LocalTime.of((int) intervalo.toHours(), (int) intervalo.toMinutes() % 60);
        String periodo = localTime.format(formatter);
        cliente.setPeriodo(periodo);

        double total = intervalo.toMinutes() % 60 * 0.0233333333333333;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String totalFormatter = decimalFormat.format(total);

        if (intervalo.toMinutes() % 60 <= 300) {
            cliente.getPagamento().setPagamento(7.0);
        } else {
            cliente.getPagamento().setPagamento(Double.parseDouble(totalFormatter));
        }

        pagamentoRepository.save(cliente.getPagamento());

        return cliente;
    }

    public Cliente recibo(Cliente cliente) {

        var vaga = findById(cliente.getId());
        clienteRepository.deleteById(cliente.getId());
        vagaRepository.deleteById(cliente.getId());

        return cliente;
    }

}
