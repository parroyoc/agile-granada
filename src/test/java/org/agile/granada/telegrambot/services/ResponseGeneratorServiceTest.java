package org.agile.granada.telegrambot.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;

public class ResponseGeneratorServiceTest {

	ResponseGeneratorService sut;

	@Mock
	User user;
	@Mock
	Chat chat;

	@Before
	public void before() {
		initMocks(this);
		when(user.getFirstName()).thenReturn("Pablo");
		when(user.getLastName()).thenReturn("Arroyo");
		sut = new ResponseGeneratorService();
	}

	@Test
	public void testAnswerJustTheName_WhenThereIsNoInputMessage() throws TelegramApiException {
		// setup
		String[] arguments = null;

		// execute
		String answer = sut.answer(user, arguments);

		// assert
		assertEquals("Hola Pablo Arroyo", answer);
	}

	@Test
	public void testAnswerNoSeQueContestar_WhenThereIsInputMessage() throws TelegramApiException {
		// setup
		String[] arguments = { "algo", "de", "entrada" };

		// execute
		String answer = sut.answer(user, arguments);

		// assert
		assertTrue(answer.contains("Hola Pablo Arroyo"));
		assertTrue(answer.contains("No tengo claro que contestar a:"));
		assertTrue(answer.contains("algo de entrada"));
	}

}
