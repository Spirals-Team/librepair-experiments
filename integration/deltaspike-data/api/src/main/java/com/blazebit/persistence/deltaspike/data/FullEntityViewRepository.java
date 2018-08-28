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

package com.blazebit.persistence.deltaspike.data;

import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import java.io.Serializable;

/**
 * Interface that supports {@link EntityViewRepository} and {@link CriteriaSupport} capabilities.
 *
 * @param <E> Entity type.
 * @param <V> Entity view type.
 * @param <PK> Id type.
 *
 * @author Moritz Becker
 * @since 1.2.0
 */
public interface FullEntityViewRepository<E, V, PK extends Serializable> extends EntityViewRepository<E, V, PK>, CriteriaSupport<E> {
}