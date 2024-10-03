package dev.se1dhe.bot.handler;

import dev.se1dhe.bot.service.DBUserService;
import dev.se1dhe.bot.statemachine.enums.Events;
import dev.se1dhe.bot.statemachine.enums.States;
import dev.se1dhe.core.bots.AbstractTelegramBot;
import dev.se1dhe.core.handlers.ICommandHandler;
import dev.se1dhe.core.handlers.IMessageHandler;
import dev.se1dhe.core.util.BotUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Log4j2
public class StartHandler implements ICommandHandler, IMessageHandler {

    private final DBUserService dbUserService;

    @Autowired
    public StartHandler(DBUserService dbUserService) {
        this.dbUserService = dbUserService;

    }

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String getUsage() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "Start interaction with the bot";
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, List<String> args) throws TelegramApiException {
        BotUtil.sendMessage(bot, message, "Привет! Как вас зовут?", true, false, null);
    }

    @Override
    public boolean onMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {
        return false;
    }

}
