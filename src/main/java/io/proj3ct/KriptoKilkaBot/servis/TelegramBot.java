package io.proj3ct.KriptoKilkaBot.servis;

import io.proj3ct.KriptoKilkaBot.config.Botconfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBot extends TelegramLongPollingBot {

   final Botconfig config;

   public TelegramBot(Botconfig config) {
       this.config = config;
   }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":

                    try {
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:

                    try {
                        sendMessage(chatId, "Прости, эта команда не поддерживается:((((" );
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

            }
        }


    }

    private void startCommandReceived(long chatId, String name) throws TelegramApiException {

        String answer = "Привет, " + name + ", чем я могу помочь?";

        sendMessage(chatId, answer);

    }
    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        }
        catch (TelegramApiException e){

        }
   }
}