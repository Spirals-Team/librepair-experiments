/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.mylasta.omoide.ognl;

import java.util.Map;

import ognl.ObjectPropertyAccessor;
import ognl.OgnlException;

import org.lastaflute.di.core.LaContainer;

/**
 * @author modified by jflute (originated in Seasar)
 * 
 */
public class LaContainerPropertyAccessor extends ObjectPropertyAccessor {

    @Override
    public Object getProperty(@SuppressWarnings("rawtypes") Map cx, Object target, Object name) throws OgnlException {
        final LaContainer container = (LaContainer) target;
        final String componentName = name.toString();
        return container.getComponent(componentName);
    }
}
