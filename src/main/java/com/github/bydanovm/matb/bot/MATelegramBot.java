package com.github.bydanovm.matb.bot;

import com.github.bydanovm.matb.command.CommandContainer;
import com.github.bydanovm.matb.service.SendBotMessageServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
// import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
// import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.github.bydanovm.matb.command.CommandName.NO;
@Component
public class MATelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    public MATelegramBot() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
 
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }    

    // @Override
    // public void onUpdateReceived(Update update) {
    //     if(update.hasMessage() && update.getMessage().hasText()) {
    //         String message = update.getMessage().getText().trim();
    //         String chatId = update.getMessage().getChatId().toString();

    //         SendMessage sm = new SendMessage();
    //         sm.setChatId(chatId);
    //         sm.setText(message);

    //         try {
    //             execute(sm);
    //         } catch (TelegramApiException e){
    //             e.printStackTrace();
    //         }
    //     }
    // }
 
    @Override
    public String getBotUsername() {
        return username;
    }
 
    @Override
    public String getBotToken() {
        return token;
    }
}
