/*     / \____  _    ______   _____ / \____   ____  _____
 *    /  \__  \/ \  / \__  \ /  __//  \__  \ /    \/ __  \   Javaslang
 *  _/  // _\  \  \/  / _\  \\_  \/  // _\  \  /\  \__/  /   Copyright 2014-2015 Daniel Dietrich
 * /___/ \_____/\____/\_____/____/\___\_____/_/  \_/____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.control;

/**
 * A {@link Match} throws a MatchError if no case matches the applied object.
 *
 * @since 1.0.0
 */
public class MatchError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Object obj;

    /**
     * Internally called by {@link Match}.
     *
     * @param obj The object which could not be matched.
     */
    MatchError(Object obj) {
        super((obj == null) ? "null" : "type: " + obj.getClass().getName() + ", value: " + obj);
        this.obj = obj;
    }

    /**
     * Returns the object which could not be matched.
     *
     * @return An Object.
     */
    public Object getObject() {
        return obj;
    }

}
