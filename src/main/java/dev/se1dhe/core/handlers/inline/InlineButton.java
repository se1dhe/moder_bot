package dev.se1dhe.core.handlers.inline;

import dev.se1dhe.core.handlers.inline.events.IInlineCallbackEvent;
import dev.se1dhe.core.handlers.inline.events.IInlineMessageEvent;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.UUID;


public class InlineButton {
    /**
     * -- GETTER --
     *
     * @return the context
     */
    @Getter
    private final InlineContext context;
    /**
     * -- GETTER --
     *
     * @return the name
     */
    @Getter
    private final String name;
    /**
     * -- GETTER --
     *
     * @return the row
     */
    @Getter
    private final int row;
    /**
     * -- GETTER --
     *
     * @return the forceNewRow
     */
    @Getter
    private final boolean forceNewRow;
    /**
     * -- GETTER --
     *
     * @return the onQueryCallback
     */
    @Getter
    private final IInlineCallbackEvent onQueryCallback;
    private final IInlineMessageEvent onInputMessage;
    /**
     * -- GETTER --
     *
     * @return the subMenu
     */
    @Getter
    private final InlineMenu subMenu;
    private final String uuid = UUID.randomUUID().toString();

    /**
     * Creates new Inline button from builder
     *
     * @param builder the builder
     */
    public InlineButton(InlineButtonBuilder builder) {
        this.context = builder.context;
        this.name = builder.name;
        this.row = builder.row;
        this.forceNewRow = builder.forceNewRow;
        this.onQueryCallback = builder.onQueryCallback;
        this.onInputMessage = builder.onInputMessage;
        this.subMenu = builder.subMenu;
    }

    /**
     * @return the onInputMessage
     */
    public IInlineMessageEvent getInputMessage() {
        return onInputMessage;
    }

    /**
     * @return the uuid
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * @return the {@link InlineKeyboardButton}
     */
    public InlineKeyboardButton createInlineKeyboardButton() {
        return InlineKeyboardButton.builder().
                text(name).
                callbackData(uuid).
                build();
    }
}
