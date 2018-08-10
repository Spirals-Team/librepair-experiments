package org.corfudb.runtime.exceptions;

import lombok.RequiredArgsConstructor;

/**
 * Created by dalia on 4/8/17.
 */
@RequiredArgsConstructor
public enum AbortCause {
    CONFLICT,
    OVERWRITE, /** Aborted because of slow writer, i.e., continuously gets overwritten (hole filled by faster reader) */
    NEW_SEQUENCER,
    USER,
    NETWORK,
    TRIM, /** Aborted because an access to this snapshot resulted in a trim exception. */
    SEQUENCER_OVERFLOW,
    SEQUENCER_TRIM,
    UNSUPPORTED,
    UNDEFINED;
}
