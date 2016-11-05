package org.agile.granada.telegrambot;

import org.agile.granada.telegrambot.commands.ChatCommand;
import org.agile.granada.telegrambot.commands.HelpCommand;
import org.agile.granada.telegrambot.commands.StartCommand;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.logging.BotLogger;

public class ChatCommandBot extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "CHATCOMMANDBOT";

    public ChatCommandBot() {
        register(new ChatCommand());
        register(new StartCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId().toString());
            commandUnknownMessage.setText("El comando '" + message.getText() + "' no se reconoce. Aqui tienes la ayuda: ");
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId().toString());
                echoMessage.setText("Hey heres your message:\n" + message.getText());

                try {
                    sendMessage(echoMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
    	return "AgileGranada0_bot"; //System.getenv("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
    	return "276518186:AAGk1DsJy3Ef0IlmZLoXKnwQKOrtwf5BzNs";// System.getenv("BOT_TOKEN");
    }
}