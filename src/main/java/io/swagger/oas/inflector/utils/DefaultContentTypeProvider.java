/*
 *  Copyright 2017 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.swagger.oas.inflector.utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;

public class DefaultContentTypeProvider implements ContextResolver<ContentTypeSelector> {
    private final ContentTypeSelector selector;

    public DefaultContentTypeProvider(MediaType type) {
        this(new DefaultContentTypeSelector(type));
    }

    public DefaultContentTypeProvider(ContentTypeSelector selector) {
        this.selector = selector;
    }

    @Override
    public ContentTypeSelector getContext(Class<?> type) {
        return selector;
    }
}
