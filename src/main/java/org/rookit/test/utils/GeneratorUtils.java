/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.test.utils;

import com.tngtech.archunit.thirdparty.com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

import org.rookit.test.TestValidator;
import org.rookit.test.generator.Generator;

@SuppressWarnings("javadoc")
public final class GeneratorUtils {

    public static final TestValidator VALIDATOR = TestValidator.getSingleton();
    private static final Random RANDOM = new Random();

    public static <E> Optional<E> randomlyConsume(final Consumer<E> consumer, final Generator<E> generator) {
        VALIDATOR.checkArgument().isNotNull(consumer, "consumer");
        VALIDATOR.checkArgument().isNotNull(generator, "generator");
        
        if (RANDOM.nextBoolean()) {
            final E item = generator.createRandom();
            consumer.accept(item);
            return Optional.of(item);
        }
        return Optional.empty();
    }

    public static <E> Collection<E> randomlyConsumeCollection(final Consumer<Collection<E>> consumer,
            final Generator<E> generator) {
        VALIDATOR.checkArgument().isNotNull(generator, "generator");
        VALIDATOR.checkArgument().isNotNull(consumer, "consumer");
        
        if (RANDOM.nextBoolean()) {
            return consumeCollection(consumer, generator.createRandomSet());
        }
        return ImmutableSet.of();
    }
    
    public static <E> Collection<E> randomlyConsumeCollection(final Consumer<Collection<E>> consumer,
            final Generator<E> generator,
            final Collection<E> exceptions) {
        VALIDATOR.checkArgument().isNotNull(consumer, "consumer");
        VALIDATOR.checkArgument().isNotNull(generator, "generator");
        VALIDATOR.checkArgument().isNotNull(exceptions, "exceptions");
        
        if (RANDOM.nextBoolean()) {
            return consumeCollection(consumer, generator.createRandomUniqueSet(exceptions));
        }
        return ImmutableSet.of();
    }
    
    private static <E> Collection<E> consumeCollection(final Consumer<Collection<E>> consumer,
            final Collection<E> items) {
        consumer.accept(items);
        return items;
    }

    private GeneratorUtils() {
    }

}
