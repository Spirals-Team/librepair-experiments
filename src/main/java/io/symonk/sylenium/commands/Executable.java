package io.symonk.sylenium.commands;


/**
 * Interface for managing commands we can issue to selenium
 * @param <T> -> The return type expected from issuing the command
 */
public interface Executable<T> {

    T issueCommand();
}
