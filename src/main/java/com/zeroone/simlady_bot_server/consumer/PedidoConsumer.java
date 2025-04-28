package com.zeroone.simlady_bot_server.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroone.simlady_bot_server.config.TelegramBot;
import com.zeroone.simlady_bot_server.dto.Pedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class PedidoConsumer {

    private final TelegramBot bot;
    private final ObjectMapper objectMapper;

    public PedidoConsumer(TelegramBot bot) {
        this.bot = bot;
        this.objectMapper = new ObjectMapper();
    }


    @RabbitListener(queues = "${rabbitmq.queue.orders}")
    public void consumirPedido(String mensagemJson) {

        try {

            Pedido pedido = objectMapper.readValue(mensagemJson, Pedido.class);
            String mensagemFormatada = formatarMensagem(pedido);
            bot.sendMessage(mensagemFormatada);

        } catch (Exception e) {
            System.err.println("Erro ao processar pedido: " + e.getMessage());
        }
    }

    private String formatarMensagem(Pedido pedido) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append("🛍️ *NOVO PEDIDO* 🛍️\n\n");
        sb.append("🆔 ID do pedido: ").append(pedido.getId()).append("\n");
        sb.append("👤 Cliente: ").append(pedido.getNomeUsuario()).append("\n");

        if (!pedido.getContatosUsuario().isEmpty()) {
            sb.append("\n📱 *Contatos:*\n");
            pedido.getContatosUsuario().forEach(contato -> {
                sb.append("  • ").append(contato).append("\n");
            });
        } else {
            sb.append("\n⚠️ *Sem contatos cadastrados*\n");
        }

        sb.append("\n📦 *ITENS:*\n");
        pedido.getItens().forEach(item -> {
            sb.append("  ▫️ ").append(item.getQuantidade())
                    .append("x Produto ID ").append(item.getIdProduto())
                    .append(" (R$ ").append(String.format("%.2f", item.getPrecoUnitario()))
                    .append(")\n");
        });

        double total = pedido.getItens().stream()
                .mapToDouble(item -> item.getQuantidade() * item.getPrecoUnitario())
                .sum();

        sb.append("\n💵 *Total: R$ ").append(String.format("%.2f", total)).append("*").append("\n\n");
        sb.append("Data e hora do pedido: ").append(LocalDateTime.now().minusHours(3).format(formatter)).append("\n");

        return sb.toString();
    }
}