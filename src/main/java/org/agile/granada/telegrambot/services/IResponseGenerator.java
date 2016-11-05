package org.agile.granada.telegrambot.services;

import org.telegram.telegrambots.api.objects.User;

public interface IResponseGenerator {

	String answer(User user, String[] arguments);

}
