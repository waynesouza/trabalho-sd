package com.trabalho.sd.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Produto implements Serializable {

    private String id;
    private String titulo;
    private Integer qtdEstoque;

    private List<Double> precos;

    public Produto(String titulo, Integer qtdEstoque, List<Double> precos) {
        this.titulo = titulo;
//        this.questions = new HashMap<String, Question>();
//        this.offers = new HashMap<String, Offer>();
        this.qtdEstoque = qtdEstoque;
        this.precos = precos;
    }
}
