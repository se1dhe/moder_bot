package dev.se1dhe.bot.statemachine.config;

import dev.se1dhe.bot.statemachine.enums.Event;
import dev.se1dhe.bot.statemachine.enums.State;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.processor.StateMachineAnnotationPostProcessor;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<State, Event> {

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states)
            throws Exception {

        states
                .withStates()
                .initial(State.STATE_INITIAL)
                .end(State.STATE_FINISH)
                .states(new HashSet<>(Arrays.asList(State.STATE1, State.STATE2, State.STATE3)));

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions)
            throws Exception {

        transitions
                .withExternal()
                .source(State.STATE_INITIAL).target(State.STATE1).event(Event.EVENT1).and()
                .withExternal()
                .source(State.STATE1).target(State.STATE2).event(Event.USER_RESPONSE).action(saveNameAction()).and()
                .withExternal()
                .source(State.STATE2).target(State.STATE3).event(Event.USER_RESPONSE).action(saveAgeAction()).and()
                .withExternal()
                .source(State.STATE3).target(State.STATE_FINISH).event(Event.END).action(finalAction());
    }

    @Bean
    public Action<State, Event> saveNameAction() {
        return ctx -> {
            String userResponse = (String) ctx.getExtendedState().getVariables().get("userResponse");
            ctx.getExtendedState().getVariables().put("name", userResponse);
            System.out.println("User responded to first question: " + userResponse);
        };
    }

    @Bean
    public Action<State, Event> saveAgeAction() {
        return ctx -> {
            String userResponse = (String) ctx.getExtendedState().getVariables().get("userResponse");
            ctx.getExtendedState().getVariables().put("age", userResponse);
            System.out.println("User responded to second question: " + userResponse);
        };
    }

    @Bean
    public Action<State, Event> finalAction() {
        return ctx -> {
            String name = ctx.getExtendedState().getVariables().get("name").toString();
            String age = (String) ctx.getExtendedState().getVariables().get("age");
            String response = String.format("Привет, %s! Вам %s лет.", name, age);
            System.out.println(response); // Логируем ответ для отладки
            // Здесь можно добавить отправку сообщения пользователю
        };
    }
}