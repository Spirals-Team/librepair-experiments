/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.core.argument;

import java.util.Optional;
import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.qualifier.QualifiedType;

import static org.jdbi.v3.core.qualifier.Qualifiers.NVARCHAR;

/**
 * Provides instances of {@link Argument} for qualified types:
 *
 * <ul>
 * <li>{@code @NVarchar String} - bind the annotated element as NVARCHAR instead of VARCHAR</li>
 * </ul>
 */
enum BuiltInQualifiedArgumentFactory implements QualifiedArgumentFactory {
    INSTANCE;

    @Override
    public Optional<Argument> build(QualifiedType type, Object value, ConfigRegistry config) {
        if (QualifiedType.of(String.class, NVARCHAR).equals(type)) {
            String stringValue = (String) value;
            return Optional.of((pos, stmt, ctx) -> stmt.setNString(pos, stringValue));
        }
        return Optional.empty();
    }
}
