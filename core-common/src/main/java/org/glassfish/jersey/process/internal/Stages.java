/*
 * Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.process.internal;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.process.Inflector;

/**
 * A stage-related collection of utility methods.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public final class Stages {
    private static final ChainableStage IDENTITY = new AbstractChainableStage() {

        @Override
        public Continuation apply(Object o) {
            //noinspection unchecked
            return Continuation.of(o, getDefaultNext());
        }
    };

    /**
     * Prevents instantiation.
     */
    private Stages() {
    }

    /**
     * Get a chainable "identity" stage.
     *
     * This stage, when applied returns the unmodified input data object
     * as part of it's continuation.
     *
     * @param <DATA> data type transformable by the stage.
     * @return identity stage.
     */
    public static <DATA> ChainableStage<DATA> identity() {
        //noinspection unchecked
        return IDENTITY;
    }

    /**
     * Creates a terminal {@link Stage} that implements {@link Inflecting}
     * interface and returns the provided {@link Inflector} instance
     * when the {@link Inflecting#inflector()} method is called.
     *
     * @param <DATA>    data type transformable by the stage and returned inflector.
     * @param <RESULT>  type of result produced by a successful inflector data transformation.
     * @param inflector a request to response transformation to be wrapped in
     *                  a stage.
     * @return a stage that wraps the supplied {@code Inflector}.
     */
    @SuppressWarnings("unchecked")
    public static <DATA, RESULT> Stage<DATA> asStage(final Inflector<DATA, RESULT> inflector) {
        return new InflectingStage<DATA, RESULT>(inflector);
    }

    private static class InflectingStage<DATA, RESULT> implements Stage<DATA>, Inflecting<DATA, RESULT> {

        private final Inflector<DATA, RESULT> inflector;

        public InflectingStage(Inflector<DATA, RESULT> inflector) {
            this.inflector = inflector;
        }

        @Override
        public Inflector<DATA, RESULT> inflector() {
            return inflector;
        }

        @Override
        public Stage.Continuation<DATA> apply(DATA request) {
            return Continuation.of(request);
        }
    }

    /**
     * (Optionally) extracts an {@link Inflector inflector} from a processing stage,
     * provided the stage implements {@link Inflecting} interface. Otherwise method
     * returns {@code null}.
     *
     * @param <DATA>   data type transformable by the stage and returned inflector.
     * @param <RESULT> type of result produced by a successful inflector data transformation.
     * @param stage    a stage to extract the inflector from.
     * @return extracted inflector if present, {@code null} otherwise.
     */
    @SuppressWarnings("unchecked")
    public static <DATA, RESULT, T extends Inflector<DATA, RESULT>> T extractInflector(Object stage) {
        if (stage instanceof Inflecting) {
            return (T) ((Inflecting<DATA, RESULT>) stage).inflector();
        }

        return null;
    }

    /**
     * Start building a stage chain.
     *
     * @param transformation root transformation function.
     * @return linear accepting chain builder.
     */
    public static <DATA> Stage.Builder<DATA> chain(Function<DATA, DATA> transformation) {
        return new StageChainBuilder<DATA>(transformation);
    }

    /**
     * Start building a stage chain.
     *
     * @param rootStage root {@link ChainableStage chainable linear stage}.
     * @return linear accepting chain builder.
     */
    public static <DATA> Stage.Builder<DATA> chain(ChainableStage<DATA> rootStage) {
        return new StageChainBuilder<DATA>(rootStage);
    }

    /**
     * Run the data through a chain of stages identified by the root stage.
     *
     * @param <DATA>    processed data type.
     * @param data      data to be processed.
     * @param rootStage root stage of the stage chain.
     * @return processing result.
     */
    public static <DATA> DATA process(DATA data, Stage<DATA> rootStage) {
        Stage.Continuation<DATA> continuation = Stage.Continuation.of(data, rootStage);
        Stage<DATA> currentStage;
        while ((currentStage = continuation.next()) != null) {
            continuation = currentStage.apply(continuation.result());
        }
        return continuation.result();
    }

    /**
     * Run the data through a chain of stages identified by the root stage.
     *
     * If an inflector is found in the leaf stage, it's reference is set into the {@code inflectorRef}
     * parameter.
     *
     * @param <DATA>       processed data type.
     * @param data         data to be processed.
     * @param rootStage    root stage of the stage chain.
     * @param inflectorRef a mutable reference to an inflector.
     * @return processing result.
     */
    public static <DATA, RESULT, T extends Inflector<DATA, RESULT>> DATA process(
            DATA data,
            Stage<DATA> rootStage,
            Ref<T> inflectorRef) {

        Stage<DATA> lastStage = rootStage;
        Stage.Continuation<DATA> continuation = Stage.Continuation.of(data, lastStage);
        while (continuation.next() != null) {
            lastStage = continuation.next();
            continuation = lastStage.apply(continuation.result());
        }

        inflectorRef.set(Stages.<DATA, RESULT, T>extractInflector(lastStage));

        return continuation.result();
    }

    private static class StageChainBuilder<DATA> implements Stage.Builder<DATA> {

        private final Deque<Function<DATA, DATA>> transformations = new LinkedList<Function<DATA, DATA>>();
        private Stage<DATA> rootStage;
        private ChainableStage<DATA> lastStage;

        private StageChainBuilder(Function<DATA, DATA> transformation) {
            transformations.push(transformation);
        }

        private StageChainBuilder(ChainableStage<DATA> rootStage) {
            this.rootStage = rootStage;
            this.lastStage = rootStage;
        }

        @Override
        public Stage.Builder<DATA> to(Function<DATA, DATA> transformation) {
            transformations.push(transformation);
            return this;
        }

        @Override
        public Stage.Builder<DATA> to(final ChainableStage<DATA> stage) {
            addTailStage(stage);
            lastStage = stage;

            return this;
        }

        private void addTailStage(Stage<DATA> lastStage) {
            Stage<DATA> tail = lastStage;
            if (!transformations.isEmpty()) {
                tail = convertTransformations(lastStage);
            }
            if (rootStage != null) {
                this.lastStage.setDefaultNext(tail);
            } else {
                rootStage = tail;
            }
        }

        @Override
        public Stage<DATA> build(Stage<DATA> stage) {
            addTailStage(stage);

            return rootStage;
        }

        @Override
        public Stage<DATA> build() {
            return build(null);
        }

        private Stage<DATA> convertTransformations(Stage<DATA> successor) {
            Stage<DATA> stage;
            if (successor == null) {
                stage = new LinkedStage<DATA>(transformations.poll());
            } else {
                stage = new LinkedStage<DATA>(transformations.poll(), successor);
            }

            Function<DATA, DATA> t;
            while ((t = transformations.poll()) != null) {
                stage = new LinkedStage<DATA>(t, stage);
            }

            return stage;
        }
    }

    /**
     * Linked linear stage implementation.
     *
     * @param <DATA> processed data type.
     */
    public static class LinkedStage<DATA> implements Stage<DATA> {

        private final Stage<DATA> nextStage;
        private final Function<DATA, DATA> transformation;

        /**
         * Create a new stage that will return the supplied stage in the
         * continuation.
         *
         * @param transformation Request transformation function to be applied in the stage.
         * @param nextStage      next stage returned in the continuation.
         */
        public LinkedStage(Function<DATA, DATA> transformation, Stage<DATA> nextStage) {
            this.nextStage = nextStage;
            this.transformation = transformation;
        }

        /**
         * Create a new terminal stage .
         *
         * @param transformation Request transformation function to be applied in the stage.
         */
        public LinkedStage(Function<DATA, DATA> transformation) {
            this(transformation, null);
        }

        @Override
        public Stage.Continuation<DATA> apply(DATA data) {
            return Continuation.of(transformation.apply(data), nextStage);
        }
    }
}
