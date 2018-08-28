/*
 * Copyright 2014 - 2018 Blazebit.
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

package com.blazebit.persistence.view.impl.type;

import com.blazebit.persistence.view.spi.type.BasicUserType;

import java.util.Collection;

/**
 *
 * @author Christian Beikov
 * @since 1.2.0
 */
public abstract class AbstractCollectionUserTypeWrapper<C extends Collection<V>, V> extends AbstractPluralUserTypeWrapper<C, V> {

    public AbstractCollectionUserTypeWrapper(BasicUserType<V> elementUserType) {
        super(elementUserType);
    }

    protected abstract C createCollection(int size);

    @Override
    public C deepClone(C object) {
        C clone = createCollection(object.size());

        for (V element : object) {
            clone.add(elementUserType.deepClone(element));
        }

        return clone;
    }
}
