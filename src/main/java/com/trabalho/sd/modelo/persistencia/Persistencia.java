package com.trabalho.sd.modelo.persistencia;

import com.trabalho.sd.controle.Comunicacao;
import com.trabalho.sd.modelo.Produto;
import com.trabalho.sd.modelo.Usuario;
import com.trabalho.sd.modelo.dao.ProdutoDAO;
import com.trabalho.sd.modelo.enums.EnumChannel;
import com.trabalho.sd.modelo.enums.EnumServico;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.util.Rsp;
import org.jgroups.util.RspList;

import java.util.List;
import java.util.Objects;

public class Persistencia extends ReceiverAdapter {

    private JChannel controlModelChannel ;
    private MessageDispatcher controlModelDispatcher;

    private ProdutoDAO produtoDAO;

    private void salvarArquivos() {
        produtoDAO.salvarArquivo();
    }

    private void criarGrupo() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.setMode(ResponseMode.GET_ALL);
        requestOptions.setAnycasting(false);

        Comunicacao comunicacao = new Comunicacao(EnumChannel.MODEL_TO_CONTROL, EnumServico.NOVO_MEMBRO_MODELO, null);
        Address cluster = null;
        Message newMessage = new Message(cluster, comunicacao);

        RspList<Comunicacao> list = null;
        try {
            list = controlModelDispatcher.castMessage(null, newMessage, requestOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Rsp<Comunicacao> x : list) {
            if(Objects.nonNull(x.getValue()) && x.getValue().channel == EnumChannel.MODEL_TO_MODEL && x.getSender().equals(this.controlModelChannel.getAddress())) {
                // this.sellers.set_sellers((List<Seller>)x.getValue().content.get(0));
                // this.products.set_products((List<Product>)x.getValue().content.get(1));
                // this.customers.set_customers((List<Customer>)x.getValue().content.get(2));
                break;
            }
        }

        return;
    }

    public void receive(Message msg) {
        System.out.println("" + msg.getSrc() + ": " + msg.getObject());
    }

    private boolean adicionarUsuario(Usuario usuario) {
        // TODO this.customers.add_customer(usuario);
        // TODO this.saveFiles();
        return true;
    }

    private boolean adicionarProduto(String idSeller, String produto, Float price, long amount, String descricao) {

        // Verifica se o produto nao esta no hashmap
        Produto novoProdoto = new Produto(produto, descricao);
        if (!produtoDAO.isExiste(produto)) produtoDAO.adicionarProduto(novoProdoto);

        //Decidir se vai incrementar caso o produto e o vendedor ja exista
        //Ou se simplesmente vai criar uma nova oferta

        // TODO Offer novaOferta = new Offer(idSeller, price, amount);
        // TODO produtoDAO.pegarProduto(produto).add_offer(novaOferta);

        // TODO novoProdoto.add_offer(novaOferta);
        // TODO this.sellers.get_seller(idSeller).products.put(product, novoProdoto);
        salvarArquivos();
        return true;
    }

}
