/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.aesh.readline.completion;

/**
 * To enable auto completion, commands need to implement this interface.
 *
 * @author Ståle W. Pedersen <stale.pedersen@jboss.org>
 */
public interface Completion<C extends CompleteOperation> {

    /**
     * Populate the CompleteOperation object with possible
     * completions + offset if needed
     *
     * @param completeOperation operation
     */
    void complete(C completeOperation);
}
