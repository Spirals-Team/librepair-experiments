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
package org.docksidestage.remote.swagger.petstore.pet.index;

import org.lastaflute.core.util.Lato;
import org.lastaflute.web.validation.Required;

/**
 * The bean class as param for remote API of POST /pet.
 * @author FreeGen
 */
public class RemotePetPostParam {

    /** The property of id. (NullAllowed) */
    public Long id;

    /** The property of category. (NullAllowed) */
    @javax.validation.Valid
    public CategoryPart category;

    /**
     * The part class of CategoryPart.
     * @author FreeGen
     */
    public static class CategoryPart {

        /** The property of id. (NullAllowed) */
        public Long id;

        /** The property of name. (NullAllowed) */
        public String name;
    }

    /** The property of name. */
    @Required
    public String name;

    /** The property of photoUrls. */
    @Required
    public org.eclipse.collections.api.list.ImmutableList<String> photoUrls;

    /** The property of tags. (NullAllowed) */
    @javax.validation.Valid
    public org.eclipse.collections.api.list.ImmutableList<TagPart> tags;

    /**
     * The part class of TagPart.
     * @author FreeGen
     */
    public static class TagPart {

        /** The property of id. (NullAllowed) */
        public Long id;

        /** The property of name. (NullAllowed) */
        public String name;
    }

    /** The property of status. (enumValue=[available, pending, sold]) (pet status in the store) (NullAllowed) */
    public String status;

    @Override
    public String toString() {
        return Lato.string(this);
    }
}
