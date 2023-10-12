package com.techscript.spot82.entities;

import com.techscript.spot82.enums.Papel;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String password;
    private String confirmaPassword;
    @Enumerated(EnumType.STRING)
    private Papel papel;

}
