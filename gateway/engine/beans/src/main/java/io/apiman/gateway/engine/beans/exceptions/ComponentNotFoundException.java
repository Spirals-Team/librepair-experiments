/*
 * Copyright 2013 JBoss Inc
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
 * Exception thrown when a policy tries to get a component from the context
 * but the component doesn't exist or is otherwise not available.
 *
 * @author eric.wittmann@redhat.com
 */
public class ComponentNotFoundException extends AbstractEngineException {

    private static final long serialVersionUID = 8430298328831765033L;

    /**
     * Constructor.
     * @param componentType the component type
     */
    public ComponentNotFoundException(String componentType) {
        super("Component not found: " + componentType); //$NON-NLS-1$
    }

    /**
     * Constructor.
     * @param componentType the component type
     * @param cause the exception cause the root cause
     */
    public ComponentNotFoundException(String componentType, Throwable cause) {
        super("Component not found: " + componentType, cause); //$NON-NLS-1$
    }
}
