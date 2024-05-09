package dev.se1dhe.core.handlers.inline.layout;

import java.util.ArrayList;
import java.util.List;

import dev.se1dhe.core.handlers.inline.InlineButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;


public class InlineRowDefinedLayout implements IInlineMenuLayout {
	/**
	 * The default Inline Row Defined layout
	 */
	public static final InlineRowDefinedLayout DEFAULT = new InlineRowDefinedLayout();
	
	private InlineRowDefinedLayout() {
	}
	
	@Override
	public InlineKeyboardMarkup generateLayout(List<InlineButton> buttons) {
		final List<InlineKeyboardRow> keyboard = new ArrayList<>();
		buttons.forEach(uiButton ->
		{
			final InlineKeyboardButton button = uiButton.createInlineKeyboardButton();
			if ((keyboard.size() <= uiButton.getRow()) || uiButton.isForceNewRow()) {
				keyboard.add(new InlineKeyboardRow());
			}
			keyboard.get(uiButton.isForceNewRow() ? (keyboard.size() - 1) : uiButton.getRow()).add(button);
		});
		return InlineKeyboardMarkup.builder().keyboard(keyboard).build();
	}
}
