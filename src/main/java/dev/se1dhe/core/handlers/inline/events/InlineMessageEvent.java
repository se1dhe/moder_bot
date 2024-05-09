package dev.se1dhe.core.handlers.inline.events;


import dev.se1dhe.core.handlers.inline.InlineButton;
import dev.se1dhe.core.handlers.inline.InlineContext;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class InlineMessageEvent {
    private final InlineContext context;
    private final InlineButton button;
    private final TelegramClient telegramClient;
    private final Update update;
    private final Message message;

    /**
     * @param context        the context
     * @param button         the button
     * @param telegramClient the telegramClient
     * @param update         the update received
     * @param message        the message
     */
    public InlineMessageEvent(InlineContext context, InlineButton button, TelegramClient telegramClient, Update update, Message message) {
        this.context = context;
        this.button = button;
        this.telegramClient = telegramClient;
        this.update = update;
        this.message = message;
    }

    /**
     * @return the context
     */
    public InlineContext getContext() {
        return context;
    }

    /**
     * @return the button
     */
    public InlineButton getButton() {
        return button;
    }

    /**
     * @return the bot
     */
    public TelegramClient getTelegramClient() {
        return telegramClient;
    }

    /**
     * @return the update
     */
    public Update getUpdate() {
        return update;
    }

    /**
     * @return the query
     */
    public Message getMessage() {
        return message;
    }
}