package org.agile.granada.telegrambot.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TemperatureDelegateServiceTest {

	TemperatureDelegateService sut;

	@Mock
	WeatherClient weatherClient;

	@Before
	public void before() {
		initMocks(this);
		sut = new TemperatureDelegateService();
	}

	@Test
	public void testAnswerUnknown_WhenTheCityIsNotSupported() throws TelegramApiException {
		// setup
		String message = "Priego";
		sut.setWeatherClient(weatherClient);
		when((weatherClient.celsiusFor(anyString()))).thenReturn("Unknown");

		// execute
		String answer = sut.answer(message);

		// assert
		assertEquals("No puedo contestar la temperatura en Priego", answer);
	}

	@Test
	public void testAnswerATemperatureBasedOnARealService_WhenTheCityIsSupported() throws TelegramApiException {
		// setup
		String city = "New York";
		String response = "La temperatura en New York es de 3 grados";
		sut.setWeatherClient(weatherClient);
		when((weatherClient.celsiusFor(city))).thenReturn("3");

		// execute
		String answer = sut.answer(city);

		// assert
		assertTrue(answer.contains(response));
	}
}
