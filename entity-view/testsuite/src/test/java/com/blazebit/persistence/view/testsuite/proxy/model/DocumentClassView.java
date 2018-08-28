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

package com.blazebit.persistence.view.testsuite.proxy.model;

import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingParameter;

/**
 *
 * @author Christian Beikov
 * @since 1.0.0
 */
public abstract class DocumentClassView implements DocumentInterfaceView {
    
    private static final long serialVersionUID = 1L;

    private final long age;
    private final Integer contactPersonNumber;

    public DocumentClassView(
        @Mapping("age + 1") Long age,
        @MappingParameter("contactPersonNumber") Integer contactPersonNumber
    ) {
        this.age = age;
        this.contactPersonNumber = contactPersonNumber;
    }

    public long getAge() {
        return age;
    }

    public Integer getContactPersonNumber() {
        return contactPersonNumber;
    }
}
