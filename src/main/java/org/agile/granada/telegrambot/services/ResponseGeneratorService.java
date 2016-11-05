package org.agile.granada.telegrambot.services;

import org.telegram.telegrambots.api.objects.User;

public class ResponseGeneratorService implements IResponseGenerator {

	public String answer(User user, String[] arguments) {
		String userName = user.getFirstName() + " " + user.getLastName();

		StringBuilder messageTextBuilder = new StringBuilder("Hola ").append(userName);

		if (arguments != null && arguments.length > 0) {
			messageTextBuilder.append("\n");
			messageTextBuilder.append("No tengo claro que contestar a:\n");
			messageTextBuilder.append(String.join(" ", arguments));
		}

		return messageTextBuilder.toString();
	}
}
