package com.techscript.spot82.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String veiculo;
    private String placa;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;
    private String horaEntrada;
    private String horaSaida;
    private String periodo;
    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;
    @ManyToOne
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

}
