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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
public interface SerializationTest<T extends Serializable> extends ObjectTest<T> {

    Gson GSON = new GsonBuilder().create();
    
    Class<T> getTestResourceType();

    @Test
    default void testJavaRoundtripSerialization() throws Exception {
        final T testResource = getTestResource();
        final ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
        final ObjectOutputStream serializer = new ObjectOutputStream(bytesOutput);
        serializer.writeObject(testResource);

        final ByteArrayInputStream bytesInput = new ByteArrayInputStream(bytesOutput.toByteArray());
        final ObjectInputStream deserializer = new ObjectInputStream(bytesInput);
        final Object deserialized = deserializer.readObject();

        assertThat(deserialized)
                .as("The deserialized object")
                .isEqualTo(testResource);

    }

    @Test
    default void testJsonRoundtripSerialization() {
        final T testResource = getTestResource();
        final Class<T> testResourceClass = getTestResourceType();
        final String serialized = GSON.toJson(testResource);

        final T deserialized = GSON.fromJson(serialized, testResourceClass);

        assertThat(deserialized)
                .as("The deserialized object")
                .isEqualTo(testResource);
    }

}
