package dev.se1dhe.bot;

import dev.se1dhe.bot.config.Config;
import dev.se1dhe.bot.handler.StartHandler;
import dev.se1dhe.bot.service.AccessLevelValidator;
import dev.se1dhe.bot.service.DBUserService;
import dev.se1dhe.core.bots.DefaultTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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

    private static DBUserService dbUserService;

    @Autowired
    public BotApplication(DBUserService dbUserService) {
        BotApplication.dbUserService = dbUserService;
    }

    public static void main(String[] args) throws TelegramApiException, IOException, ClassNotFoundException {
        // Загрузка конфигурации
        Config.load();

        // Создание контекста Spring
        final ConfigurableApplicationContext context = SpringApplication.run(BotApplication.class);

        // Запуск приложения
        runBot(context);
    }

    private static void runBot(ConfigurableApplicationContext context) throws TelegramApiException, IOException, ClassNotFoundException {
        // Создание Telegram клиента
        final TelegramClient telegramClient = new OkHttpTelegramClient(Config.BOT_TOKEN);

        // Создание бота
        final DefaultTelegramBot telegramBot = new DefaultTelegramBot(telegramClient);

        // Регистрация бота
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(Config.BOT_TOKEN, telegramBot);

        // Настройка валидатора уровня доступа
        telegramBot.setAccessLevelValidator(new AccessLevelValidator(dbUserService));

        // Добавление обработчиков
        telegramBot.addHandler(new StartHandler(dbUserService));

        // Вывод информации о системе
        printSystemInfo();
    }

    private static void printSystemInfo() {
        // Вывод информации о проекте
        log.info("=== Информация о проекте ===");
        log.info("Автор проекта: {} {} {}", "se1dhe", "[t.me/se1dhe]" , "[se1dhe.dev]");
        log.info("Название проекта: {}", Config.BOT_NAME);
        log.info("Версия проекта: {}", System.getProperty("java.version"));
        log.info("Используемая JVM: {}", System.getProperty("java.vm.name"));
        log.info("Операционная система: {}", System.getProperty("os.name"));
        log.info("=== ====");

        // Получение IP-адреса сервера
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            log.info("IP-адрес сервера: {}", ipAddress);
        } catch (Exception e) {
            log.error("Не удалось получить IP-адрес сервера", e);
        }

        // Получение доступной ОЗУ сервера
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        log.info("Использовано ОЗУ: {} MB", (usedMemory / (1024 * 1024)));
        log.info("=== ====");

    }
}
