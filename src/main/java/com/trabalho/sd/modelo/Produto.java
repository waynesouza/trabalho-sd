package com.trabalho.sd.modelo;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
public class Produto implements Serializable {

    private String id;
    private String descricao;
    private Integer qtdEstoque;

    public Produto(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
//        this.questions = new HashMap<String, Question>();
//        this.offers = new HashMap<String, Offer>();
        this.qtdEstoque = 0;
    }
}
