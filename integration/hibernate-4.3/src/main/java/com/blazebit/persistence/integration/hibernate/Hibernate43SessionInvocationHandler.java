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

package com.blazebit.persistence.integration.hibernate;

import org.hibernate.engine.spi.SessionImplementor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Christian Beikov
 * @since 1.2.0
 */
public class Hibernate43SessionInvocationHandler implements InvocationHandler {
    
    private final SessionImplementor delegate;
    private final Object transactionCoordinatorProxy;

    public Hibernate43SessionInvocationHandler(SessionImplementor delegate, Object transactionCoordinatorProxy) {
        this.delegate = delegate;
        this.transactionCoordinatorProxy = transactionCoordinatorProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getTransactionCoordinator".equals(method.getName())) {
            return transactionCoordinatorProxy;
        }
        
        return method.invoke(delegate, args);
    }

}
