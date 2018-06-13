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
package org.rookit.test;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SuppressWarnings("javadoc")
public interface RookitTest<T> {

    default Collection<DynamicTest> testNonPositiveIntegerArgument(final IntConsumer method) {
        return ImmutableList.<DynamicTest>builder()
                .add(dynamicTest("-10", () -> assertThatThrownBy(() -> method.accept(-10))
                        .as("This method is accepting non positive numbers.")
                        .isInstanceOf(IllegalArgumentException.class)))
                .add(dynamicTest("-1", () -> assertThatThrownBy(() -> method.accept(-1))
                        .as("This method is accepting non positive numbers.")
                        .isInstanceOf(IllegalArgumentException.class)))
                .add(dynamicTest("0", () -> assertThatThrownBy(() -> method.accept(0))
                        .as("This method is accepting non positive numbers.")
                        .isInstanceOf(IllegalArgumentException.class)))
                .build();
    }

    default Collection<DynamicTest> testBlankStringArgument(final Consumer<String> method) {
        final Executable executable = () -> assertThatThrownBy(() -> method.accept(" "))
                .as("This method is accepting a blank string")
                .isInstanceOf(IllegalArgumentException.class);
        return ImmutableList.<DynamicTest>builder()
                .addAll(testEmptyStringArgument(method))
                .add(dynamicTest("Blank String", executable))
                .build();
    }

    default Collection<DynamicTest> testEmptyStringArgument(final Consumer<String> method) {
        final Executable executable = () -> assertThatThrownBy(() -> method.accept(""))
                .as("This method is accepting an empty string as a value")
                .isInstanceOf(IllegalArgumentException.class);
        return ImmutableList.<DynamicTest>builder()
                .addAll(testNullArgument(method))
                .add(dynamicTest("Empty String", executable))
                .build();
    }

    default Collection<DynamicTest> testNullArgument(final Consumer<?> method) {
        final Executable executable = () -> assertThatThrownBy(() -> method.accept(null))
                .as("This method is accepting null as a value")
                .isInstanceOf(IllegalArgumentException.class);
        return ImmutableList.of(dynamicTest("Null", executable));
    }

    T getTestResource();
    
    T createTestResource();

}
