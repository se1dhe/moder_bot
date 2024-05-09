package dev.se1dhe.core.util;

import dev.se1dhe.core.handlers.ICommandHandler;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class BotUtil {
    public static <T extends TelegramClient> void sendAction(T bot, Message message, ActionType actionType) throws TelegramApiException {
        final SendChatAction sendAction = SendChatAction.builder().
                chatId(message.getChat().getId()).
                action(actionType.toString())
                .build();
        bot.execute(sendAction);
    }

    public static <T extends TelegramClient> void sendUsage(T bot, Message message, ICommandHandler handler) throws TelegramApiException {
        final SendMessage msg = SendMessage.builder().
                chatId(message.getChat().getId()).
                text(handler.getUsage()).
                build();
        bot.execute(msg);
    }

    public static <T extends TelegramClient> Message sendMessage(T bot, Message message, String text, boolean replyToMessage, boolean useMarkDown, ReplyKeyboard replayMarkup) throws TelegramApiException {
        final SendMessage msg = SendMessage.builder().
                chatId(message.getChat().getId()).
                text(text).
                parseMode(useMarkDown ? ParseMode.MARKDOWNV2 : null).
                replyToMessageId(replyToMessage ? message.getMessageId() : null).
                replyMarkup(replayMarkup).
                build();
        return bot.execute(msg);
    }

    public static <T extends TelegramClient> void sendHtmlMessage(T bot, Message message, String text, boolean replyToMessage, ReplyKeyboard replayMarkup) throws TelegramApiException {
        final SendMessage msg = SendMessage.builder().
                chatId(message.getChat().getId()).
                text(text).
                parseMode(ParseMode.HTML).
                replyToMessageId(replyToMessage ? message.getMessageId() : null).
                replyMarkup(replayMarkup).
                build();
        bot.execute(msg);
    }

    public static <T extends TelegramClient> void editMessage(T bot, Message message, String text, boolean useMarkDown, InlineKeyboardMarkup inlineMarkup) throws TelegramApiException {
        final EditMessageText msg = EditMessageText.builder().
                chatId(message.getChat().getId()).
                messageId(message.getMessageId()).
                text(text).
                parseMode(useMarkDown ? ParseMode.MARKDOWNV2 : null).
                replyMarkup(inlineMarkup).
                build();
        ;bot.execute(msg);
    }

    public static <T extends TelegramClient> void editMessage(T bot, CallbackQuery query, String text, boolean useMarkDown, InlineKeyboardMarkup inlineMarkup) throws TelegramApiException {
        final EditMessageText msg = EditMessageText.builder().
                chatId(query.getMessage().getChat().getId()).
                messageId(query.getMessage().getMessageId()).
                inlineMessageId(query.getInlineMessageId()).
                text(text).
                parseMode(useMarkDown ? ParseMode.MARKDOWNV2 : null).
                replyMarkup(inlineMarkup).
                build();
        bot.execute(msg);
    }
}