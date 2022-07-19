package com.trabalho.sd.modelo.dao;

import com.trabalho.sd.modelo.Produto;
import com.trabalho.sd.util.JsonBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements Serializable, JsonBase {

    private static final String NOME_ARQUIVO = "produtos.json";

    private List<Produto> produtos = new ArrayList<Produto>();

    public ProdutoDAO() {
        this.carregarArquivo();
    }

    private void carregarArquivo() {
        readJson(NOME_ARQUIVO, produtos.getClass());
    }

    public synchronized void salvarArquivo() {
        writeJson(produtos, NOME_ARQUIVO);
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public Produto pegarProduto(String produto) {
        return produtos.stream().filter(p -> p.getId().equals(produto)).findFirst().orElse(null);
    }

    public List<Produto> pegarProdutos() {
        return produtos;
    }

    public void atualizarProdutos(List<Produto> ps){
        produtos = ps;
    }

    public boolean isExiste(String id) {
        return produtos.stream().anyMatch(p -> p.getId().equals(id));
    }

}
