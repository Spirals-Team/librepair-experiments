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

import javax.xml.bind.annotation.XmlTransient;

import org.dozer.loader.xml.ELEngine;

@XmlTransient
public abstract class AbstractDefinition {

    @XmlTransient
    private ELEngine elEngine;

    protected void setELEngine(ELEngine elEngine) {
        this.elEngine = elEngine;
    }

    protected void setELVariable(String name, String value) {
        if (name != null && value != null) {
            if (elEngine != null) {
                elEngine.setVariable(name, value);
            }
        }
    }

    protected String resolveELExpression(String expression) {
        String resolvedExpression = expression;
        if (expression != null && elEngine != null) {
            resolvedExpression = elEngine.resolve(expression);
        }

        return resolvedExpression;
    }
}
