package com.trabalho.sd.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements Serializable {

    private String cpf;
    private String senha;
    private Double saldo = 100.0;

}
