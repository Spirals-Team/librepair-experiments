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

package org.dominokit.jacksonapt.server.ser.number;

import org.dominokit.jacksonapt.ser.BaseNumberJsonSerializer;
import org.dominokit.jacksonapt.server.ser.AbstractJsonSerializerTest;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author Nicolas Morel
 */
public class BigDecimalJsonSerializerTest extends AbstractJsonSerializerTest<BigDecimal> {

    @Override
    protected BaseNumberJsonSerializer.BigDecimalJsonSerializer createSerializer() {
        return BaseNumberJsonSerializer.BigDecimalJsonSerializer.getInstance();
    }

    @Test
	public void testSerializeValue() {
        BigDecimal value = new BigDecimal("15487846511321245665435132032454.1545815468465578451323888744");
        assertSerialization("15487846511321245665435132032454.1545815468465578451323888744", value);
    }

}
