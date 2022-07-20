package com.trabalho.sd.view;

import com.trabalho.sd.modelo.Produto;
import com.trabalho.sd.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Menu {

    Scanner s = new Scanner(System.in);
    List<Usuario> usuarios = new ArrayList<>();
    List<Produto> produtos = new ArrayList<>();

    public void menuInicial() {
        int opc = 0;
        while (opc == 0) {
            System.out.println("\n----- MENU INICIAL ------\n");
            System.out.println("1) Criar conta");
            System.out.println("2) Logar na conta");
            System.out.println("3) Sair da conta\n");
            System.out.print("Informe sua opção: ");
            opc = Integer.parseInt(s.nextLine());
            System.out.println("");
        }
        if(opc == 1) {
            criarConta();
        } else if(opc == 2) {
            logar();
        } else {
            System.out.println("Logout realizado com sucesso !!");
            menuInicial();
        }
    }

    public void criarConta() {// travar na verificação do cpf(apenas um usuario por cliente)
        Usuario usuario = new Usuario();
        System.out.println("------ CRIAR USUÁRIO ------\n");
        System.out.print("Informe o CPF: ");
        usuario.setCpf(s.nextLine());
        System.out.print("Informe a senha: ");
        usuario.setSenha(s.nextLine());
        if(usuarios.stream().anyMatch(u -> u.getCpf().equals(usuario.getCpf()))) {
            System.out.println("CPF JÁ CADASTRADO\n");
            menuInicial();
        } else {
            usuarios.add(usuario);
            System.out.println("\nUSUAŔIO " + usuario.getCpf() + " CADASTRADO COM SUCESSO !\n");
            menuInicial();
        }
    }

    public void logar() {
        System.out.println("------ LOGAR NO SISTEMA ------\n");
        System.out.print("CPF: ");
        String cpf = s.nextLine();
        System.out.print("Senha: ");
        String senha = s.nextLine();

        if(usuarios.stream().anyMatch(u -> u.getCpf().equals(cpf) && u.getSenha().equals(senha))) {
            System.out.println("\nLOGIN REALIZADO COM SUCESSO !\n");

            String opc = "0";
            while (Objects.equals(opc, "0")) {
                System.out.println("Deseja cadastrar um produto?");
                System.out.print("1 - Sim (Realize o cadastro)\n");
                System.out.println("2 - Não (Siga para o menu principal)");
                System.out.print("\tSua escolha: ");
                opc = s.nextLine();

                if(Objects.equals(opc, "")){
                    opc = s.nextLine();
                    System.out.println("");
                }
            }

            if(Objects.equals(opc, "1")) {
                cadastrarProduto();
            } else {
                menuPrincipal();
            }
        } else {
            System.out.println("\nCPF e/ou senha incorreto(s)\n");
            logar();
        }
    }

    public void menuPrincipal(){
        String opc = "0";
        while (Objects.equals(opc, "0")) {
            System.out.println("\n--------- MENU PRINCIPAL ---------\n");
            System.out.println("1) Consultar saldo da carteira");
            System.out.println("2) Anunciar produto(s)");
            System.out.println("3) Pesquisar produto");
            System.out.println("4) Comprar Produto");
            System.out.println("5) Listar estoque do produto");
            System.out.println("6) Consultar histórico de operações");
            System.out.println("7) Retornar ao menu inicial");
            System.out.print("Informe sua opção: ");
            opc = s.nextLine();

            if (Objects.equals(opc, "")){
                opc = s.nextLine();
                System.out.println("");
            }
        }

        if(Objects.equals(opc, "1")) {
            System.out.print("\tInforme o produto: ");

        } else if (Objects.equals(opc, "2")) {
            System.out.print("\tInforme o produto: ");

            menuPrincipal();
        } else if (Objects.equals(opc, "3")) {
            System.out.print("\tInforme o produto: ");

            menuPrincipal();
        } else {
            menuInicial();
        }
    }

    public void cadastrarProduto(){
        Produto produto = new Produto();

        System.out.println("\n------ CADASTRAR PRODUTO ------\n");
        System.out.print("Informe o titulo do produto: ");
        produto.setTitulo(s.nextLine());

        produtos.forEach( item -> {
            if (item.getTitulo().equals(produto.getTitulo())) {
                System.out.print("Informe a quantidade do produto: ");
                item.setQtdEstoque(item.getQtdEstoque() + s.nextInt());
                produto.setQtdEstoque(item.getQtdEstoque());
                System.out.print("Informe o preço do produto: ");
                //
                List<Double> precos = item.getPrecos();
                precos.add(s.nextDouble());
                item.setPrecos(precos);
                produto.setPrecos(item.getPrecos());
                //
                item.setTitulo(produto.getTitulo());
            }
        });

        if (produto.getQtdEstoque() == null){
            System.out.print("Informe a quantidade do produto: ");
            produto.setQtdEstoque(s.nextInt());
            System.out.print("Informe o preço do produto: ");
            List<Double> precos = new ArrayList<>();
            precos.add(s.nextDouble());
            produto.setPrecos(precos);

            produtos.add(produto);
        }
        System.out.println("\nPRODUTO " + produto.getTitulo().toUpperCase() + " CADASTRADO COM SUCESSO !");
        menuPrincipal();
    }

}
