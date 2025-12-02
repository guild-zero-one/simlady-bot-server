package com.zeroone.simlady_bot_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class Pedido implements Serializable {

    @JsonProperty("pedidoId")
    private UUID pedidoId;

    @JsonProperty("usuarioId")
    private UUID usuarioId;

    @JsonProperty("nomeUsuario")
    private String nomeUsuario;

    @JsonProperty("contatosUsuario")
    private Set<String> contatosUsuario;

    @JsonProperty("dataCriacao")
    private LocalDateTime dataCriacao;

    @JsonProperty("status")
    private String status;

    @JsonProperty("valorTotal")
    private Double valorTotal;
}