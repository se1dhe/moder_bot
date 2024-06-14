package dev.se1dhe.bot.handler;


import dev.se1dhe.bot.statemachine.enums.Event;
import dev.se1dhe.bot.statemachine.enums.State;
import dev.se1dhe.core.bots.AbstractTelegramBot;
import dev.se1dhe.core.handlers.IMessageHandler;
import dev.se1dhe.core.util.BotUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Log4j2
public class UserAnswerHandler implements IMessageHandler {

    private final StateMachine<State, Event> stateMachine;

    public UserAnswerHandler(StateMachine<State, Event> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public boolean onMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {
        String userResponse = message.getText();
        State currentState = stateMachine.getState().getId();

        switch (currentState) {
            case STATE1:
                stateMachine.getExtendedState().getVariables().put("name", userResponse);
                stateMachine.sendEvent(Event.USER_RESPONSE);
                BotUtil.sendMessage(bot, message, "Спасибо! Сейчас укажите свой возраст.", true, false, null);
                break;
            case STATE2:
                stateMachine.getExtendedState().getVariables().put("age", userResponse);
                stateMachine.sendEvent(Event.USER_RESPONSE);
                break;
            default:
                log.warn("Unexpected state {}", currentState);
                break;
        }

        return true;
    }
}