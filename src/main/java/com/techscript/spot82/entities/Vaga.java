package com.techscript.spot82.entities;

import com.techscript.spot82.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vagaDoCliente;
    @Enumerated(EnumType.STRING)
    private Status status;

}
