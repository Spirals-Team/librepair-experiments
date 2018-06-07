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

package io.apiman.gateway.engine.components;

import io.apiman.gateway.engine.IComponent;
import io.apiman.gateway.engine.beans.PolicyFailure;
import io.apiman.gateway.engine.beans.PolicyFailureType;

/**
 * Component that can be used to create policy failures.
 *
 * @author eric.wittmann@redhat.com
 */
public interface IPolicyFailureFactoryComponent extends IComponent {
    
    /**
     * Creates a policy failure for the given information.
     * @param type
     * @param failureCode
     * @param message
     * @return the policy failure
     */
    PolicyFailure createFailure(PolicyFailureType type, int failureCode, String message);

}
