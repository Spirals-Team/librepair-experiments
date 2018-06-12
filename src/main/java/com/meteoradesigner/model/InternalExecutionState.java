package com.meteoradesigner.model;

//TODO fix all the documentation, by using this class, -es and dots.
//TODO AFTER servises will be DONE: add to code and requirements: default state ??is ..SERIAL or

/**
 * InternalExecutionState - not the entity, obligatory internal execution state for a task.
 */
public enum InternalExecutionState {
    INTERNAL_EXECUTION_STATE_SERIAL,
    INTERNAL_EXECUTION_STATE_CONCURRENT,
    INTERNAL_EXECUTION_STATE_PARALLEL,
    INTERNAL_EXECUTION_STATE_MONO,
    INTERNAL_EXECUTION_STATE_SERIAL_STRICT,
    INTERNAL_EXECUTION_STATE_CONCURRENT_STRICT,
    INTERNAL_EXECUTION_STATE_PARALLEL_STRICT,
    INTERNAL_EXECUTION_STATE_MONO_STRICT,
}

