package com.trabalho.sd.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Negociacao implements Serializable {

    private String idProduto;
    private String idVendedor;
    private String idComprador;
    private Double valor;
    private Integer quantidade;

}
