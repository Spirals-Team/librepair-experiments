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
 *
 */
package org.esigate.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * Collection parameter.
 * 
 * @author Alexis Thaveau
 */
public class ParameterCollection extends Parameter<Collection<String>> {

    public ParameterCollection(String name) {
        super(name);
    }

    public ParameterCollection(String name, String... defaultValue) {
        super(name, Arrays.asList(defaultValue));
    }

    @Override
    public Collection<String> getValue(Properties properties) {

        Collection<String> defaultValue = getDefaultValue();
        if (defaultValue == null) {
            defaultValue = Collections.emptyList();
        }
        return PropertiesUtil.getPropertyValue(properties, getName(), defaultValue);

    }
}
