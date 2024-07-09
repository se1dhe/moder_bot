package dev.se1dhe.bot.statemachine.config;

import dev.se1dhe.bot.statemachine.enums.Events;
import dev.se1dhe.bot.statemachine.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.START)
                .state(States.ASK_NAME)
                .end(States.END);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.START).target(States.ASK_NAME).event(Events.START_EVENT)
                .and()
                .withExternal()
                .source(States.ASK_NAME).target(States.END).event(Events.ASK_NAME_EVENT);
    }

    @Bean
    public static StateMachineListenerAdapter<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                if (from != null) {
                    System.out.println("State changed from " + from.getId() + " to " + to.getId());
                } else {
                    System.out.println("State changed to " + to.getId());
                }
            }
        };
    }
}
