package com.github.bydanovm.matb.command;

import com.github.bydanovm.matb.bot.MATelegramBot;
import com.github.bydanovm.matb.service.SendBotMessageService;
import com.github.bydanovm.matb.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
* Abstract class for testing {@link Command}s.
*/
abstract class AbstractCommandTest {

   protected MATelegramBot MABot = Mockito.mock(MATelegramBot.class);
   protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(MABot);

   abstract String getCommandName();

   abstract String getCommandMessage();

   abstract Command getCommand();

   @Test
   public void shouldProperlyExecuteCommand() throws TelegramApiException {
       //given
       Long chatId = 1234567824356L;

       Update update = new Update();
       Message message = Mockito.mock(Message.class);
       Mockito.when(message.getChatId()).thenReturn(chatId);
       Mockito.when(message.getText()).thenReturn(getCommandName());
       update.setMessage(message);

       SendMessage sendMessage = new SendMessage();
       sendMessage.setChatId(chatId.toString());
       sendMessage.setText(getCommandMessage());
       sendMessage.enableHtml(true);

       //when
       getCommand().execute(update);

       //then
       Mockito.verify(MABot).execute(sendMessage);
   }
}