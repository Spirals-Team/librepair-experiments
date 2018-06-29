package de.malkusch.trashcollection.infrastructure.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.Event;
import de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
final class StateMachineRunner implements ApplicationRunner {

	private final StateMachine<State, Event> machine;

	@Autowired
	StateMachineRunner(StateMachine<State, Event> machine) {
		this.machine = machine;
	}

	@Override
	public void run(ApplicationArguments args) {
		machine.start();
	}

}
