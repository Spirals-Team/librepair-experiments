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

package com.blazebit.persistence.examples.showcase.fragments.basic.view;

import com.blazebit.persistence.examples.showcase.base.model.Cat;
import com.blazebit.persistence.view.EntityView;

/**
 * @author Moritz Becker
 * @since 1.2.0
 */
@EntityView(Cat.class)
public abstract class BasicCatView implements IdHolderView<Integer> {

    public abstract String getName();

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
