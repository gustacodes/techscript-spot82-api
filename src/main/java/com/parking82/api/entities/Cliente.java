package com.parking82.api.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import lombok.Data;

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
    @JoinColumn(name = "payment_id")
    private Pagamento pagamento;
    @ManyToOne
    @JoinColumn(name = "vagancy_id")
    private Vaga vaga;

}
