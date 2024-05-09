package dev.se1dhe.bot.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
class HibernateConf {


    @Bean
    public DriverManagerDataSource dataSource() throws IOException {
        Config.load();


        final DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUsername(Config.DB_USER);
        source.setPassword(Config.DB_PWD);
        source.setUrl(Config.DB_URL);

        return source;
    }
}
