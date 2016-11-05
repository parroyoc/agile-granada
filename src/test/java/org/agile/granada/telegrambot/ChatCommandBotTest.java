package org.agile.granada.telegrambot;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.agile.granada.telegrambot.services.IResponseGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.telegram.telegrambots.api.objects.Update;

public class ChatCommandBotTest {

	ChatCommandBot sut;

	@Mock
	Update update;
	@Mock
	IResponseGenerator responseGenerator;

	@Before
	public void before() {
		initMocks(this);
		sut = new ChatCommandBot(responseGenerator);
	}

	@Test
	public void testBasicConstruction_RegistersThe3Commands() {
		// setup
		ChatCommandBot chatCommandBot;

		// execute
		chatCommandBot = new ChatCommandBot(responseGenerator);

		// assert
		assertEquals(3, chatCommandBot.getRegisteredCommands().size());
	}

	@Test
	public void testGetsBotTokenFromConfig() {
		// assert
		assertEquals(Config.getBotToken(), sut.getBotToken());
	}

	@Test
	public void testGetsBotUsernameFromConfig() {
		// assert
		assertEquals(Config.getBotUsername(), sut.getBotUsername());
	}
}
