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

package org.dominokit.jacksonapt.deser.array.cast;

import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.array.AbstractArrayJsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for array of {@link java.lang.String}.
 * <p>Not working in production mode, cast problem. Can maybe work with disableCastChecking</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class StringArrayJsonDeserializer extends AbstractArrayJsonDeserializer<String[]> {

    private static final StringArrayJsonDeserializer INSTANCE = new StringArrayJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link org.dominokit.jacksonapt.deser.array.cast.StringArrayJsonDeserializer}
     */
    public static StringArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }



    private StringArrayJsonDeserializer() {
    }

    /** {@inheritDoc} */
    @Override
    public String[] doDeserializeArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        return JacksonContextProvider.get().stringArrayReader().readArray(reader);
    }


    /** {@inheritDoc} */
    @Override
    protected String[] doDeserializeSingleArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        return new String[]{reader.nextString()};
    }
}
