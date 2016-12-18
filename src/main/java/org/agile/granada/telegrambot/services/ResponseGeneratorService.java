package org.agile.granada.telegrambot.services;

import org.telegram.telegrambots.api.objects.User;

import java.util.HashMap;

public class ResponseGeneratorService implements IResponseGenerator {

	private HashMap<String, IDelegateService> delegates = new HashMap<>();

	public String answer(User user, String[] arguments) {
		if (arguments != null && arguments.length > 0) {
			return answer(user, String.join(" ", arguments));
		}

		return "Hola " + user.getFirstName() + " " + user.getLastName();
	}

	public String answer(User user, String argument) {
		String userName = user.getFirstName() + " " + user.getLastName();

		StringBuilder messageTextBuilder = new StringBuilder("Hola ").append(userName);

		String command = argument.substring(0, argument.indexOf(' '));
		String rest = argument.substring(argument.indexOf(' ') + 1);
		IDelegateService delegateService = delegates.get(command);

		if (delegateService != null) messageTextBuilder.append(delegateService.answer(rest));
		else messageTextBuilder.append("\nNo tengo claro que contestar a:\n").append(argument);

		return messageTextBuilder.toString();
	}

	public void addDelegate(String command, IDelegateService delegateService) {
		delegates.put(command, delegateService);
	}
}
