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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.builder.model.AbstractDefinition;
import org.dozer.config.BeanContainer;

@XmlRootElement(name = "variable")
@XmlAccessorType(XmlAccessType.FIELD)
public class VariableDefinition extends AbstractDefinition {

    @XmlValue
    private String clazz;

    @XmlAttribute(name = "name", required = true)
    private String name;

    public VariableDefinition() {
        setELEngine(BeanContainer.getInstance().getElEngine());
    }

    public String getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setName(String value) {
        this.name = value;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public VariableDefinition withClazz(String clazz) {
        setClazz(clazz);

        return this;
    }

    public VariableDefinition withName(String value) {
        setName(value);

        return this;
    }

    public void build() {
        setELVariable(name, clazz);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("clazz", clazz)
            .append("name", name)
            .toString();
    }
}
