package dev.se1dhe.core.handlers;

import dev.se1dhe.core.bots.AbstractTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public interface IInlineQueryHandler extends ITelegramHandler {
	/**
	 * Fired whenever bot receives a callback query
	 *
	 * @param bot    the bot
	 * @param update the update
	 * @param query  the query
	 * @return {@code true} whenever this even has to be consumed, {@code false} to continue notified other handlers
	 * @throws TelegramApiException the exception
	 */
	boolean onInlineQuery(AbstractTelegramBot bot, Update update, InlineQuery query) throws TelegramApiException;
}
