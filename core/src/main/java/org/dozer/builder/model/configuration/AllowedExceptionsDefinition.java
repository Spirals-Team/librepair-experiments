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

import org.dozer.builder.model.AbstractDefinition;
import org.dozer.config.BeanContainer;
import org.dozer.util.MappingUtils;

@XmlRootElement(name = "allowed-exceptions")
@XmlAccessorType(XmlAccessType.FIELD)
public class AllowedExceptionsDefinition extends AbstractDefinition {

    @XmlElement(name = "exception", required = true)
    private List<String> exceptions;

    public AllowedExceptionsDefinition() {
        setELEngine(BeanContainer.getInstance().getElEngine());
    }

    public List<String> getException() {
        return exceptions;
    }

    public void setException(List<String> exceptions) {
        this.exceptions = exceptions;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public AllowedExceptionsDefinition addException(String exception) {
        if (getException() == null) {
            setException(new ArrayList<String>());
        }

        getException().add(exception);

        return this;
    }

    @SuppressWarnings("unchecked")
    public List<Class<RuntimeException>> build() {
        List<Class<RuntimeException>> answer = new ArrayList<Class<RuntimeException>>();
        for (String current : exceptions) {
            Class runtimeException = MappingUtils.loadClass(resolveELExpression(current));
            if (!RuntimeException.class.isAssignableFrom(runtimeException)) {
                MappingUtils.throwMappingException("allowed-exceptions must extend java.lang.RuntimeException. Found: "
                                                   + runtimeException.getName());
            }

            answer.add(runtimeException);
        }

        return answer;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
            .append("exception", exceptions)
            .toString();
    }
}
