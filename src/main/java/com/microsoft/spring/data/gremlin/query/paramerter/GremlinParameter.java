/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.spring.data.gremlin.query.paramerter;

import org.springframework.core.MethodParameter;
import org.springframework.data.repository.query.Parameter;

public class GremlinParameter extends Parameter {

    public GremlinParameter(MethodParameter parameter) {
        super(parameter);
    }
}
