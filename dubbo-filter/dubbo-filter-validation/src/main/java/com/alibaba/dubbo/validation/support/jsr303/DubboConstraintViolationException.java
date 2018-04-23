/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.validation.support.jsr303;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author ningyu
 * @date 2018-04-23 15:47
 */
public class DubboConstraintViolationException extends ConstraintViolationException {
    private static final long serialVersionUID = -8570552769314565659L;
    private Set<? extends ConstraintViolation<?>> constraintViolations;

    public DubboConstraintViolationException() {
        super(null);
    }

    public DubboConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
        this.constraintViolations = constraintViolations;
    }

    public Set<ConstraintViolation<?>> getConstraintViolations() {
        return (Set<ConstraintViolation<?>>) constraintViolations;
    }

    public void setConstraintViolations(Set<? extends ConstraintViolation<?>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }
}
