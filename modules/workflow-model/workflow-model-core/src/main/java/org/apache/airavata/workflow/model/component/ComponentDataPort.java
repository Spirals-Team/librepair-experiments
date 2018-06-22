/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.airavata.workflow.model.component;

import javax.xml.namespace.QName;

import org.apache.airavata.model.application.io.DataType;
import org.apache.airavata.workflow.model.graph.DataPort;

public abstract class ComponentDataPort extends ComponentPort {

    protected DataType type;
    /**
     * Constructs a ComponentDataPort.
     * 
     */
    public ComponentDataPort() {
        super();
    }

    /**
     * Constructs a ComponentDataPort.
     * 
     * @param name
     */
    public ComponentDataPort(String name) {
        super(name);
    }

    public void setType(DataType type) {
        this.type = type;
    }

    /**
     * Returns the type.
     * 
     * @return The type
     */
    public DataType getType() {
        return this.type;
    }

    /**
     * @see org.apache.airavata.workflow.model.component.ComponentPort#createPort()
     */
    @Override
    public abstract DataPort createPort();

}