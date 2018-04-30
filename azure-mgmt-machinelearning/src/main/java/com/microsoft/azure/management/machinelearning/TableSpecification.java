/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.machinelearning;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The swagger 2.0 schema describing a single service input or output. See
 * Swagger specification: http://swagger.io/specification/.
 */
public class TableSpecification {
    /**
     * Swagger schema title.
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * Swagger schema description.
     */
    @JsonProperty(value = "description")
    private String description;

    /**
     * The type of the entity described in swagger.
     */
    @JsonProperty(value = "type", required = true)
    private String type;

    /**
     * The format, if 'type' is not 'object'.
     */
    @JsonProperty(value = "format")
    private String format;

    /**
     * The set of columns within the data table.
     */
    @JsonProperty(value = "properties")
    private Map<String, ColumnSpecification> properties;

    /**
     * Get the title value.
     *
     * @return the title value
     */
    public String title() {
        return this.title;
    }

    /**
     * Set the title value.
     *
     * @param title the title value to set
     * @return the TableSpecification object itself.
     */
    public TableSpecification withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get the description value.
     *
     * @return the description value
     */
    public String description() {
        return this.description;
    }

    /**
     * Set the description value.
     *
     * @param description the description value to set
     * @return the TableSpecification object itself.
     */
    public TableSpecification withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the TableSpecification object itself.
     */
    public TableSpecification withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the format value.
     *
     * @return the format value
     */
    public String format() {
        return this.format;
    }

    /**
     * Set the format value.
     *
     * @param format the format value to set
     * @return the TableSpecification object itself.
     */
    public TableSpecification withFormat(String format) {
        this.format = format;
        return this;
    }

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public Map<String, ColumnSpecification> properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the TableSpecification object itself.
     */
    public TableSpecification withProperties(Map<String, ColumnSpecification> properties) {
        this.properties = properties;
        return this;
    }

}
