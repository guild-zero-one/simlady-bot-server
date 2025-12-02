package com.zeroone.simlady_bot_server.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zeroone.simlady_bot_server.config.TelegramBot;
import com.zeroone.simlady_bot_server.dto.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class PedidoConsumer {

    private final TelegramBot bot;
    private final ObjectMapper objectMapper;

    public PedidoConsumer(TelegramBot bot) {
        this.bot = bot;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @RabbitListener(queues = "${rabbitmq.queue.orders}")
    public void consumirPedido(String mensagemJson) {
        try {
            log.info("📨 Mensagem recebida do RabbitMQ: {}", mensagemJson);

            Pedido pedido = objectMapper.readValue(mensagemJson, Pedido.class);

            log.info("✓ Pedido deserializado - ID: {}, Cliente: {}",
                    pedido.getPedidoId(), pedido.getNomeUsuario());

            String mensagemFormatada = formatarMensagem(pedido);
            bot.sendMessage(mensagemFormatada);

            log.info("✓ Mensagem enviada ao Telegram com sucesso");

        } catch (Exception e) {
            log.error("✗ Erro ao processar pedido: {}", e.getMessage(), e);
            try {
                bot.sendMessage("⚠️ Erro ao processar pedido: " + e.getMessage());
            } catch (Exception ex) {
                log.error("Erro ao enviar mensagem de erro: {}", ex.getMessage());
            }
        }
    }

    private String formatarMensagem(Pedido pedido) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append("🛍️ *NOVO PEDIDO* 🛍️\n\n");
        sb.append("🆔 ID do pedido: `").append(pedido.getPedidoId()).append("`\n");
        sb.append("👤 Cliente: *").append(pedido.getNomeUsuario()).append("*\n");
        sb.append("📊 Status: ").append(pedido.getStatus()).append("\n");

        if (pedido.getContatosUsuario() != null && !pedido.getContatosUsuario().isEmpty()) {
            sb.append("\n📱 *Contatos:*\n");
            pedido.getContatosUsuario().forEach(contato -> {
                sb.append("  • ").append(contato).append("\n");
            });
        } else {
            sb.append("\n⚠️ *Sem contatos cadastrados*\n");
        }

        sb.append("\n💵 *Valor Total: R$ ").append(String.format("%.2f", pedido.getValorTotal())).append("*\n");

        if (pedido.getDataCriacao() != null) {
            sb.append("\n📅 Data do pedido: ").append(pedido.getDataCriacao().format(formatter)).append("\n");
        }

        return sb.toString();
    }
}