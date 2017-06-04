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
package org.dozer.builder.model.mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.builder.model.AbstractDefinition;
import org.dozer.config.BeanContainer;
import org.dozer.converters.CustomConverterDescription;
import org.dozer.util.MappingUtils;

@XmlRootElement(name = "converter-type")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConverterTypeDefinition extends AbstractDefinition {

    @XmlElement(name = "class-a", required = true)
    private ClassDefinition classA;

    @XmlElement(name = "class-b", required = true)
    private ClassDefinition classB;

    @XmlAttribute(name = "type", required = true)
    private String type;

    public ConverterTypeDefinition() {
        setELEngine(BeanContainer.getInstance().getElEngine());
    }

    public ClassDefinition getClassA() {
        return classA;
    }

    public ClassDefinition getClassB() {
        return classB;
    }

    public String getType() {
        return type;
    }

    public void setClassA(ClassDefinition classA) {
        this.classA = classA;
    }

    public void setClassB(ClassDefinition classB) {
        this.classB = classB;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public ClassDefinition withClassA() {
        ClassDefinition classA = new ClassDefinition();
        setClassA(classA);

        return classA;
    }

    public ClassDefinition withClassB() {
        ClassDefinition classB = new ClassDefinition();
        setClassB(classB);

        return classB;
    }

    public ConverterTypeDefinition withType(String type) {
        setType(type);

        return this;
    }

    public CustomConverterDescription convert() {
        CustomConverterDescription converterDescription = new CustomConverterDescription();
        converterDescription.setClassA(MappingUtils.loadClass(resolveELExpression(classA.getClazz())));
        converterDescription.setClassB(MappingUtils.loadClass(resolveELExpression(classB.getClazz())));
        converterDescription.setType(MappingUtils.loadClass(resolveELExpression(type)));

        return converterDescription;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("classA", classA)
            .append("classB", classB)
            .append("type", type)
            .toString();
    }
}
