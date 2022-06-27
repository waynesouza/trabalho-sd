package com.trabalho.sd.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Usuario implements Serializable {

    private String cpf;
    private String senha;
    private Float saldo = 100f;

}
