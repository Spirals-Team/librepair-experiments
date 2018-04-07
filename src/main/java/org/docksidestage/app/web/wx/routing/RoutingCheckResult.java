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
package org.docksidestage.app.web.wx.routing;

import org.dbflute.optional.OptionalThing;
import org.lastaflute.core.util.Lato;

/**
 * @author jflute
 */
public class RoutingCheckResult {

    public final String method;
    public final Object first;
    public final Object second;

    public RoutingCheckResult(String method, Object first, Object second) {
        this.method = method;
        this.first = first instanceof OptionalThing ? first.toString() : first;
        this.second = second instanceof OptionalThing ? second.toString() : second;
    }

    @Override
    public String toString() {
        return Lato.string(this);
    }
}
