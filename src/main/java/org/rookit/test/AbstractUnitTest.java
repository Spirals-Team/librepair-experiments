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

import com.google.common.base.MoreObjects;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import io.github.glytching.junit.extension.folder.TemporaryFolder;
import io.github.glytching.junit.extension.folder.TemporaryFolderExtension;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.rookit.utils.log.validator.Validator;
import org.rookit.utils.reflect.DynamicObject;
import org.rookit.utils.reflect.MethodEnricher;

import java.util.Collection;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SuppressWarnings("javadoc")
@ExtendWith(TemporaryFolderExtension.class)
public abstract class AbstractUnitTest<T> implements ObjectTest<T>, ArchTest {

    protected static final Validator VALIDATOR = TestValidator.getSingleton();
    public static final Object[] EMTPY_ARGUMENTS = new Object[0];

    public static <E> void testCollectionOps(final CollectionOps<E> collectionOps, final Supplier<E> itemSupplier) {
        CollectionOpsTester.test(collectionOps, itemSupplier);
    }

    protected TemporaryFolder temporaryFolder;

    protected T testResource;
    private JavaClasses javaClass;

    @Override
    public JavaClasses getJavaClasses() {
        return this.javaClass;
    }

    @Override
    public T getTestResource() {
        return this.testResource;
    }

    @BeforeEach
    public final void setupGuineaPig() {
        this.testResource = createTestResource();
        this.javaClass = new ClassFileImporter().importClasses(this.testResource.getClass());
    }

    @Test
    public final void testCreatorDoesNotReturnNull() {
        assertThat(createTestResource())
                .as("Creating a non null test object")
                .isNotNull();
    }

    @Test
    public final Collection<DynamicTest> testMethodReturnValue() {
        final DynamicObject dynamicObject = DynamicObject.fromObject(this.testResource);
        final String className = this.testResource.getClass().getName();
        final Collection<DynamicTest> tests = Lists.newArrayList();
        for (final MethodEnricher method : dynamicObject.methods()) {
            if (!method.returnsVoid() && (method.arguments().length == 0)) {
                final Object returnValue = method.apply(EMTPY_ARGUMENTS);
                final DynamicTest returnValueTest = dynamicTest(String.format("Return value of %s cannot be null",
                        method.name()),
                        () -> assertThat(returnValue)
                                .as("Invoking method %s of class %s does not return null",
                                        method.name(), className)
                                .isNotNull());
                tests.add(returnValueTest);

                if (Collection.class.isAssignableFrom(method.returnType())) {
                    final Collection<?> returnedCollection = (Collection<?>) returnValue;
                    final DynamicTest collectionTest = dynamicTest(String.format("Collection from %s is immutable",
                            method.name()),
                            () -> assertThatThrownBy(() -> returnedCollection.add(null))
                                    .as("The returned collection must be immutable")
                                    .isInstanceOf(UnsupportedOperationException.class));

                    tests.add(collectionTest);
                }
            }
        }
        return tests;
    }

    @TestFactory
    public final Collection<DynamicTest> testArgumentsNotNull() {
        final String className = this.testResource.getClass().getName();
        final DynamicObject dynamicObject = DynamicObject.fromObject(this.testResource);
        final Collection<DynamicTest> tests = Lists.newArrayList();

        for (final MethodEnricher methodEnricher : dynamicObject.methods()) {
            final Class<?>[] arguments = methodEnricher.arguments();
            if ((arguments.length > 0) && !"equals".equals(methodEnricher.name())) {
                final Object[][] objects = cylcleNull(arguments);
                for (int i = 0; i < arguments.length; i++) {
                    final String display = String.format("Method %s::%s cannot accept null in argument of index %d",
                            className, methodEnricher.name(), i);
                    final int index = i;
                    final DynamicTest test = dynamicTest(display, () -> assertThatThrownBy(
                            () -> methodEnricher.apply(objects[index]))
                            .as("Calling %s::%s where argument of index %d is null should not be allowed.",
                                    className, methodEnricher.name(), index)
                            .isInstanceOf(IllegalArgumentException.class));
                    tests.add(test);
                }
            }
        }
        return tests;
    }

    @SuppressWarnings("AssignmentToNull")
    private Object[][] cylcleNull(final Class<?>[] arguments) {
        final int length = arguments.length;
        final Object[] mocks = createMocks(arguments);
        final Object[][] objects = new Object[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                objects[i][j] = (i == j) ? null : mocks[j];
            }
        }
        return objects;
    }

    private Object[] createMocks(final Class<?>[] types) {
        final Object[] objects = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            objects[i] = mock(types[i]);
        }
        return objects;
    }

    private Object mock(final Class<?> type) {
        if (type.equals(int.class) || type.equals(Integer.class)
                || type.equals(short.class) || type.equals(Short.class)
                || type.equals(long.class) || type.equals(Long.class)
                || type.equals(double.class) || type.equals(Double.class)
                || type.equals(float.class) || type.equals(Float.class)) {
            return 2;
        }
        if (type.equals(String.class)) {
            return "mockedString";
        }

        return Mockito.mock(type);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("temporaryFolder", this.temporaryFolder)
                .add("testResource", this.testResource)
                .add("javaClass", this.javaClass)
                .toString();
    }
}
