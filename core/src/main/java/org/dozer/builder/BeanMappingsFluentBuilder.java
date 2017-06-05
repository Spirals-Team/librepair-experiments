/*
 * Copyright 2005-2017 Dozer Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dozer.builder;

import java.util.Arrays;
import java.util.List;

import org.dozer.builder.model.ConfigurationDefinition;
import org.dozer.builder.model.MappingDefinition;
import org.dozer.builder.model.MappingsDefinition;
import org.dozer.classmap.Configuration;
import org.dozer.classmap.MappingFileData;

public abstract class BeanMappingsFluentBuilder implements BeanMappingsBuilder {

    private final MappingsDefinition mappingsDefinition = new MappingsDefinition();

    public BeanMappingsFluentBuilder() {
        configure();
    }

    protected abstract void configure();

    protected ConfigurationDefinition configuration() {
        return mappingsDefinition.withConfiguration();
    }

    protected MappingDefinition mapping() {
        return mappingsDefinition.addMapping();
    }

    @Override
    public List<MappingFileData> convert() {
        Configuration configuration = null;
        if (mappingsDefinition.getConfiguration() != null) {
            configuration = mappingsDefinition.getConfiguration().convert();
        }

        MappingFileData data = new MappingFileData();
        data.setConfiguration(configuration);
        data.getClassMaps().addAll(mappingsDefinition.build(configuration));

        return Arrays.asList(data);
    }
}
