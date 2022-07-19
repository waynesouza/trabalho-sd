package com.trabalho.sd.controle;

import com.trabalho.sd.modelo.enums.EnumChannel;
import com.trabalho.sd.modelo.enums.EnumServico;

import java.io.Serializable;
import java.util.ArrayList;

public class Comunicacao implements Serializable {

    @Override
    public String toString() {
        return "Comunicação [channel=" + channel + ", service=" + service + ", content=" + content + "]";
    }

    private static final long serialVersionUID = -585749592930578838L;
    public EnumChannel channel;
    public EnumServico service;
    public ArrayList<Object> content;

    public Comunicacao() {
        content = new ArrayList<Object>();
    }

    public Comunicacao(EnumChannel channel, EnumServico service, ArrayList<Object> content) {
        this.channel = channel;
        this.service = service;
        this.content = content;
    }

}
