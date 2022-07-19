package com.trabalho.sd.modelo.persistencia;

import com.trabalho.sd.controle.Comunicacao;
import com.trabalho.sd.modelo.Produto;
import com.trabalho.sd.modelo.Usuario;
import com.trabalho.sd.modelo.dao.ProdutoDAO;
import com.trabalho.sd.modelo.dao.UsuarioDAO;
import com.trabalho.sd.modelo.enums.EnumChannel;
import com.trabalho.sd.modelo.enums.EnumServico;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.util.Rsp;
import org.jgroups.util.RspList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Persistencia extends ReceiverAdapter implements RequestHandler, Serializable {

    private JChannel controleModeloChannel ;
    private MessageDispatcher controleModeloDispatcher;

    private ProdutoDAO produtoDAO;
    private UsuarioDAO usuarioDAO;

    public Persistencia() throws Exception {
        produtoDAO = new ProdutoDAO();
        usuarioDAO = new UsuarioDAO();

        try {
            controleModeloChannel = new JChannel(); // TODO <-- adicionar XML
        } catch (Exception e) {
            e.printStackTrace();
        }

        controleModeloChannel.setReceiver(this);
        controleModeloDispatcher = new MessageDispatcher(controleModeloChannel, null, null, null);

        controleModeloChannel.connect("ControleModeloChannel");
        criarGrupo();
    }

    private void salvarArquivos() {
        produtoDAO.salvarArquivo();
    }

    private void criarGrupo() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.setMode(ResponseMode.GET_ALL);
        requestOptions.setAnycasting(false);

        Comunicacao comunicacao = new Comunicacao(EnumChannel.MODELO_PARA_CONTROLE, EnumServico.NOVO_MEMBRO_MODELO, null);
        Address cluster = null;
        Message newMessage = new Message(cluster, comunicacao);

        RspList<Comunicacao> list = null;
        try {
            list = controleModeloDispatcher.castMessage(null, newMessage, requestOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Rsp<Comunicacao> l : list) {
            if(Objects.nonNull(l.getValue()) && l.getValue().channel.equals(EnumChannel.MODELO_PARA_MODELO) && l.getSender().equals(controleModeloChannel.getAddress())) {
                 produtoDAO.atualizarProdutos((List<Produto>) l.getValue().content.get(0));
                break;
            }
        }

        return;
    }

    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }

    private boolean adicionarUsuario(Usuario usuario) {
        adicionarUsuario(usuario);
        salvarArquivos();
        return true;
    }

    private boolean adicionarProduto(String cpfVendedor, String produto, Double preco, Integer qtd, String descricao) {

        Produto novoProdoto = new Produto(produto, descricao);
        if (!produtoDAO.isExiste(produto)) produtoDAO.adicionarProduto(novoProdoto);

        salvarArquivos();
        return true;
    }

    public Object handle(Message m) throws Exception {
        Comunicacao msg = (Comunicacao) m.getObject();
        System.out.println("Mensagem pra persistencia: \n" + msg);

        Comunicacao response = new Comunicacao();

        ArrayList<Object> content = new ArrayList<Object>();

        if(msg.channel == EnumChannel.CONTROLE_PARA_MODELO) {
            if(msg.service == EnumServico.SALVAR_PRODUTO) {
                // TODO content.add(adicionarProduto());
            } else if(msg.service == EnumServico.SALVAR_USUARIO) {
                content.add(adicionarUsuario((Usuario) msg.content.stream().findFirst().get()));
            } else if(msg.service == EnumServico.PEGAR_PRODUTOS) {
                content.add(produtoDAO.pegarProdutos());
            } else if(msg.service == EnumServico.PEGAR_USUARIOS) {
                content.add(usuarioDAO.pegarUsuarios());
            } else if(msg.service == EnumServico.PRODUTO_EXISTE) {
                content.add(produtoDAO.isExiste((String) msg.content.stream().findFirst().get()));
            } else if(msg.service == EnumServico.USUARIO_EXSITE) {
                content.add(usuarioDAO.isExiste((String) msg.content.stream().findFirst().get()));
            }
            // TODO compra, venda e login

            response.service = msg.service;
            response.channel = EnumChannel.MODELO_PARA_CONTROLE;
            response.content = content;
        } else if(msg.channel == EnumChannel.MODELO_PARA_CONTROLE) {

            if(msg.service == EnumServico.NOVO_MEMBRO_MODELO) {
                response.channel = EnumChannel.MODELO_PARA_MODELO;
                response.service = EnumServico.TRANSFERENCIA_ESTADO;

                content.add(produtoDAO.pegarProdutos());
            }
            response.content = content;
        }

        System.out.println("Resposta do modelo: "+ response + "\n");

        return response;
    }

}
