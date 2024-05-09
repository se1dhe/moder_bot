package dev.se1dhe.bot.service;



import dev.se1dhe.core.handlers.IAccessLevelValidator;
import dev.se1dhe.core.handlers.ITelegramHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class AccessLevelValidator implements IAccessLevelValidator {
    private final DBUserService dbUserService;

    public AccessLevelValidator(DBUserService dbUserService) {
        this.dbUserService = dbUserService;
    }

    @Override
    public boolean validate(ITelegramHandler handler, User user) {
        return dbUserService.validate(user.getId(), handler.getRequiredAccessLevel());
    }
}