package com.trabalho.sd.view;

import com.trabalho.sd.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner s = new Scanner(System.in);
    List<Usuario> usuarios = new ArrayList<>();

    public void menuInicial() {
        int opc = 0;
        while (opc == 0) {
            System.out.println("--- MENU DE OPÇÕES ---\n");
            System.out.println("1) Criar conta");
            System.out.println("2) Logar na conta\n");
            System.out.print("Informe sua opção: ");
            opc = Integer.parseInt(s.nextLine());
            System.out.println("");
        }
        if(opc == 1) {
            criarConta();
        } else {
            logar();
        }
    }

    public void criarConta() {
        Usuario usuario = new Usuario();
        System.out.println("--- CRIAR USUÁRIO ---\n");
        System.out.print("Informe o CPF: ");
        usuario.setCpf(s.nextLine());
        System.out.print("Informe a senha: ");
        usuario.setSenha(s.nextLine());
        usuarios.add(usuario);
        System.out.println("\nUSUAŔIO " + usuario.getCpf() + " CADASTRADO COM SUCESSO !\n");
        menuInicial();
    }

    public void logar() {
        System.out.println("--- LOGAR NO SISTEMA ---\n");
        System.out.print("CPF: ");
        String cpf = s.nextLine();
        System.out.print("Senha: ");
        String senha = s.nextLine();

        if(usuarios.stream().anyMatch(u -> u.getCpf().equals(cpf) && u.getSenha().equals(senha))) {
            System.out.println("\nLOGIN REALIZADO COM SUCESSO !\n");
        } else {
            System.out.println("\nCPF e/ou senha incorreto(s)\n");
            logar();
        }
    }

}
