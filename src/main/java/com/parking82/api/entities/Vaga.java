package com.parking82.api.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Integer vaga;

}
