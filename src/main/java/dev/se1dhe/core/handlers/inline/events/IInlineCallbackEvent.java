package dev.se1dhe.core.handlers.inline.events;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@FunctionalInterface
public interface IInlineCallbackEvent {
	/**
	 * @param event the callback event
	 * @return {@code true} on success, {@code false} otherwise
	 * @throws TelegramApiException in case of an erro
	 */
	boolean onCallbackEvent(InlineCallbackEvent event) throws TelegramApiException;
}