/**
 * Copyright (C) 2010-16 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rvesse.airline.annotations.restrictions;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation that indicates that you only permit one option from some set of
 * options to be present, the set of options are identified by a user defined
 * tag.
 * <p>
 * By using the same tag across several annotated options you can state that you
 * require only one of those options to be present. If you require one/more from
 * some set of options you should instead use the less restrictive
 * {@link RequireSome}. If you optionally require at most one from some set of
 * options you can use {@link MutuallyExclusiveWith} instead.
 * </p>
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface RequireOnlyOne {

    /**
     * Provides a tag used to identify some set of options
     * 
     * @return Tag
     */
    String tag() default "";
}
