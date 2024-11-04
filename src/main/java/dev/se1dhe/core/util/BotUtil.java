package dev.se1dhe.core.util;

import dev.se1dhe.bot.config.Config;
import dev.se1dhe.core.handlers.ICommandHandler;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;


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

    public static <T extends TelegramClient> Message sendMessage(T bot, Message message, String text, boolean replyToMessage, boolean useHtml, ReplyKeyboard replayMarkup) throws TelegramApiException {
        final SendMessage msg = SendMessage.builder().
                chatId(message.getChat().getId()).
                text(text).
                parseMode(useHtml ? ParseMode.HTML : null).
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

    public static String uploadPhoto(TelegramClient bot, String fileId) {
        try {
            GetFile getFileMethod = new GetFile(fileId);
            org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFileMethod);
            String fileUrl = "https://api.telegram.org/file/bot" + Config.BOT_TOKEN + "/" + file.getFilePath();

            java.io.File downloadedFile = downloadFile(fileUrl);

            String imgDirectory = "img";
            String localPath = imgDirectory + "/" + downloadedFile.getName();

            java.nio.file.Path imgPath = java.nio.file.Paths.get(imgDirectory);
            if (!java.nio.file.Files.exists(imgPath)) {
                java.nio.file.Files.createDirectories(imgPath);
            }

            java.nio.file.Files.copy(downloadedFile.toPath(), new java.io.File(localPath).toPath(), StandardCopyOption.REPLACE_EXISTING);

            return localPath;

        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    private static java.io.File downloadFile(String fileUrl) throws IOException {
        java.io.File tempFile = java.io.File.createTempFile("telegram_photo", ".jpg");
        java.nio.file.Path path = tempFile.toPath();

        try (java.io.InputStream in = new java.net.URL(fileUrl).openStream()) {
            java.nio.file.Files.copy(in, path, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        return tempFile;
    }

    public static String escapeMarkdownV2(String text) {
        return text.replaceAll("([_\\*\\[\\]\\(\\)~`>#+\\-=|{}.!])", "\\\\$1")
                .replaceAll("(\\!)", "\\\\!");
    }

    public static String escapeHTML(String text) {
        return text.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

    public static String formatText(Message message, String parseMode) {
        String text = message.getText() != null ? message.getText() : message.getCaption();
        List<MessageEntity> entities = message.getEntities() != null ? message.getEntities() : message.getCaptionEntities();

        if (entities == null || entities.isEmpty()) {
            return parseMode.equalsIgnoreCase("MarkdownV2") ? escapeMarkdownV2(text) : escapeHTML(text);
        }

        StringBuilder formattedText = new StringBuilder(text);

        for (int i = entities.size() - 1; i >= 0; i--) {
            MessageEntity entity = entities.get(i);
            int offset = entity.getOffset();
            int length = entity.getLength();
            String entityText = formattedText.substring(offset, offset + length);

            if (parseMode.equalsIgnoreCase("MarkdownV2")) {
                entityText = escapeMarkdownV2(entityText);
                formattedText = applyMarkdownV2Formatting(entity, formattedText, entityText, offset, length);
            } else if (parseMode.equalsIgnoreCase("HTML")) {
                entityText = escapeHTML(entityText);
                formattedText = applyHTMLFormatting(entity, formattedText, entityText, offset, length);
            }
        }

        return formattedText.toString();
    }

    private static StringBuilder applyMarkdownV2Formatting(MessageEntity entity, StringBuilder formattedText, String entityText, int offset, int length) {
        switch (entity.getType()) {
            case "bold":
                formattedText.replace(offset, offset + length, "*" + entityText + "*");
                break;
            case "italic":
                formattedText.replace(offset, offset + length, "_" + entityText + "_");
                break;
            case "underline":
                formattedText.replace(offset, offset + length, "__" + entityText + "__");
                break;
            case "strikethrough":
                formattedText.replace(offset, offset + length, "~" + entityText + "~");
                break;
            case "spoiler":
                formattedText.replace(offset, offset + length, "||" + entityText + "||");
                break;
            case "code":
                formattedText.replace(offset, offset + length, "`" + entityText + "`");
                break;
            case "pre":
                formattedText.replace(offset, offset + length, "```\n" + entityText + "\n```");
                break;
            case "url":
            case "text_link":
                formattedText.replace(offset, offset + length, "[" + entityText + "](" + escapeMarkdownV2(entity.getUrl()) + ")");
                break;
            case "blockquote":
                String[] lines = entityText.split("\n");
                StringBuilder quotedText = new StringBuilder();
                for (String line : lines) {
                    quotedText.append("> ").append(line).append("\n");
                }
                formattedText.replace(offset, offset + length, quotedText.toString().trim());
                break;
        }
        return formattedText;
    }

    private static StringBuilder applyHTMLFormatting(MessageEntity entity, StringBuilder formattedText, String entityText, int offset, int length) {
        switch (entity.getType()) {
            case "bold":
                formattedText.replace(offset, offset + length, "<b>" + entityText + "</b>");
                break;
            case "italic":
                formattedText.replace(offset, offset + length, "<i>" + entityText + "</i>");
                break;
            case "underline":
                formattedText.replace(offset, offset + length, "<u>" + entityText + "</u>");
                break;
            case "strikethrough":
                formattedText.replace(offset, offset + length, "<s>" + entityText + "</s>");
                break;
            case "spoiler":
                formattedText.replace(offset, offset + length, "<tg-spoiler>" + entityText + "</tg-spoiler>");
                break;
            case "code":
                formattedText.replace(offset, offset + length, "<code>" + entityText + "</code>");
                break;
            case "pre":
                formattedText.replace(offset, offset + length, "<pre>" + entityText + "</pre>");
                break;
            case "url":
            case "text_link":
                formattedText.replace(offset, offset + length, "<a href=\"" + escapeHTML(entity.getUrl()) + "\">" + entityText + "</a>");
                break;
            case "blockquote":
                String[] lines = entityText.split("\n");
                StringBuilder quotedText = new StringBuilder();
                for (String line : lines) {
                    quotedText.append("<blockquote>").append(line).append("</blockquote>\n");
                }
                formattedText.replace(offset, offset + length, quotedText.toString().trim());
                break;
        }
        return formattedText;
    }
}
