package de.malkusch.trashcollection.infrastructure.statemachine;

import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.Event.BUTTON_PRESSED;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.Event.WAITED;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State.INITIAL_WAITING;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State.PREPARATION_DAY;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State.TESTING;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State.WAITING;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import de.malkusch.trashcollection.application.shownext.ShowNextTrashCollectionHandler;
import de.malkusch.trashcollection.application.shownext.ShowNextTrashCollectionResult;
import de.malkusch.trashcollection.application.waitnext.WaitForNextTrashCollectionHandler;
import de.malkusch.trashcollection.application.waitnext.WaitForNextTrashCollectionResult;
import de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.Event;
import de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State;
import de.malkusch.trashcollection.presentation.button.TrashCanButton;
import de.malkusch.trashcollection.presentation.display.TrashCanDisplay;

@Configuration
@EnableStateMachine
class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<State, Event> {

	enum State {
		TESTING, INITIAL_WAITING, WAITING, PREPARATION_DAY
	}
	
	enum Event {
		WAITED, BUTTON_PRESSED
	}
	
	@Autowired
	private WaitForNextTrashCollectionHandler waitNextHandler;
	
	@Autowired
	private ShowNextTrashCollectionHandler showNextHandler;
	
	@Autowired
	private TaskScheduler scheduler;
	
	@Autowired
	private TrashCanButton button;
	
	@Autowired
	private TrashCanDisplay display;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineConfiguration.class);

	@Override
	public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
		states.withStates()
			.initial(TESTING, withErrorHandler(testingAction()))
			.stateEntry(INITIAL_WAITING, withErrorHandler(initialWaitingEntryAction()))
			.stateEntry(WAITING, withErrorHandler(waitingEntryAction()))
			.state(PREPARATION_DAY, withErrorHandler(preparationDayEntryAction()), withErrorHandler(preparationDayExitAction()));
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
		transitions
				.withExternal()
					.source(TESTING).target(INITIAL_WAITING)
					.timerOnce(MINUTES.toMillis(3))
			.and()
				.withExternal()
					.source(TESTING).target(INITIAL_WAITING)
					.event(BUTTON_PRESSED)
			.and()
				.withExternal()
					.source(INITIAL_WAITING).target(PREPARATION_DAY)
					.event(WAITED)
			.and()
				.withExternal()
					.source(WAITING).target(PREPARATION_DAY)
					.event(WAITED)
			.and()
				.withExternal()
					.source(PREPARATION_DAY).target(WAITING)
					.event(BUTTON_PRESSED)
			.and()
				.withExternal()
					.source(PREPARATION_DAY).target(WAITING)
					.timerOnce(DAYS.toMillis(1));
	}

	private Action<State, Event> testingAction() {
		return context -> {
			LOGGER.debug("Testing display and button");
			sendEventOnButtonPressed(context, BUTTON_PRESSED);
			display.test();
		};
	}
	
	private Action<State, Event> initialWaitingEntryAction() {
		return context -> {
			LOGGER.debug("Waiting for PREPARATION_DAY");
			ShowNextTrashCollectionResult result = showNextHandler.show();
			scheduleEvent(context, WAITED, result.preparationDate);
			button.stopWaiting();
			display.showWaiting();
		};
	}
	
	private Action<State, Event> waitingEntryAction() {
		return context -> {
			LOGGER.debug("Waiting for PREPARATION_DAY");
			WaitForNextTrashCollectionResult result = waitNextHandler.waitForNextTrashCollection();
			scheduleEvent(context, WAITED, result.preparationDate);
			display.showWaiting();
		};
	}

	private Action<State, Event> preparationDayEntryAction() {
		return context -> {
			LOGGER.debug("Waiting for button");
			ShowNextTrashCollectionResult result = showNextHandler.show();
			sendEventOnButtonPressed(context, BUTTON_PRESSED);
			display.showTrashCans(result.trashCans);
		};
	}

	private Action<State, Event> preparationDayExitAction() {
		return context -> button.stopWaiting();
	}
	
	private Action<State, Event> withErrorHandler(Action<State, Event> action) {
		return context -> {
			try {
				action.execute(context);

			} catch (Exception error) {
				LOGGER.error(error.getMessage(), error);
				display.showError(error);
				throw error;
			}
		};
	}

	private void sendEventOnButtonPressed(StateContext<State, Event> context, Event event) {
		button.waitForPressed(() -> context.getStateMachine().sendEvent(event));
	}
	
	private void scheduleEvent(StateContext<State, Event> context, Event event, LocalDate date) {
		scheduler.schedule(() -> context.getStateMachine().sendEvent(event), convert(date));
		LOGGER.debug("Scheduled {} for {}", event, date);
	}
	
	private static Date convert(LocalDate date) {
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
}
