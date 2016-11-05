package org.agile.granada.telegrambot.commands;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.logging.BotLogger;

public class HelpCommand extends BotCommand {

	private static final String LOGTAG = "HELPCOMMAND";
	public static final String HELP_IDENTIFIER = "help";
	public static final String HELP_DESCRIPTION = "Muestra todos los comandos";
	public static final String HELP_LIST_HEADER = "<b>Help</b>\nEstos son los comandos registrados:\n\n";

	private final ICommandRegistry commandRegistry;

	public HelpCommand(ICommandRegistry commandRegistry) {
		super(HELP_IDENTIFIER, HELP_DESCRIPTION);
		this.commandRegistry = commandRegistry;
	}

	@Override
	public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
		try {
			SendMessage helpMessage = new SendMessage();
			helpMessage.setChatId(chat.getId().toString());
			helpMessage.enableHtml(true);
			helpMessage.setText(getHelpMessage());
			absSender.sendMessage(helpMessage);
		} catch (TelegramApiException e) {
			BotLogger.error(LOGTAG, e);
		}
	}

	private String getHelpMessage() {
		StringBuilder helpMessageBuilder = new StringBuilder(HELP_LIST_HEADER);

		for (BotCommand botCommand : commandRegistry.getRegisteredCommands()) {
			helpMessageBuilder.append(botCommand.toString()).append("\n\n");
		}
		return helpMessageBuilder.toString();
	}
}
