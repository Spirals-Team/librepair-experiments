/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.TestClass;

/**
 * Custom Test Runner class.
 */
public class CustomRunner extends Runner {
    private final List<Method> testMethods = new ArrayList<Method>();
    private final TestClass testClass;

    /**
     * Instantiates a new custom runner.
     *
     * @param aClass the a class
     */
    public CustomRunner(final Class<?> aClass) {
        super();
        testClass = new TestClass(aClass);
        final Method[] classMethods = aClass.getDeclaredMethods();
        for (final Method classMethod : classMethods) {
            final Class<?> retClass = classMethod.getReturnType();
            final int length = classMethod.getParameterTypes().length;
            final int modifiers = classMethod.getModifiers();

            if (retClass == null
                    || length != 0
                    || Modifier.isStatic(modifiers)
                    || Modifier.isInterface(modifiers)
                    || Modifier.isAbstract(modifiers)
                    || !Modifier.isPublic(modifiers)) {
                continue;
            }

            final String methodName = classMethod.getName();
            if (methodName.toUpperCase().startsWith("TEST")
                    || classMethod.getAnnotation(Test.class) != null) {
                testMethods.add(classMethod);
            }

            if (classMethod.getAnnotation(Ignore.class) != null) {
                testMethods.remove(classMethod);
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.junit.runner.Runner#getDescription()
     */
    @Override
    public Description getDescription() {
        final String className = testClass.getName();
        final Class<?> javaClass = testClass.getJavaClass();
        final Annotation[] annotations = javaClass.getAnnotations();
        return Description.createSuiteDescription(className, annotations);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.junit.runner.Runner#run(org.junit.runner.notification.RunNotifier)
     */
    @Override
    public void run(final RunNotifier runNotifier) {
        for (int i = 0; i < testMethods.size(); i++) {
            final Method method = testMethods.get(i);
            final Class<? extends Method> methodClass = method.getClass();
            final String methodName = method.getName();
            runNotifier.fireTestStarted(Description.createTestDescription(methodClass, methodName));
        }
    }
}
