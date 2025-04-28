package com.zeroone.simlady_bot_server.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.chat-id}")
    private String chatId;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }


    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    public void sendMessage(String text) {

        SendMessage mensagem = new SendMessage();
        mensagem.setChatId(chatId);
        mensagem.setText(text);

        try {
            execute(mensagem);
        } catch (TelegramApiException e) {
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());;
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}