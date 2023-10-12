package com.techscript.spot82.services;

import com.techscript.spot82.entities.Estacionamento;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.Status;
import com.techscript.spot82.respository.EstacionamentoRepository;
import com.techscript.spot82.respository.UsuarioRespository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EstacionamentoService {

    private EstacionamentoRepository estacionamentoRepository;
    private UsuarioRespository usuarioRespository;
    private VagaRepository vagaRepository;

    public Estacionamento salvar(Estacionamento estacionamento) {

        usuarioRespository.save(estacionamento.getUsuario());

        for (long i = 1; i <= estacionamento.getVaga(); i++) {
            Vaga vaga = new Vaga();
            vaga.setQuantidadeDeVagas(i);
            vaga.setStatus(Status.DISPONIVEL);
            vagaRepository.save(vaga);
        }

        return estacionamentoRepository.save(estacionamento);
    }

}
