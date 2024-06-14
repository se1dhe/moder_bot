package dev.se1dhe.bot.statemachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.processor.StateMachineAnnotationPostProcessor;

@Configuration
public class StateMachineProcessorConfig {

    @Bean
    public static StateMachineAnnotationPostProcessor stateMachineAnnotationPostProcessor() {
        return new StateMachineAnnotationPostProcessor();
    }
}