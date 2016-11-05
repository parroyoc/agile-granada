package org.agile.granada.telegrambot.commands;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.logging.BotLogger;

public class StartCommand extends BotCommand {

	public static final String LOGTAG = "STARTCOMMAND";
	public static final String HELLO_IDENTIFIER = "start";
	public static final String HELLO_MESSAGE = "Este es un bot de prueba para Agile Granada. Escribe /chat 'algun texto' para que te responda";
	public static final String HELLO_DESCRIPTION = "Este comando arranca el bot";

	public StartCommand() {
		super(HELLO_IDENTIFIER, HELLO_DESCRIPTION);
	}

	@Override
	public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
		try {
			SendMessage answer = new SendMessage();
			answer.setChatId(chat.getId().toString());
			answer.setText(getStartMessage(user));
			absSender.sendMessage(answer);
		} catch (TelegramApiException e) {
			BotLogger.error(LOGTAG, e);
		}
	}

	private String getStartMessage(User user) {
		StringBuilder messageBuilder = new StringBuilder();

		String userName = user.getFirstName() + " " + user.getLastName();
		messageBuilder.append("Hola ").append(userName).append(".\n");
		messageBuilder.append(HELLO_MESSAGE);
		return messageBuilder.toString();
	}
}