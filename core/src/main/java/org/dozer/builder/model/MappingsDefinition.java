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
package org.dozer.builder.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.classmap.ClassMap;
import org.dozer.classmap.Configuration;

/**
 * The document root.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "mappings")
public class MappingsDefinition {

    @XmlTransient
    private String schemaLocation;

    @XmlElement(name = "configuration")
    private ConfigurationDefinition configuration;

    @XmlElement(name = "mapping")
    private List<MappingDefinition> mapping;

    public ConfigurationDefinition getConfiguration() {
        return configuration;
    }

    public List<MappingDefinition> getMapping() {
        return mapping;
    }

    public void setConfiguration(ConfigurationDefinition configuration) {
        this.configuration = configuration;
    }

    public void setMapping(List<MappingDefinition> mapping) {
        this.mapping = mapping;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public ConfigurationDefinition withConfiguration() {
        return new ConfigurationDefinition(this);
    }

    public MappingDefinition addMapping() {
        if (mapping == null) {
            mapping = new ArrayList<MappingDefinition>();
        }

        MappingDefinition mapping = new MappingDefinition(this);
        getMapping().add(mapping);

        return mapping;
    }

    public List<ClassMap> build(Configuration configuration) {
        List<ClassMap> answer = new ArrayList<ClassMap>();

        if (mapping != null) {
            for (MappingDefinition current : mapping) {
                answer.add(current.build(configuration));
            }
        }

        return answer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("configuration", configuration)
            .append("mapping", mapping)
            .toString();
    }
}
