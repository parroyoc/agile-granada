package org.agile.granada.telegrambot;

import org.agile.granada.telegrambot.services.ResponseGeneratorService;
import org.agile.granada.telegrambot.services.TemperatureDelegateService;
import org.agile.granada.telegrambot.services.WeatherClient;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.logging.BotsFileHandler;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Main {
	private static final String LOGTAG = "MAIN";

	public static void main(String[] args) {
		BotLogger.setLevel(Level.ALL);
		BotLogger.registerLogger(new ConsoleHandler());
		try {
			BotLogger.registerLogger(new BotsFileHandler());
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

			ResponseGeneratorService responseGenerator = new ResponseGeneratorService();
			TemperatureDelegateService temperatureDelegateService = new TemperatureDelegateService();
			temperatureDelegateService.setWeatherClient(new WeatherClient());
			responseGenerator.addDelegate("temperatura", temperatureDelegateService);
			telegramBotsApi.registerBot(new ChatCommandBot(responseGenerator));
		} catch (Exception e) {
			BotLogger.error(LOGTAG, e);
		}
	}

}
