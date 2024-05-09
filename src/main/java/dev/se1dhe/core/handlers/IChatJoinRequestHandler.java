package dev.se1dhe.core.handlers;

import dev.se1dhe.core.bots.AbstractTelegramBot;
import org.telegram.telegrambots.meta.api.objects.ChatJoinRequest;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public interface IChatJoinRequestHandler extends ITelegramHandler {
	/**
	 * Fired whenever chat join request is received
	 *
	 * @param bot             the bot
	 * @param update          the update
	 * @param chatJoinRequest the chat join request
	 * @return {@code true} if handler 'consumed' that event, aborting notification to other handlers, {@code false} otherwise, continuing to look for handler that would return {@code true}
	 * @throws TelegramApiException the exception
	 */
	boolean onChatJoinRequest(AbstractTelegramBot bot, Update update, ChatJoinRequest chatJoinRequest) throws TelegramApiException;
}
