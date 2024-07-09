package dev.se1dhe.bot;

import dev.se1dhe.bot.config.Config;
import dev.se1dhe.bot.handler.StartHandler;
import dev.se1dhe.bot.service.AccessLevelValidator;
import dev.se1dhe.bot.service.DBUserService;
import dev.se1dhe.bot.statemachine.enums.Event;
import dev.se1dhe.bot.statemachine.enums.State;
import dev.se1dhe.core.bots.DefaultTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.net.InetAddress;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class BotApplication {
    public static DefaultTelegramBot telegramBot;

    private static DBUserService dbUserService;

    private static StateMachine<State, Event> stateMachine;
    @Autowired
    public BotApplication(DBUserService dbUserService, StateMachine<State, Event> stateMachine) {
        BotApplication.dbUserService = dbUserService;
        BotApplication.stateMachine = stateMachine;
    }

    public static void main(String[] args) throws TelegramApiException, IOException {
        Config.load();
        SpringApplication.run(BotApplication.class);

        runBot();
    }

    private static void runBot() throws TelegramApiException {
        final TelegramClient telegramClient = new OkHttpTelegramClient(Config.BOT_TOKEN);

        telegramBot = new DefaultTelegramBot(telegramClient);

        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(Config.BOT_TOKEN, telegramBot);

        telegramBot.setAccessLevelValidator(new AccessLevelValidator(dbUserService));
        telegramBot.addHandler(new StartHandler(dbUserService, stateMachine));
        printSystemInfo();
    }

    private static void printSystemInfo() {
        log.info("=== Информация о проекте ===");
        log.info("Автор проекта: {} {} {}", "se1dhe", "[t.me/se1dhe]" , "[se1dhe.dev]");
        log.info("Название проекта: {}", Config.BOT_NAME);
        log.info("Версия проекта: {}", System.getProperty("java.version"));
        log.info("Используемая JVM: {}", System.getProperty("java.vm.name"));
        log.info("Операционная система: {}", System.getProperty("os.name"));
        log.info("=== ====");

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            log.info("IP-адрес сервера: {}", ipAddress);
        } catch (Exception e) {
            log.error("Не удалось получить IP-адрес сервера", e);
        }

        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        log.info("Использовано ОЗУ: {} MB", (usedMemory / (1024 * 1024)));
        log.info("=== ====");

    }
}
