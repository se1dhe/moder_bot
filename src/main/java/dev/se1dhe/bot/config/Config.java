package dev.se1dhe.bot.config;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;


@Log4j2
public class Config {

    public static final String CONFIGURATION_BOT_FILE = "config/bot.properties";
    public static final String CONFIGURATION_DB_FILE = "config/database.properties";

    public static String BOT_NAME;
    public static String BOT_TOKEN;

    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PWD;





    public static void load() throws IOException {
        final PropertiesParser botConfig = new PropertiesParser(CONFIGURATION_BOT_FILE);
        final PropertiesParser botDBConfig = new PropertiesParser(CONFIGURATION_DB_FILE);


        BOT_NAME = botConfig.getString("bot.name", "contest_bot");
        BOT_TOKEN = botConfig.getString("bot.token", "1590228823:AAE5CS0GZXyOEFj_wreUV48vGclDmSIcdjA");



        DB_URL = botDBConfig.getString("bot.url", "jdbc:mysql://127.0.0.1:3306/bot?useUnicode=true&character_set_server=utf8mb4&autoReconnect=true&interactiveClient=true&serverTimezone=Europe/Kiev&useSSL=false");
        DB_USER = botDBConfig.getString("bot.username", "root");
        DB_PWD = botDBConfig.getString("bot.password", "1234");





    }

}
