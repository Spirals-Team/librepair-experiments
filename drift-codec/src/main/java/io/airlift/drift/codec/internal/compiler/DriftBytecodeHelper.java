/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.airlift.drift.codec.internal.compiler;

import java.lang.reflect.Method;

public final class DriftBytecodeHelper
{
    public static final Method NO_CONSTRUCTOR_FOUND;

    static {
        try {
            NO_CONSTRUCTOR_FOUND = DriftBytecodeHelper.class.getMethod("noConstructorFound", Class.class, short.class);
        }
        catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    private DriftBytecodeHelper()
    {
    }

    public static Throwable noConstructorFound(Class<?> clazz, short fieldId)
    {
        return new IllegalStateException(String.format("No constructor for Union %s with field id %s found", clazz.getSimpleName(), fieldId));
    }
}
