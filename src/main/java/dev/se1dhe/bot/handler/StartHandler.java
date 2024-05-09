package dev.se1dhe.bot.handler;

import dev.se1dhe.bot.service.DBUserService;
import dev.se1dhe.bot.service.LocalizationService;
import dev.se1dhe.core.bots.AbstractTelegramBot;
import dev.se1dhe.core.handlers.ICommandHandler;
import dev.se1dhe.core.util.BotUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Log4j2
public class StartHandler implements ICommandHandler {

    private final DBUserService dbUserService;

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
        return "/start";
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, List<String> args) throws TelegramApiException {
        dbUserService.registerUser(message.getFrom());
        BotUtil.sendMessage(bot, message, LocalizationService.getString("start.welcomeMessage"), false, false, null);
    }
}
