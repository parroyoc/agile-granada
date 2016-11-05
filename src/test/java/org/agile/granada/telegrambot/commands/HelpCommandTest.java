package org.agile.granada.telegrambot.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;

import org.agile.granada.telegrambot.commands.HelpCommand;
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
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;

public class HelpCommandTest {
	
	HelpCommand sut;
	
	@Mock ICommandRegistry commandRegistry;
	@Mock AbsSender absSender;
	@Mock User user;
	@Mock Chat chat;
	
	@Before
	public void before() {
		initMocks(this);
		sut = new HelpCommand(commandRegistry);
	}

	@Test
	public void testBasicConstruction_InitialisesIdentifierAndDescription() {
		// setup
		HelpCommand helpCommand; // mejor ser explicito por claridad, an lugar de utilizar sut en este caso
		
		// execute
		helpCommand = new HelpCommand(commandRegistry);
		
		// assert
		assertEquals(HelpCommand.HELP_IDENTIFIER, helpCommand.getCommandIdentifier());
		assertEquals(HelpCommand.HELP_DESCRIPTION, helpCommand.getDescription());
	}
	
	@Test
	public void testExecute_SendsAMessageToTheAppropiateChatAndUser() throws TelegramApiException {
		// setup
		setUpUserAndChat("Pablo", "Arroyo", 12345L);
		when(((Message) absSender.sendMessage(anyObject()))).thenReturn(new Message());
		
		// execute
		sut.execute(absSender, user, chat, null);
		
		// assert
		ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
		verify(absSender, times(1)).sendMessage(captor.capture());
		SendMessage messageSent = captor.getValue();
		assertEquals("12345", messageSent.getChatId());
		assertTrue(messageSent.getText().contains(HelpCommand.HELP_LIST_HEADER));
	}

	@Test
	public void testExecute_SendsACorrectMessageIncludingAllCommands() throws TelegramApiException {
		// setup
		setUpUserAndChat("Pablo", "Arroyo", 12345L);
		when(((Message) absSender.sendMessage(anyObject()))).thenReturn(new Message());
		ArrayList<BotCommand> commands = new ArrayList<>();
		commands.add(createBotCommand("id1", "description1"));
		commands.add(createBotCommand("id2", "description2"));
		when(commandRegistry.getRegisteredCommands()).thenReturn(commands);
		
		// execute
		sut.execute(absSender, user, chat, null);
		
		// assert
		ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
		verify(absSender, times(1)).sendMessage(captor.capture());
		SendMessage messageSent = captor.getValue();
		assertEquals("12345", messageSent.getChatId());
		assertTrue(messageSent.getText().contains(HelpCommand.HELP_LIST_HEADER));
		assertTrue(messageSent.getText().contains("id1"));
		assertTrue(messageSent.getText().contains("description1"));
		assertTrue(messageSent.getText().contains("id2"));
		assertTrue(messageSent.getText().contains("description2"));
	}

	private void setUpUserAndChat(String firstName, String lastName, long chatId) {
		when(chat.getId()).thenReturn(chatId);
		when(user.getFirstName()).thenReturn(firstName);
		when(user.getLastName()).thenReturn(lastName);
	}

	private BotCommand createBotCommand(String id, String desc) {
		return new BotCommand(id, desc) {
			@Override
			public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {}
		};
	}
}
