package com.meteoradesigner.model;

//TODO fix all the documentation, by using this class, -es and dots.
//TODO fix the scheme: from NONE position to any (paint the lines)
//TODO fix the transitions: add NONE transition's paragraph (to is possible just for new instances
//TODO fix the transitions: add to possible previous states for each state "from NONE state"

/**
 * SelfCompletionState - not the entity, obligatory self completion state for a task.
 */
public enum SelfCompletionState {
    SELF_COMPLETION_STATE_NEW,
    SELF_COMPLETION_STATE_DISABLED,
    SELF_COMPLETION_STATE_ENABLED,
    SELF_COMPLETION_STATE_FROZEN,
    SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES_BY,
    SELF_COMPLETION_STATE_FROZEN_WITH_FORCIBLY_UNLOCKED_RELATIVES,
    SELF_COMPLETION_STATE_ACTIVE,
    SELF_COMPLETION_STATE_COMPLETED,
    SELF_COMPLETION_STATE_TENTATIVE,
}