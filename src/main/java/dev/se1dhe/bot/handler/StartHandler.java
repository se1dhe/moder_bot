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
    private final StateMachine<States, Events> stateMachine;

    @Autowired
    public StartHandler(DBUserService dbUserService, StateMachine<States, Events> stateMachine) {
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
        return "Start interaction with the bot";
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, List<String> args) throws TelegramApiException {
        stateMachine.sendEvent(MessageBuilder.withPayload(Events.START_EVENT).build());
        BotUtil.sendMessage(bot, message, "Привет! Как вас зовут?", true, false, null);
    }

    @Override
    public boolean onMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {
        String text = message.getText();
        switch (stateMachine.getState().getId()) {
            case ASK_NAME:
                onNameReceived(bot, update, message, List.of(text));
                return true;
            case ASK_AGE:
                onAgeReceived(bot, update, message, List.of(text));
                return true;
            default:
                return false;
        }
    }

    private void onNameReceived(AbstractTelegramBot bot, Update update, Message message, List<String> args) throws TelegramApiException {
        String name = args.get(0);
        stateMachine.getExtendedState().getVariables().put("name", name);
        stateMachine.sendEvent(MessageBuilder.withPayload(Events.NAME_RECEIVED).build());
        BotUtil.sendMessage(bot, message, "Сколько вам лет?", true, false, null);
    }

    private void onAgeReceived(AbstractTelegramBot bot, Update update, Message message, List<String> args) throws TelegramApiException {
        String age = args.get(0);
        String name = (String) stateMachine.getExtendedState().getVariables().get("name");
        stateMachine.sendEvent(MessageBuilder.withPayload(Events.AGE_RECEIVED).build());
        String greeting = "Привет, " + name + "! Вам " + age + " лет.";
        BotUtil.sendMessage(bot, message, greeting, true, false, null);
        stateMachine.sendEvent(MessageBuilder.withPayload(Events.GREET_USER).build());
    }
}
