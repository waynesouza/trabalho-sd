package com.trabalho.sd.modelo.dao;

import com.trabalho.sd.modelo.Usuario;
import com.trabalho.sd.util.JsonBase;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Service
public class UsuarioDAO implements Serializable, JsonBase {

    private static final String NOME_ARQUIVO = "usuarios.json";

    private List<Usuario> usuarios = new ArrayList<Usuario>();

    private void carregarArquivo() {
        readJson(NOME_ARQUIVO, usuarios.getClass());
    }

    public synchronized void salvarArquivo() {
        writeJson(this, NOME_ARQUIVO);
    }

    public void pagarCompra(String cpfVendedor, Double valor) {
        usuarios.stream().forEach(u -> {
            if (u.getCpf().equals(cpfVendedor)) u.setSaldo(u.getSaldo() - valor);
        });
    }

    public void receberVenda(String cpfVendedor, Double valor) {
        usuarios.stream().forEach(u -> {
            if (u.getCpf().equals(cpfVendedor)) u.setSaldo(u.getSaldo() + valor);
        });
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario pegarUsuario(String cpfUsuario) {
        return usuarios.stream().filter(u -> u.getCpf().equals(cpfUsuario)).findFirst().orElse(null);
    }

    public List<Usuario> pegarUsuarios() {
        return usuarios;
    }

    public void atualizarUsuarios(List<Usuario> us){
        usuarios = us;
    }

    public boolean isExiste(String cpfUsuario) {
        return usuarios.stream().anyMatch(u -> u.getCpf().equals(cpfUsuario));
    }
    
}
