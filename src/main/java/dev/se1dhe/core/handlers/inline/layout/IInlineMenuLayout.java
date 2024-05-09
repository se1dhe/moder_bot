package dev.se1dhe.core.handlers.inline.layout;

import java.util.List;

import dev.se1dhe.core.handlers.inline.InlineButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;


public interface IInlineMenuLayout {
	/**
	 * @param buttons the buttons to generate the menu with
	 * @return the generated markup
	 */
	InlineKeyboardMarkup generateLayout(List<InlineButton> buttons);
}