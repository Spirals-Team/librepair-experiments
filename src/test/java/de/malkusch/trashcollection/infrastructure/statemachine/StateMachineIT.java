package de.malkusch.trashcollection.infrastructure.statemachine;

import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.Event.WAITED;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State.INITIAL_WAITING;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State.PREPARATION_DAY;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State.TESTING;
import static de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State.WAITING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.Event;
import de.malkusch.trashcollection.infrastructure.statemachine.StateMachineConfiguration.State;
import de.malkusch.trashcollection.model.NextTrashCollectionRepository;
import de.malkusch.trashcollection.model.TrashCollection;
import de.malkusch.trashcollection.model.TrashCollectionScheduleService;
import de.malkusch.trashcollection.test.IntegrationTest;
import de.malkusch.trashcollection.test.TestButton;

@IntegrationTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
public class StateMachineIT {

	@Autowired
	private StateMachine<State, Event> stateMachine;

	@Autowired
	private TestButton button;

	@Autowired
	private TrashCollectionScheduleService scheduleService;

	@Autowired
	private NextTrashCollectionRepository nextTrashCollectionRepository;

	@Test
	public void shouldStartIn_TESTING() {
		TrashCollection expected = scheduleService.findNextCollection();

		assertState(TESTING);
		assertEquals(expected, currentTrashCollection());
	}
	
	@Test
	public void shouldBeIn_INITIAL_After_TESTING() {
		TrashCollection expected = scheduleService.findNextCollection();
		assumeState(TESTING);
		
		button.press();

		assertState(INITIAL_WAITING);
		assertEquals(expected, currentTrashCollection());
	}

	@Test
	public void shouldIgnore_BUTTON_PRESSED_in_INITIAL() {
		stepTo_INITIAL_WAITING();
		TrashCollection expected = scheduleService.findNextCollection();

		button.press();

		assertState(INITIAL_WAITING);
		assertEquals(expected, currentTrashCollection());
	}

	@Test
	public void shouldIgnore_BUTTON_PRESSED_in_WAITING() throws Exception {
		stepTo_WAITING();
		TrashCollection expected = currentTrashCollection();

		button.press();

		assertState(WAITING);
		assertEquals(expected, currentTrashCollection());
	}

	@Test
	public void shouldBeIn_PREPARATION_DAY_After_INITIAL() {
		stepTo_INITIAL_WAITING();
		TrashCollection expected = currentTrashCollection();

		stateMachine.sendEvent(WAITED);

		assertState(PREPARATION_DAY);
		assertEquals(expected, currentTrashCollection());
	}

	@Test
	@Ignore
	public void shouldBeIn_WAITING_AfterOneDayTimeout() {
	}

	@Test
	public void shouldBeIn_WAITING_After_PREPARATION_DAY() {
		stepTo_PREPARATION_DAY();
		TrashCollection expected = scheduleService.findNextCollectionAfter(currentTrashCollection());

		button.press();

		assertState(WAITING);
		assertEquals(expected, currentTrashCollection());
	}

	@Test
	public void testStepThrough() throws Exception {
		assumeState(TESTING);
		button.press();
		assumeState(INITIAL_WAITING);
		stateMachine.sendEvent(WAITED);
		assumeState(PREPARATION_DAY);
		button.press();
		assumeState(WAITING);
		stateMachine.sendEvent(WAITED);
		assumeState(PREPARATION_DAY);

		button.press();
		assertState(WAITING);
	}

	private void stepTo_INITIAL_WAITING() {
		assumeState(TESTING);
		button.press();
		assumeState(INITIAL_WAITING);
	}

	private void stepTo_PREPARATION_DAY() {
		stepTo_INITIAL_WAITING();
		stateMachine.sendEvent(WAITED);
		assumeState(PREPARATION_DAY);
	}
	
	private void stepTo_WAITING() {
		stepTo_PREPARATION_DAY();
		button.press();
		assumeState(WAITING);
	}
	
	private TrashCollection currentTrashCollection() {
		return nextTrashCollectionRepository.find().trashCollection();
	}

	private void assertState(State state) {
		assertEquals(state, stateMachine.getState().getId());
	}

	private void assumeState(State state) {
		assumeTrue(stateMachine.getState().getId() == state);
	}

}
