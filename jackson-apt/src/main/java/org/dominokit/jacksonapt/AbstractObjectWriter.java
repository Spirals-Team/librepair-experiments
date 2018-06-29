/*
 * Copyright 2013 Nicolas Morel
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

package org.dominokit.jacksonapt;

/**
 * Base implementation of {@link org.dominokit.jacksonapt.ObjectWriter}. Extends {@link org.dominokit.jacksonapt.AbstractObjectMapper} to avoid code duplication, trying to read with this
 * writer will result in an {@link java.lang.UnsupportedOperationException}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractObjectWriter<T> extends AbstractObjectMapper<T> implements ObjectWriter<T> {

    /**
     * <p>Constructor for AbstractObjectWriter.</p>
     *
     * @param rootName a {@link java.lang.String} object.
     */
    public AbstractObjectWriter(String rootName) {
        super(rootName);
    }

    /** {@inheritDoc} */
    @Override
    protected final JsonDeserializer<T> newDeserializer() {
        throw new UnsupportedOperationException("ObjectWriter doesn't support deserialization");
    }
}
