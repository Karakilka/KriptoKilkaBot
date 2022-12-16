package io.proj3ct.KriptoKilkaBot.servis;

import io.proj3ct.KriptoKilkaBot.config.Botconfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j

@Component
public class TelegramBot extends TelegramLongPollingBot {


    final Botconfig config;
static final String HELP_TEXT = "/start - начать\n" +
        "/help - что умеет бот\n" +
        "/kurs - конвертер\n" +
        "/usd - курс доллара\n" +
        "/eur - курс евро\n" +
        "/btc - курс битка ₿\n" +
        "/eth - курс эфириума\n" +
        "/usdt - курс виртуального доллара";
    static final String wwww = "мне надо чтоб прога запускалась0";
    static final String ww = "мне надо чтоб прога запускалась1";
    static final String wwq = "мне надо чтоб прога запускалась2";
    static final String wwe = "мне надо чтоб прога запускалась3";
    static final String wwr = "мне надо чтоб прога запускалась4";
    static final String ebat_pomogite ="Курс какой валюты ты бы хотел узнать?))))\n" +
            "/usd - курс доллара\n" +
            "/eur - курс евро\n" +
            "/btc - курс битка ₿\n" +
            "/eth - курс эфириума\n" +
            "/usdt - курс виртуального доллара";
    public TelegramBot(Botconfig config) {
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "начать"));
        listofCommands.add(new BotCommand("/help", "что умеет бот"));
        listofCommands.add(new BotCommand("/kurs", "конвертер"));
        listofCommands.add(new BotCommand("/usd", "курс доллара"));
        listofCommands.add(new BotCommand("/eur", "курс евро"));
        listofCommands.add(new BotCommand("/btc", "курс битка ₿"));
        listofCommands.add(new BotCommand("/eth", "курс эфириума"));
        listofCommands.add(new BotCommand("/usdt", "курс виртуального доллара"));

        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(),null));
        } catch (TelegramApiException e) {
            log.error("Ошибка: " + e.getMessage());
        }
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

                case "/help":
                    try {
                        sendMessage(chatId, HELP_TEXT);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;


                case "/kurs":
                    try {
                        sendMessage(chatId,ebat_pomogite);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "/usd":
                    try {
                        sendMessage(chatId,wwww);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                case "/eur":
                    try {
                        sendMessage(chatId,ww);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "/btc":
                    try {
                        sendMessage(chatId, wwq );
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "/eth":
                    try {
                        sendMessage(chatId, wwe );
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "/usdt":
                    try {
                        sendMessage(chatId, wwr);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;



                default:

                    try {
                        sendMessage(chatId, "Прости, эта команда не поддерживается:((((");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

            }
        }


    }
List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
    private void startCommandReceived(long chatId, String name) throws TelegramApiException {

        String answer = "Привет, " + name + ", чем я могу помочь?\n" + "нажмите /help чтобы узнать команды";
        log.info("Replied to user" + name);
        sendMessage(chatId, answer);


    }

    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred" + e.getMessage());
        }
    }

}
