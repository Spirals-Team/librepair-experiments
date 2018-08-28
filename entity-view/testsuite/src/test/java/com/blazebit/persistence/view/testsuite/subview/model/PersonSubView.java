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

package com.blazebit.persistence.view.testsuite.subview.model;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.testsuite.entity.Person;

/**
 *
 * @author Christian Beikov
 * @since 1.0.0
 */
@EntityView(Person.class)
public interface PersonSubView extends SimplePersonSubView {

    // Although it might not be used, we add it to cover array expressions in subviews
    @Mapping("localized[1]")
    public String getFirstLocalized();

    @Mapping("VIEW_ROOT()")
    public SimpleDocumentView getRoot();

    @Mapping("EMBEDDING_VIEW()")
    public SimpleDocumentView getParent();
}
