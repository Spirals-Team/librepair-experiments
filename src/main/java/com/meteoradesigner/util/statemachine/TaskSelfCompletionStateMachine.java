package com.meteoradesigner.util.statemachine;

import com.meteoradesigner.model.SelfCompletionStateEvents;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import static com.meteoradesigner.model.SelfCompletionState.SELF_COMPLETION_STATE_ACTIVE;
import static com.meteoradesigner.model.SelfCompletionState.SELF_COMPLETION_STATE_COMPLETED;
import static com.meteoradesigner.model.SelfCompletionState.SELF_COMPLETION_STATE_DISABLED;
import static com.meteoradesigner.model.SelfCompletionState.SELF_COMPLETION_STATE_ENABLED;
import static com.meteoradesigner.model.SelfCompletionState.SELF_COMPLETION_STATE_FROZEN;
import static com.meteoradesigner.model.SelfCompletionState
        .SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES;
import static com.meteoradesigner.model.SelfCompletionState
        .SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY;
import static com.meteoradesigner.model.SelfCompletionState.SELF_COMPLETION_STATE_NEW;
import static com.meteoradesigner.model.SelfCompletionState.SELF_COMPLETION_STATE_TENTATIVE;


//TODO not String actions and events can cause errors.
//TODO not adding special event for each transition can cause errors, but logically better to use only one event
// for all transitions.
//TODO fix docs from New possible only to Disabled or Enabled
//TODO fix docs from Disabled possible only to Enabled

/**
 * This @code{TaskSelfCompletionStateMachine} class represents configuration
 * of @code{StateMachine} for handling @code{SelfCompletionState}s.
 */
@Configuration
@EnableStateMachine
public class TaskSelfCompletionStateMachine
        extends StateMachineConfigurerAdapter<String, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states.withStates()
              .initial(SELF_COMPLETION_STATE_NEW.name())
              .state(SELF_COMPLETION_STATE_DISABLED.name())
              .state(SELF_COMPLETION_STATE_ENABLED.name())
              .state(SELF_COMPLETION_STATE_FROZEN.name())
              .state(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
              .state(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
              .state(SELF_COMPLETION_STATE_ACTIVE.name())
              .state(SELF_COMPLETION_STATE_COMPLETED.name())
              .state(SELF_COMPLETION_STATE_TENTATIVE.name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions

                //to Disabled
                .withLocal()
                .source(SELF_COMPLETION_STATE_NEW.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromNewToDisabledAction())
                .guard(fromNewToDisabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_DISABLED.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromDisabledToDisabledAction())
                .guard(fromDisabledToDisabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromFrozenToDisabledAction())
                .guard(fromFrozenToDisabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ACTIVE.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromActiveToDisabledAction())
                .guard(fromActiveToDisabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ENABLED.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromEnabledToDisabledAction())
                .guard(fromEnabledToDisabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_COMPLETED.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromCompletedToDisabledAction())
                .guard(fromCompletedToDisabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromFrozenWithForciblyUnlockedRelativesToDisabledAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesToDisabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromFrozenWithForciblyUnlockedRelativesByToDisabledAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesByToDisabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_TENTATIVE.name())
                .target(SELF_COMPLETION_STATE_DISABLED.name())
                .event(SelfCompletionStateEvents.SET_DISABLED.name())
                .action(fromTentativeToDisabledAction())
                .guard(fromTentativeToDisabledGuard())
                .and()

                //to Enabled
                .withLocal()
                .source(SELF_COMPLETION_STATE_NEW.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromNewToEnabledAction())
                .guard(fromNewToEnabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_DISABLED.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromDisabledToEnabledAction())
                .guard(fromDisabledToEnabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_TENTATIVE.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromTentativeToEnabledAction())
                .guard(fromTentativeToEnabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromFrozenToEnabledAction())
                .guard(fromFrozenToEnabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ACTIVE.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromActiveToEnabledAction())
                .guard(fromActiveToEnabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_COMPLETED.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromCompletedToEnabledAction())
                .guard(fromCompletedToEnabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ENABLED.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromEnabledToEnabledAction())
                .guard(fromEnabledToEnabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromFrozenWithForciblyUnlockedRelativesByToEnabledAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesByToEnabledGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .target(SELF_COMPLETION_STATE_ENABLED.name())
                .event(SelfCompletionStateEvents.SET_ENABLED.name())
                .action(fromFrozenWithForciblyUnlockedRelativesToEnabledAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesToEnabledGuard())
                .and()

                //to Frozen
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN.name())
                .target(SELF_COMPLETION_STATE_FROZEN.name())
                .event(SelfCompletionStateEvents.SET_FROZEN.name())
                .action(fromFrozenToFrozenAction())
                .guard(fromFrozenToFrozenGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ENABLED.name())
                .target(SELF_COMPLETION_STATE_FROZEN.name())
                .event(SelfCompletionStateEvents.SET_FROZEN.name())
                .action(fromEnabledToFrozenAction())
                .guard(fromEnabledToFrozenGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .target(SELF_COMPLETION_STATE_FROZEN.name())
                .event(SelfCompletionStateEvents.SET_FROZEN.name())
                .action(fromFrozenWithForciblyUnlockedRelativesByToFrozenAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesByToFrozenGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .target(SELF_COMPLETION_STATE_FROZEN.name())
                .event(SelfCompletionStateEvents.SET_FROZEN.name())
                .action(fromFrozenWithForciblyUnlockedRelativesToFrozenAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesToFrozenGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ACTIVE.name())
                .target(SELF_COMPLETION_STATE_FROZEN.name())
                .event(SelfCompletionStateEvents.SET_FROZEN.name())
                .action(fromActiveToFrozenAction())
                .guard(fromActiveToFrozenGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_TENTATIVE.name())
                .target(SELF_COMPLETION_STATE_FROZEN.name())
                .event(SelfCompletionStateEvents.SET_FROZEN.name())
                .action(fromTentativeToFrozenAction())
                .guard(fromTentativeToFrozenGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_COMPLETED.name())
                .target(SELF_COMPLETION_STATE_FROZEN.name())
                .event(SelfCompletionStateEvents.SET_FROZEN.name())
                .action(fromCompletedToFrozenAction())
                .guard(fromCompletedToFrozenGuard())
                .and()

                //to FrozenWithForciblyUnlockedRelativesBy
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .action(fromFrozenWithForciblyUnlockedRelativesByToFrozenWithForciblyUnlockedRelativesByAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesByToFrozenWithForciblyUnlockedRelativesByGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ENABLED.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .action(fromEnabledToFrozenWithForciblyUnlockedRelativesByAction())
                .guard(fromEnabledToFrozenWithForciblyUnlockedRelativesByGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .action(fromFrozenToFrozenWithForciblyUnlockedRelativesByAction())
                .guard(fromFrozenToFrozenWithForciblyUnlockedRelativesByGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .action(fromFrozenWithForciblyUnlockedRelativesToFrozenWithForciblyUnlockedRelativesByAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesToFrozenWithForciblyUnlockedRelativesByGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ACTIVE.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .action(fromActiveToFrozenWithForciblyUnlockedRelativesByAction())
                .guard(fromActiveToFrozenWithForciblyUnlockedRelativesByGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_TENTATIVE.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .action(fromTentativeToFrozenWithForciblyUnlockedRelativesByAction())
                .guard(fromTentativeToFrozenWithForciblyUnlockedRelativesByGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_COMPLETED.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .action(fromCompletedToFrozenWithForciblyUnlockedRelativesByAction())
                .guard(fromCompletedToFrozenWithForciblyUnlockedRelativesByGuard())
                .and()

                //to FrozenWithForciblyUnlockedRelatives
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .action(fromFrozenWithForciblyUnlockedRelativesToFrozenWithForciblyUnlockedRelativesAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesToFrozenWithForciblyUnlockedRelativesGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ENABLED.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .action(fromEnabledToFrozenWithForciblyUnlockedRelativesAction())
                .guard(fromEnabledToFrozenWithForciblyUnlockedRelativesGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .action(fromFrozenToFrozenWithForciblyUnlockedRelativesAction())
                .guard(fromFrozenToFrozenWithForciblyUnlockedRelativesGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .action(fromFrozenWithForciblyUnlockedRelativesByToFrozenWithForciblyUnlockedRelativesAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesByToFrozenWithForciblyUnlockedRelativesGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ACTIVE.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .action(fromActiveToFrozenWithForciblyUnlockedRelativesAction())
                .guard(fromActiveToFrozenWithForciblyUnlockedRelativesGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_TENTATIVE.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .action(fromTentativeToFrozenWithForciblyUnlockedRelativesAction())
                .guard(fromTentativeToFrozenWithForciblyUnlockedRelativesGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_COMPLETED.name())
                .target(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .event(SelfCompletionStateEvents.SET_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .action(fromCompletedToFrozenWithForciblyUnlockedRelativesAction())
                .guard(fromCompletedToFrozenWithForciblyUnlockedRelativesGuard())
                .and()

                //to Active
                .withLocal()
                .source(SELF_COMPLETION_STATE_ACTIVE.name())
                .target(SELF_COMPLETION_STATE_ACTIVE.name())
                .event(SelfCompletionStateEvents.SET_ACTIVE.name())
                .action(fromActiveToActiveAction())
                .guard(fromActiveToActiveGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ENABLED.name())
                .target(SELF_COMPLETION_STATE_ACTIVE.name())
                .event(SelfCompletionStateEvents.SET_ACTIVE.name())
                .action(fromEnabledToActiveAction())
                .guard(fromEnabledToActiveGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN.name())
                .target(SELF_COMPLETION_STATE_ACTIVE.name())
                .event(SelfCompletionStateEvents.SET_ACTIVE.name())
                .action(fromFrozenToActiveAction())
                .guard(fromFrozenToActiveGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .target(SELF_COMPLETION_STATE_ACTIVE.name())
                .event(SelfCompletionStateEvents.SET_ACTIVE.name())
                .action(fromFrozenWithForciblyUnlockedRelativesByToActiveAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesByToActiveGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .target(SELF_COMPLETION_STATE_ACTIVE.name())
                .event(SelfCompletionStateEvents.SET_ACTIVE.name())
                .action(fromFrozenWithForciblyUnlockedRelativesToActiveAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesToActiveGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_TENTATIVE.name())
                .target(SELF_COMPLETION_STATE_ACTIVE.name())
                .event(SelfCompletionStateEvents.SET_ACTIVE.name())
                .action(fromTentativeToActiveAction())
                .guard(fromTentativeToActiveGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_COMPLETED.name())
                .target(SELF_COMPLETION_STATE_ACTIVE.name())
                .event(SelfCompletionStateEvents.SET_ACTIVE.name())
                .action(fromCompletedToActiveAction())
                .guard(fromCompletedToActiveGuard())
                .and()

                //to Tentative
                .withLocal()
                .source(SELF_COMPLETION_STATE_TENTATIVE.name())
                .target(SELF_COMPLETION_STATE_TENTATIVE.name())
                .event(SelfCompletionStateEvents.SET_TENTATIVE.name())
                .action(fromTentativeToTentativeAction())
                .guard(fromTentativeToTentativeGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_COMPLETED.name())
                .target(SELF_COMPLETION_STATE_TENTATIVE.name())
                .event(SelfCompletionStateEvents.SET_TENTATIVE.name())
                .action(fromCompletedToTentativeAction())
                .guard(fromCompletedToTentativeGuard())
                .and()

                //to Completed
                .withLocal()
                .source(SELF_COMPLETION_STATE_COMPLETED.name())
                .target(SELF_COMPLETION_STATE_COMPLETED.name())
                .event(SelfCompletionStateEvents.SET_COMPLETED.name())
                .action(fromCompletedToCompletedAction())
                .guard(fromCompletedToCompletedGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ENABLED.name())
                .target(SELF_COMPLETION_STATE_COMPLETED.name())
                .event(SelfCompletionStateEvents.SET_COMPLETED.name())
                .action(fromEnabledToCompletedAction())
                .guard(fromEnabledToCompletedGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN.name())
                .target(SELF_COMPLETION_STATE_COMPLETED.name())
                .event(SelfCompletionStateEvents.SET_COMPLETED.name())
                .action(fromFrozenToCompletedAction())
                .guard(fromFrozenToCompletedGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY.name())
                .target(SELF_COMPLETION_STATE_COMPLETED.name())
                .event(SelfCompletionStateEvents.SET_COMPLETED.name())
                .action(fromFrozenWithForciblyUnlockedRelativesByToCompletedAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesByToCompletedGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES.name())
                .target(SELF_COMPLETION_STATE_COMPLETED.name())
                .event(SelfCompletionStateEvents.SET_COMPLETED.name())
                .action(fromFrozenWithForciblyUnlockedRelativesToCompletedAction())
                .guard(fromFrozenWithForciblyUnlockedRelativesToCompletedGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_ACTIVE.name())
                .target(SELF_COMPLETION_STATE_COMPLETED.name())
                .event(SelfCompletionStateEvents.SET_COMPLETED.name())
                .action(fromActiveToCompletedAction())
                .guard(fromActiveToCompletedGuard())
                .and()
                .withLocal()
                .source(SELF_COMPLETION_STATE_TENTATIVE.name())
                .target(SELF_COMPLETION_STATE_COMPLETED.name())
                .event(SelfCompletionStateEvents.SET_COMPLETED.name())
                .action(fromTentativeToCompletedAction())
                .guard(fromTentativeToCompletedGuard());

    }


    //Actions to Disabled

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromNewToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromDisabledToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromFrozenToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromActiveToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromEnabledToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromCompletedToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromTentativeToDisabledAction() {
        return (context) -> {/*add some action*/};
    }

    //Actions to Enabled

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromNewToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromDisabledToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromEnabledToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromFrozenToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromActiveToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromTentativeToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromCompletedToEnabledAction() {
        return (context) -> {/*add some action*/};
    }

    //Actions to Frozen

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromFrozenToFrozenAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromEnabledToFrozenAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToFrozenAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesToFrozenAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromActiveToFrozenAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromTentativeToFrozenAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromCompletedToFrozenAction() {
        return (context) -> {/*add some action*/};
    }

    //Actions to FrozenWithForciblyUnlockedRelativesBy

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToFrozenWithForciblyUnlockedRelativesByAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromEnabledToFrozenWithForciblyUnlockedRelativesByAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenToFrozenWithForciblyUnlockedRelativesByAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesToFrozenWithForciblyUnlockedRelativesByAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromActiveToFrozenWithForciblyUnlockedRelativesByAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromTentativeToFrozenWithForciblyUnlockedRelativesByAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromCompletedToFrozenWithForciblyUnlockedRelativesByAction() {
        return (context) -> {/*add some action*/};
    }

    //Actions to FrozenWithForciblyUnlockedRelativesBy

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesToFrozenWithForciblyUnlockedRelativesAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromEnabledToFrozenWithForciblyUnlockedRelativesAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenToFrozenWithForciblyUnlockedRelativesAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToFrozenWithForciblyUnlockedRelativesAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromActiveToFrozenWithForciblyUnlockedRelativesAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromTentativeToFrozenWithForciblyUnlockedRelativesAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromCompletedToFrozenWithForciblyUnlockedRelativesAction() {
        return (context) -> {/*add some action*/};
    }

    //Actions to Active

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromActiveToActiveAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromEnabledToActiveAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromFrozenToActiveAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToActiveAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesToActiveAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromTentativeToActiveAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromCompletedToActiveAction() {
        return (context) -> {/*add some action*/};
    }

    //Actions to Tentative

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromTentativeToTentativeAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromCompletedToTentativeAction() {
        return (context) -> {/*add some action*/};
    }

    //Actions to Completed

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromCompletedToCompletedAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromEnabledToCompletedAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromFrozenToCompletedAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToCompletedAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String>
    fromFrozenWithForciblyUnlockedRelativesToCompletedAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromActiveToCompletedAction() {
        return (context) -> {/*add some action*/};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Action<String, String> fromTentativeToCompletedAction() {
        return (context) -> {/*add some action*/};
    }

    //Guards to Disabled

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromNewToDisabledGuard() {
        return (context) -> {/*add some action*/ return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromDisabledToDisabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromFrozenToDisabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromActiveToDisabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromEnabledToDisabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromCompletedToDisabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToDisabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesToDisabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromTentativeToDisabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    //Guards to Enabled

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromNewToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromDisabledToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromEnabledToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromFrozenToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromActiveToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromTentativeToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromCompletedToEnabledGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    //Guards to Frozen

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromFrozenToFrozenGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromEnabledToFrozenGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToFrozenGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesToFrozenGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromActiveToFrozenGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromTentativeToFrozenGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromCompletedToFrozenGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    //Guards to FrozenWithForciblyUnlockedRelativesBy

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToFrozenWithForciblyUnlockedRelativesByGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromEnabledToFrozenWithForciblyUnlockedRelativesByGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenToFrozenWithForciblyUnlockedRelativesByGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesToFrozenWithForciblyUnlockedRelativesByGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromActiveToFrozenWithForciblyUnlockedRelativesByGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromTentativeToFrozenWithForciblyUnlockedRelativesByGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromCompletedToFrozenWithForciblyUnlockedRelativesByGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    //Guards to FrozenWithForciblyUnlockedRelativesBy

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesToFrozenWithForciblyUnlockedRelativesGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromEnabledToFrozenWithForciblyUnlockedRelativesGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenToFrozenWithForciblyUnlockedRelativesGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToFrozenWithForciblyUnlockedRelativesGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromActiveToFrozenWithForciblyUnlockedRelativesGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromTentativeToFrozenWithForciblyUnlockedRelativesGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromCompletedToFrozenWithForciblyUnlockedRelativesGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    //Guards to Active

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromActiveToActiveGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromEnabledToActiveGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromFrozenToActiveGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToActiveGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesToActiveGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromTentativeToActiveGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromCompletedToActiveGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    //Guards to Tentative

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromTentativeToTentativeGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromCompletedToTentativeGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    //Guards to Completed

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromCompletedToCompletedGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromEnabledToCompletedGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromFrozenToCompletedGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesByToCompletedGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String>
    fromFrozenWithForciblyUnlockedRelativesToCompletedGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromActiveToCompletedGuard() {
        return (context) -> {/*add some action*/return false;};
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public Guard<String, String> fromTentativeToCompletedGuard() {
        return (context) -> {/*add some action*/return false;};
    }
}