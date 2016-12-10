package org.agile.granada.telegrambot;

public class Config {
	public static String getBotUsername() {
		return System.getProperty("BOT_USERNAME");
	}

	public static String getBotToken() {
		return System.getProperty("BOT_TOKEN");
	}
}
