package dev.se1dhe.bot.handler;

import dev.se1dhe.bot.model.DbUser;
import dev.se1dhe.bot.service.DBUserService;
import dev.se1dhe.bot.service.LocalizationService;
import dev.se1dhe.bot.statemachine.enums.Event;
import dev.se1dhe.bot.statemachine.enums.State;
import dev.se1dhe.core.bots.AbstractTelegramBot;
import dev.se1dhe.core.handlers.ICommandHandler;
import dev.se1dhe.core.util.BotUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Log4j2
public class StartHandler implements ICommandHandler {

    private final DBUserService dbUserService;
    private final StateMachine<State, Event> stateMachine;

    public StartHandler(DBUserService dbUserService, StateMachine<State, Event> stateMachine) {
        this.dbUserService = dbUserService;
        this.stateMachine = stateMachine;
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
        return "/start";
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, List<String> args) throws TelegramApiException {
        DbUser dbUser = dbUserService.registerUser(message.getFrom());
        stateMachine.startReactively().block();
        System.out.println("Sending event EVENT1...");
        stateMachine.sendEvent(MessageBuilder.withPayload(Event.EVENT1).build());
        BotUtil.sendMessage(bot, message, "Привет! Как вас зовут?", true, false, null);
    }
}