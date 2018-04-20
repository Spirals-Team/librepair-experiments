/*
 * Copyright (c) 2018 Alex de Kruijff <alex.de.kruijff@MazarineBlue.org>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.mazarineblue.eventnotifier;

import java.lang.reflect.Method;
import static java.util.Arrays.stream;
import org.mazarineblue.eventnotifier.exceptions.EventHandlerMissingException;
import org.mazarineblue.eventnotifier.exceptions.IllegalEventHandlerException;
import org.mazarineblue.eventnotifier.exceptions.MissingSubscriberExcpetion;

public class EventServiceUtil {

    public static void checkSubscriber(Subscriber<?> subscriber) {
        if (subscriber == null)
            throw new MissingSubscriberExcpetion();
        if (subscriber instanceof ReflectionSubscriber)
            checkReflectionSubscriber(subscriber);
    }

    private static void checkReflectionSubscriber(Subscriber<?> subscriber) {
        if (!hasHandler(subscriber))
            throw new EventHandlerMissingException(subscriber);
        checkHandlersAreValid(subscriber);
    }

    private static boolean hasHandler(Subscriber<?> subscriber) {
        return stream(subscriber.getClass().getMethods())
                .anyMatch(method -> isEventHandler(method));
    }

    private static void checkHandlersAreValid(Subscriber<?> subscriber) {
        for (Method method : subscriber.getClass().getMethods())
            if (!isEventHandler(method))
                continue;
            else if (isValidEventHandler(method))
                continue;
            else
                throw new IllegalEventHandlerException(subscriber, method);
    }

    private static boolean isEventHandler(Method method) {
        return method.getAnnotation(EventHandler.class) != null;
    }

    private static boolean isValidEventHandler(Method handler) {
        if (!isReturnTypeVoid(handler))
            return false;
        else if (!isParameterEvent(handler))
            return false;
        else
            for (Method m : Subscriber.class.getMethods())
                if (!isReturnTypeVoid(m))
                    continue;
                else if (!isParameterEvent(m))
                    continue;
                else if (!isParameterAssignable(m, handler))
                    continue;
                else
                    return true;
        return false;
    }

    private static boolean isReturnTypeVoid(Method m) {
        return Void.TYPE.isAssignableFrom(m.getReturnType());
    }

    private static boolean isParameterEvent(Method m) {
        Class<?>[] type = m.getParameterTypes();
        return type.length != 1 ? false : Event.class.isAssignableFrom(type[0]);
    }

    private static boolean isParameterAssignable(Method expected, Method actual) {
        Class<?>[] expectedType = expected.getParameterTypes();
        Class<?>[] actualType = actual.getParameterTypes();
        return expectedType[0].isAssignableFrom(actualType[0]);
    }

    private EventServiceUtil() {
    }
}
