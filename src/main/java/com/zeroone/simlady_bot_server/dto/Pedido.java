package com.zeroone.simlady_bot_server.dto;

import java.util.List;
import java.util.UUID;

public class Pedido {
    private UUID id;
    private String nomeUsuario;
    private List<String> contatosUsuario;
    private List<ItemPedido> itens;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public List<String> getContatosUsuario() {
        return contatosUsuario;
    }

    public void setContatosUsuario(List<String> contatosUsuario) {
        this.contatosUsuario = contatosUsuario;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}