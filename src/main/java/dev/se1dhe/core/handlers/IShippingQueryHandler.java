package dev.se1dhe.core.handlers;

import dev.se1dhe.core.bots.AbstractTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public interface IShippingQueryHandler extends ITelegramHandler {
	/**
	 * Fired whenever shipping query is received
	 *
	 * @param bot           the bot
	 * @param update        the update
	 * @param shippingQuery the shipping query
	 * @return {@code true} if handler 'consumed' that event, aborting notification to other handlers, {@code false} otherwise, continuing to look for handler that would return {@code true}
	 * @throws TelegramApiException the exception
	 */
	boolean onShippingQuery(AbstractTelegramBot bot, Update update, ShippingQuery shippingQuery) throws TelegramApiException;
}
