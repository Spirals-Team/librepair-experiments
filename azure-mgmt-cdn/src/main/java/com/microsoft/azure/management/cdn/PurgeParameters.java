/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters required for content purge.
 */
public class PurgeParameters {
    /**
     * The path to the content to be purged. Can describe a file path or a wild
     * card directory.
     */
    @JsonProperty(value = "contentPaths", required = true)
    private List<String> contentPaths;

    /**
     * Get the contentPaths value.
     *
     * @return the contentPaths value
     */
    public List<String> contentPaths() {
        return this.contentPaths;
    }

    /**
     * Set the contentPaths value.
     *
     * @param contentPaths the contentPaths value to set
     * @return the PurgeParameters object itself.
     */
    public PurgeParameters withContentPaths(List<String> contentPaths) {
        this.contentPaths = contentPaths;
        return this;
    }

}
