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
import org.dozer.classmap.CopyByReference;
import org.dozer.config.BeanContainer;

@XmlRootElement(name = "copy-by-references")
@XmlAccessorType(XmlAccessType.FIELD)
public class CopyByReferencesDefinition extends AbstractDefinition {

    @XmlElement(name = "copy-by-reference", required = true)
    private List<String> copyByReference;

    public CopyByReferencesDefinition() {
        setELEngine(BeanContainer.getInstance().getElEngine());
    }

    public List<String> getCopyByReference() {
        return copyByReference;
    }

    public void setCopyByReference(List<String> copyByReference) {
        this.copyByReference = copyByReference;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public CopyByReferencesDefinition addCopyByReference(String copyByReference) {
        if (getCopyByReference() == null) {
            setCopyByReference(new ArrayList<String>());
        }

        getCopyByReference().add(copyByReference);

        return this;
    }

    public List<CopyByReference> convert() {
        List<CopyByReference> answer = new ArrayList<CopyByReference>();
        for (String current : copyByReference) {
            answer.add(new CopyByReference(resolveELExpression(current)));
        }

        return answer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("copyByReference", copyByReference)
            .toString();
    }
}
