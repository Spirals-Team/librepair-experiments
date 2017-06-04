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
package org.dozer.builder.model.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.builder.model.AbstractDefinition;
import org.dozer.builder.model.mapping.ConverterTypeDefinition;
import org.dozer.config.BeanContainer;
import org.dozer.converters.CustomConverterDescription;

@XmlRootElement(name = "custom-converters")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomConvertersDefinition  {

    @XmlElement(name = "converter", required = true)
    private List<ConverterTypeDefinition> converter;

    public CustomConvertersDefinition() {

    }

    public List<ConverterTypeDefinition> getConverter() {
        return converter;
    }

    public void setConverter(List<ConverterTypeDefinition> converter) {
        this.converter = converter;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public ConverterTypeDefinition addConverter() {
        if (getConverter() == null) {
            setConverter(new ArrayList<ConverterTypeDefinition>());
        }

        ConverterTypeDefinition converter = new ConverterTypeDefinition();
        getConverter().add(converter);

        return converter;
    }

    public List<CustomConverterDescription> convert() {
        List<CustomConverterDescription> answer = new ArrayList<CustomConverterDescription>();
        for (ConverterTypeDefinition current : converter) {
            answer.add(current.convert());
        }

        return answer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("converter", converter)
            .toString();
    }
}
