/**
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
package org.apache.metamodel.update;

import junit.framework.TestCase;

import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Schema;

/**
 * Abstract test class (will not be executed) to check the builder API's
 * syntactical applicability.
 */
public abstract class SyntaxExamplesTest extends TestCase {

    private Column col;
    private Schema schema;

    public void testUpdateClass() throws Exception {
        Update u = new Update(schema, "table").value(col, "foo").where("baz").eq("bar");
        assertNotNull(u);
    }
}