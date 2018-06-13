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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.rookit.test.preconditions.TestPreconditions.assure;

@SuppressWarnings("javadoc")
public interface ObjectTest<T> extends RookitTest<T> {

    @Test
    default void testEquals() {
        final T testResource = getTestResource();

        assertThat(testResource)
                .as("The self object")
                .isEqualTo(createTestResource())
                .isNotEqualTo(new Object())
                .isNotEqualTo(null)
                .isEqualTo(testResource);
    }

    @Test
    default void testHashCode() {
        final T testResource1 = getTestResource();
        final T testResource2 = createTestResource();
        assure().isEquals(testResource1, testResource2, "test resources");

        assertThat(testResource1.hashCode())
                .as("The self hash code")
                .isEqualTo(testResource1.hashCode())
                .isEqualTo(testResource2.hashCode());
    }

    @Test
    default void testToString() {
        final T testResource = getTestResource();

        assertThat(testResource.toString())
                .as("The string version of the test resource")
                .isNotEmpty();
    }

}
