/*
 * Copyright (C) 2011 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.glassfish.jersey.internal.guava;

import java.util.concurrent.Future;

/**
 * Transforms a value, possibly asynchronously. For an example usage and more
 * information, see {@link Futures#transform(ListenableFuture, AsyncFunction)}.
 *
 * @author Chris Povirk
 * @since 11.0
 */
interface AsyncFunction<I, O> {
    /**
     * Returns an output {@code Future} to use in place of the given {@code
     * input}. The output {@code Future} need not be {@linkplain Future#isDone
     * done}, making {@code AsyncFunction} suitable for asynchronous derivations.
     * <p>
     * <p>Throwing an exception from this method is equivalent to returning a
     * failing {@code Future}.
     */
    ListenableFuture<O> apply(I input);
}
