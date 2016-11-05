package org.agile.granada.telegrambot.commands;

import org.agile.granada.telegrambot.services.IResponseGenerator;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.logging.BotLogger;

public class ChatCommand extends BotCommand {

    private static final String LOGTAG = "CHATCOMMAND";
	public static final String CHAT_IDENTIFIER = "chat";
	public static final String CHAT_DESCRIPTION = "Di algo al bot";
	
	private IResponseGenerator responseGenerator;

    public ChatCommand(IResponseGenerator responseGenerator) {
        super(CHAT_IDENTIFIER, CHAT_DESCRIPTION);
        this.responseGenerator = responseGenerator;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        try {
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText(responseGenerator.answer(user, arguments));
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}