package dev.se1dhe.core.handlers;

import org.telegram.telegrambots.meta.api.objects.User;


public interface IAccessLevelValidator {
	/**
	 * @param handler the handler
	 * @param user    the user to validate
	 * @return {@code true} if {@link User} has sufficient access level, {@code false} otherwise
	 */
	boolean validate(ITelegramHandler handler, User user);
}
