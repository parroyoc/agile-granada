package org.agile.granada.telegrambot.services;

import org.telegram.telegrambots.api.objects.User;

public class ResponseGeneratorService implements IResponseGenerator {

	public String answer(User user, String[] arguments) {
		if (arguments != null && arguments.length > 0) {
			return answer(user, String.join(" ", arguments));
		}

		return "Hola " + user.getFirstName() + " " + user.getLastName();
	}

	public String answer(User user, String argument) {
		String userName = user.getFirstName() + " " + user.getLastName();

		StringBuilder messageTextBuilder = new StringBuilder("Hola ").append(userName);

		messageTextBuilder.append("\n");
		messageTextBuilder.append("No tengo claro que contestar a:\n");
		messageTextBuilder.append(argument);

		return messageTextBuilder.toString();
	}
}
