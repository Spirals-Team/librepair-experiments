/*
 * Copyright 2016 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.github.dannil.scbjavaclient.validator.json;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * <p>Class which contains operations for validating and confirming JSON.</p>
 *
 * @since 0.1.0
 */
public final class JsonValidator {

    /**
     * <p>Private constructor to prevent instantiation.</p>
     */
    private JsonValidator() {

    }

    /**
     * <p>Checks if the JSON is a query.</p>
     *
     * @param node
     *            the node to check
     * @return true if the node is a query
     */
    public static boolean isQuery(JsonNode node) {
        // Check if the node is actually a query
        return node.has("query");
    }
}
