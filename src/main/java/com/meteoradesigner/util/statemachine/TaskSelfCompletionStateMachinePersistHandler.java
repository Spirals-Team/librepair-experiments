package com.meteoradesigner.util.statemachine;

import com.meteoradesigner.dto.TaskDto;
import com.meteoradesigner.model.SelfCompletionState;
import com.meteoradesigner.service.task.TaskService;
import com.meteoradesigner.util.validator.CommonValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.function.Predicate;

//TODO
@Configuration
public class TaskSelfCompletionStateMachinePersistHandler {

    private PersistStateMachineHandler persistStateMachineHandler;

    @Autowired
    private StateMachine<String, String> stateMachine;

    @Autowired
    private TaskService taskService;

    //TODO need to make listener final or not
    public TaskSelfCompletionStateMachinePersistHandler(PersistStateMachineHandler persistStateMachineHandler) {
        this.persistStateMachineHandler = persistStateMachineHandler;
        persistStateMachineHandler.addPersistStateChangeListener(new PersistStateChangeListenerImpl());
    }

    public void changeState(int id,
                            String event) {
        TaskDto taskDto = taskService.find(id);
        persistStateMachineHandler.handleEventWithState(
                MessageBuilder
                        .withPayload(event)
                        .setHeader("taskId", id)
                        .build(),
                taskDto.getInternalExecutionState().name()
        );
        //TODO check handling
    }

    //Complete
    class PersistStateChangeListenerImpl implements PersistStateMachineHandler.PersistStateChangeListener {
        @Override
        public void onPersist(State<String, String> state,
                              Message<String> message,
                              Transition<String, String> transition,
                              StateMachine<String, String> stateMachine) {

            Predicate<Message<String>> messagePredicate = msg -> {
                CommonValidatorUtil.notNull(msg, "");
                return msg.getHeaders().containsKey("taskId");
            };

            if (messagePredicate.test(message)) {
                Integer id = message.getHeaders().get("task", Integer.class);
                taskService.find(id).setSelfCompletionState(SelfCompletionState.valueOf(state.getId()));
            }
        }
    }
}