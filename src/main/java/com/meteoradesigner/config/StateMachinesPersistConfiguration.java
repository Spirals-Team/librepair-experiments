package com.meteoradesigner.config;

import com.meteoradesigner.util.statemachine.TaskSelfCompletionStateMachinePersistHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

/**
 * This @class{StateMachinePersistConfiguration} class represents configuration for the project's state machines.
 */
@Configuration
public class StateMachinesPersistConfiguration {

    @Autowired
    StateMachine<String,String> stateMachine;

    @Bean
    public TaskSelfCompletionStateMachinePersistHandler taskSelfCompletionStateMachinePersistHandler() {
        return new TaskSelfCompletionStateMachinePersistHandler(persistStateMachineHandler());
    }

    @Bean
    public PersistStateMachineHandler persistStateMachineHandler() {
        return new PersistStateMachineHandler(stateMachine);
    }


}
