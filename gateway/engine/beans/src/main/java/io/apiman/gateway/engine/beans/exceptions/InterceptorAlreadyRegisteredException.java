/*
 * Copyright 2014 JBoss Inc
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
package io.apiman.gateway.engine.beans.exceptions;

/**
 * Thrown when an API Connection Interceptor has already been registered in
 * the IPolicyContext
 *
 * @author rromero
 *
 */
public class InterceptorAlreadyRegisteredException extends AbstractEngineException {

    private static final long serialVersionUID = -4134086341668627517L;

    /**
     * Constructor.
     * @param interceptorClass the interceptor class
     */
    @SuppressWarnings("nls")
    public InterceptorAlreadyRegisteredException(Class<?> interceptorClass) {
        super("An Interceptor of type " + interceptorClass + " was already registered in the context");
    }

}
