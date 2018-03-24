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
package org.apache.nifi.atlas.resolver;

import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.components.ValidationContext;
import org.apache.nifi.components.ValidationResult;
import org.apache.nifi.context.PropertyContext;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public interface ClusterResolver {

    default Collection<ValidationResult> validate(final ValidationContext validationContext) {
        return Collections.emptySet();
    }

    PropertyDescriptor getSupportedDynamicPropertyDescriptor(final String propertyDescriptorName);

    /**
     * Implementation should clear previous configurations when this method is called again.
     * @param context passed from ReportingTask
     */
    void configure(PropertyContext context);

    /**
     * Resolve a cluster name from a list of host names or an ip addresses.
     * @param hostNames hostname or ip address
     * @return resolved cluster name or null
     */
    default String fromHostNames(String ... hostNames) {
        return null;
    }

    /**
     * Resolve a cluster name from hints, such as Zookeeper Quorum, client port and znode path
     * @param hints Contains variables to resolve a cluster name
     * @return resolved cluster name or null
     */
    default String fromHints(Map<String, String> hints) {
        return null;
    }

}
