package org.agile.granada.telegrambot;

import org.agile.granada.telegrambot.commands.ChatCommand;
import org.agile.granada.telegrambot.commands.HelpCommand;
import org.agile.granada.telegrambot.commands.StartCommand;
import org.agile.granada.telegrambot.services.IResponseGenerator;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.logging.BotLogger;

public class ChatCommandBot extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "CHATCOMMANDBOT";

    public ChatCommandBot(IResponseGenerator responseGenerator) {
        register(new ChatCommand(responseGenerator));
        register(new StartCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            try {
                SendMessage commandUnknownMessage = new SendMessage();
                commandUnknownMessage.setChatId(message.getChatId().toString());
                commandUnknownMessage.setText("El comando '" + message.getText() + "' no se reconoce: ");
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) processMessage(update.getMessage());
    }

    private void processMessage(Message message) {
        if (! message.hasText()) return;
        
        try {
            SendMessage echoMessage = new SendMessage();
            echoMessage.setChatId(message.getChatId().toString());
            echoMessage.setText("Este es tu mensaje:\n" + message.getText());
            sendMessage(echoMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
	}

	@Override
    public String getBotUsername() {
		return Config.getBotUsername();
    }

    @Override
    public String getBotToken() {
		return Config.getBotToken();
    }
}