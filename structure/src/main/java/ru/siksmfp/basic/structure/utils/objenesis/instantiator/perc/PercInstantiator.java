/**
 * Copyright 2006-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.siksmfp.basic.structure.utils.objenesis.instantiator.perc;

import ru.siksmfp.basic.structure.utils.objenesis.ObjenesisException;
import ru.siksmfp.basic.structure.utils.objenesis.instantiator.ObjectInstantiator;
import ru.siksmfp.basic.structure.utils.objenesis.instantiator.annotations.Instantiator;
import ru.siksmfp.basic.structure.utils.objenesis.instantiator.annotations.Typology;

import java.io.ObjectInputStream;
import java.lang.reflect.Method;

/**
 * Instantiates a class by making a call to internal Perc private methods. It is only supposed to
 * work on Perc JVMs. This instantiator will not call any constructors. The code was provided by
 * Aonix Perc support team.
 *
 * @author Henri Tremblay
 * @see org.objenesis.instantiator.ObjectInstantiator
 */
@Instantiator(Typology.STANDARD)
public class PercInstantiator<T> implements ObjectInstantiator<T> {

    private final Method newInstanceMethod;

    private final Object[] typeArgs = new Object[]{null, Boolean.FALSE};

    public PercInstantiator(Class<T> type) {

        typeArgs[0] = type;

        try {
            newInstanceMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class,
                    Boolean.TYPE);
            newInstanceMethod.setAccessible(true);
        } catch (RuntimeException e) {
            throw new ObjenesisException(e);
        } catch (NoSuchMethodException e) {
            throw new ObjenesisException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public T newInstance() {
        try {
            return (T) newInstanceMethod.invoke(null, typeArgs);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }

}
