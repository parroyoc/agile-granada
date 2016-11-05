package org.agile.granada.telegrambot;

public class Config {
    public static String getBotUsername() {
    	return System.getenv("BOT_USERNAME");
    }

    public static String getBotToken() {
    	return System.getenv("BOT_TOKEN");
    }
}
