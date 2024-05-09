package dev.se1dhe.bot.service;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


public class LocalizationService {
    private static final String STRINGS_FILE = "strings";
    private static final Object lock = new Object();

    @Getter
    private static final List<Language> supportedLanguages = new ArrayList<>();
    private static final Utf8ResourceBundle defaultLanguage;
    private static final Utf8ResourceBundle ukrainian;
    private static final Utf8ResourceBundle english;


    static {
        synchronized (lock) {
            defaultLanguage = new Utf8ResourceBundle(STRINGS_FILE, new Locale("ru"));
            supportedLanguages.add(new Language("en", "English"));
            english = new Utf8ResourceBundle(STRINGS_FILE, new Locale("en"));
            supportedLanguages.add(new Language("ua", "Ukrainian"));
            ukrainian = new Utf8ResourceBundle(STRINGS_FILE, new Locale("ua"));

        }
    }

    public static String getString(String key) {
        String result;
        try {
            result = defaultLanguage.getString(key);
        } catch (MissingResourceException e) {
            result = "String not found";
        }

        return result;
    }

    public static String getString(String key, String language) {
        String result;
        try {
            switch (language.toLowerCase()) {
                case "en":
                    result = english.getString(key);
                    break;
                case "ua":
                    result = ukrainian.getString(key);
                    break;
                default:
                    result = defaultLanguage.getString(key);
                    break;
            }
        } catch (MissingResourceException e) {
            result = defaultLanguage.getString(key);
        }

        return result;
    }

    public static Language getLanguageByCode(String languageCode) {
        return supportedLanguages.stream().filter(x -> x.getCode().equals(languageCode)).findFirst().orElse(null);
    }

    public static Language getLanguageByName(String languageName) {
        return supportedLanguages.stream().filter(x -> x.getName().equals(languageName)).findFirst().orElse(null);
    }

    public static String getLanguageCodeByName(String language) {
        return supportedLanguages.stream().filter(x -> x.getName().equals(language))
                .map(Language::getCode).findFirst().orElse(null);
    }

    @Setter
    @Getter
    public static class Language {
        private String code;
        private String name;
        private String emoji;

        public Language(String code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public String toString() {
            if (emoji == null || emoji.isEmpty()) {
                return name;
            } else {
                return emoji + " " + name;
            }
        }
    }

    private static class Utf8ResourceBundle extends ResourceBundle {

        private static final String BUNDLE_EXTENSION = "properties";
        private static final String CHARSET = "UTF-8";
        private static final Control UTF8_CONTROL = new UTF8Control();

        private final Properties props;

        Utf8ResourceBundle(String bundleName, Locale locale) {
            String fileName = "config/messages/" + bundleName + "_" + locale.toString() + ".properties";
            try (InputStream is = new FileInputStream(fileName)) {
                props = new Properties();
                props.load(new InputStreamReader(is, CHARSET));
            } catch (IOException e) {
                throw new RuntimeException("Cannot load resource bundle: " + fileName, e);
            }
        }

        @Override
        protected Object handleGetObject(@NotNull String key) {
            return props.getProperty(key);
        }

        @NotNull
        @Override
        public Enumeration<String> getKeys() {
            return Collections.enumeration(props.stringPropertyNames());
        }

        private static class UTF8Control extends Control {
            public ResourceBundle newBundle
                    (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                    throws IOException {
                String bundleName = toBundleName(baseName, locale);
                String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
                ResourceBundle bundle = null;
                InputStream stream = null;
                if (reload) {
                    URL url = loader.getResource(resourceName);
                    if (url != null) {
                        URLConnection connection = url.openConnection();
                        if (connection != null) {
                            connection.setUseCaches(false);
                            stream = connection.getInputStream();
                        }
                    }
                } else {
                    stream = loader.getResourceAsStream(resourceName);
                }
                if (stream != null) {
                    try {
                        bundle = new PropertyResourceBundle(new InputStreamReader(stream, CHARSET));
                    } finally {
                        stream.close();
                    }
                }
                return bundle;
            }
        }
    }
}