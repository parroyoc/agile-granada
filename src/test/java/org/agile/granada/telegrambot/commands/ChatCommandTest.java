package org.agile.granada.telegrambot.commands;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.agile.granada.telegrambot.commands.ChatCommand;
import org.agile.granada.telegrambot.services.IResponseGenerator;
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

public class ChatCommandTest {

	ChatCommand sut;

	@Mock
	AbsSender absSender;
	@Mock
	User user;
	@Mock
	Chat chat;
	@Mock
	IResponseGenerator responseGenerator;

	@Before
	public void before() {
		initMocks(this);
		sut = new ChatCommand(responseGenerator);
	}

	@Test
	public void testBasicConstruction_InitialisesIdentifierAndDescription() {
		// setup
		ChatCommand chatCommand; 

		// execute
		chatCommand = new ChatCommand(responseGenerator);

		// assert
		assertEquals(ChatCommand.CHAT_IDENTIFIER, chatCommand.getCommandIdentifier());
		assertEquals(ChatCommand.CHAT_DESCRIPTION, chatCommand.getDescription());
	}

	@Test
	public void testExecute_SendsACorrectMessageBasedOnTheResponseGenerator() throws TelegramApiException {
		// setup
		when(chat.getId()).thenReturn(12345L);
		when(((Message) absSender.sendMessage(anyObject()))).thenReturn(new Message());
		String response = "ejemplo de respuesta";
		String[] questions = { "entrada1", "entrada2" };
		when(responseGenerator.answer(user, questions)).thenReturn(response);

		// execute
		sut.execute(absSender, user, chat, questions);

		// assert
		ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
		verify(absSender, times(1)).sendMessage(captor.capture());
		SendMessage messageSent = captor.getValue();
		assertEquals("12345", messageSent.getChatId());
		assertTrue(messageSent.getText().contains(response));
	}

}
