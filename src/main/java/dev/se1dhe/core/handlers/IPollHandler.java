package dev.se1dhe.core.handlers;

import dev.se1dhe.core.bots.AbstractTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public interface IPollHandler extends ITelegramHandler {
	/**
	 * Fired whenever poll is received
	 *
	 * @param bot    the bot
	 * @param update the update
	 * @param poll   the poll
	 * @return {@code true} if handler 'consumed' that event, aborting notification to other handlers, {@code false} otherwise, continuing to look for handler that would return {@code true}
	 * @throws TelegramApiException the exception
	 */
	boolean onPoll(AbstractTelegramBot bot, Update update, Poll poll) throws TelegramApiException;
}
