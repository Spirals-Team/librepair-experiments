/*
 * Copyright 2010, 2013, 2015-2017 Jan Ouwens
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
package nl.jqno.equalsverifier.internal.util;

import nl.jqno.equalsverifier.internal.prefabvalues.TypeTag;
import nl.jqno.equalsverifier.internal.reflection.ClassAccessor;
import nl.jqno.equalsverifier.internal.reflection.FieldAccessor;
import nl.jqno.equalsverifier.internal.reflection.FieldIterable;
import nl.jqno.equalsverifier.internal.reflection.ObjectAccessor;

import java.lang.reflect.Field;
import java.util.Set;

public class FieldInspector<T> {
    private final ClassAccessor<T> classAccessor;
    private final TypeTag typeTag;

    public FieldInspector(ClassAccessor<T> classAccessor, TypeTag typeTag) {
        this.classAccessor = classAccessor;
        this.typeTag = typeTag;
    }

    public void check(FieldCheck check) {
        for (Field field : FieldIterable.of(classAccessor.getType())) {
            ObjectAccessor<T> reference = classAccessor.getRedAccessor(typeTag);
            ObjectAccessor<T> changed = classAccessor.getRedAccessor(typeTag);

            check.execute(reference.fieldAccessorFor(field), changed.fieldAccessorFor(field));
        }
    }

    public void checkWithNull(Set<String> nonnullFields, FieldCheck check) {
        for (Field field : FieldIterable.of(classAccessor.getType())) {
            ObjectAccessor<T> reference = classAccessor.getDefaultValuesAccessor(typeTag, nonnullFields);
            ObjectAccessor<T> changed = classAccessor.getDefaultValuesAccessor(typeTag, nonnullFields);

            check.execute(reference.fieldAccessorFor(field), changed.fieldAccessorFor(field));
        }
    }

    public interface FieldCheck {
        void execute(FieldAccessor referenceAccessor, FieldAccessor changedAccessor);
    }
}
