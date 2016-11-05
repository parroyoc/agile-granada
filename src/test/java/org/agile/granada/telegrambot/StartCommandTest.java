package org.agile.granada.telegrambot;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.agile.granada.telegrambot.commands.StartCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

public class StartCommandTest {
	
	StartCommand sut;
	
	@Mock AbsSender absSender;
	@Mock User user;
	@Mock Chat chat;
	
	@Before
	public void before() {
		initMocks(this);
		sut = new StartCommand();
	}

	@Test
	public void testBasicConstruction_InitialisesIdentifierAndDescription() {
		// setup
		StartCommand startCommand; // mejor ser explicito por claridad, an lugar de utilizar sut en este caso
		
		// execute
		startCommand = new StartCommand();
		
		// assert
		assertEquals(StartCommand.HELLO_IDENTIFIER, startCommand.getCommandIdentifier());
		assertEquals(StartCommand.HELLO_DESCRIPTION, startCommand.getDescription());
	}
	
	@Test
	public void testExecute_SendsACorrectMessageToTheAppropiateChatAndUser() throws TelegramApiException {
		// setup
		when(chat.getId()).thenReturn(12345L);
		when(user.getFirstName()).thenReturn("Pablo");
		when(user.getLastName()).thenReturn("Arroyo");
		when(((Message) absSender.sendMessage(anyObject()))).thenReturn(new Message());
		
		// execute
		sut.execute(absSender, user, chat, null);
		
		// assert
		ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
		verify(absSender, times(1)).sendMessage(captor.capture());
		SendMessage messageSent = captor.getValue();
		assertEquals("12345", messageSent.getChatId());
		assertTrue(messageSent.getText().contains("Hola Pablo Arroyo"));
		assertTrue(messageSent.getText().contains(StartCommand.HELLO_MESSAGE));
	}

}
