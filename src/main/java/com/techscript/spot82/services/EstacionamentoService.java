package com.techscript.spot82.services;

import com.techscript.spot82.entities.Estacionamento;
import com.techscript.spot82.respository.EstacionamentoRepository;
import com.techscript.spot82.respository.UsuarioRespository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EstacionamentoService {

    private EstacionamentoRepository repository;
    private UsuarioRespository usuarioRespository;

    public Estacionamento salvar(Estacionamento estacionamento) {

        usuarioRespository.save(estacionamento.getUsuario());
        return repository.save(estacionamento);

    }

}
