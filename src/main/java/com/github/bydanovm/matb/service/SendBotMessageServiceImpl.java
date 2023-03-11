package com.github.bydanovm.matb.service;

import com.github.bydanovm.matb.bot.MATelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
* Implementation of {@link SendBotMessageService} interface.
*/
@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

   private final MATelegramBot javarushBot;

   @Autowired
   public SendBotMessageServiceImpl(MATelegramBot javarushBot) {
       this.javarushBot = javarushBot;
   }

   @Override
   public void sendMessage(String chatId, String message) {
       SendMessage sendMessage = new SendMessage();
       sendMessage.setChatId(chatId);
       sendMessage.enableHtml(true);
       sendMessage.setText(message);

       try {
           javarushBot.execute(sendMessage);
       } catch (TelegramApiException e) {
           //todo add logging to the project.
           e.printStackTrace();
       }
   }
}
