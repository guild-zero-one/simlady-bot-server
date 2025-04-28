package com.zeroone.simlady_bot_server.dto;

import java.util.List;

public class Pedido {
    private Long id;
    private String nomeUsuario;
    private List<String> contatosUsuario;
    private List<ItemPedido> itens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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