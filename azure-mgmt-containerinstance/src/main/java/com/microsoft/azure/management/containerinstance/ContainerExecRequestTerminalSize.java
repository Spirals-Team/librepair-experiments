/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerinstance;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The size of the terminal.
 */
public class ContainerExecRequestTerminalSize {
    /**
     * The row size of the terminal.
     */
    @JsonProperty(value = "row")
    private Integer row;

    /**
     * The column size of the terminal.
     */
    @JsonProperty(value = "column")
    private Integer column;

    /**
     * Get the row value.
     *
     * @return the row value
     */
    public Integer row() {
        return this.row;
    }

    /**
     * Set the row value.
     *
     * @param row the row value to set
     * @return the ContainerExecRequestTerminalSize object itself.
     */
    public ContainerExecRequestTerminalSize withRow(Integer row) {
        this.row = row;
        return this;
    }

    /**
     * Get the column value.
     *
     * @return the column value
     */
    public Integer column() {
        return this.column;
    }

    /**
     * Set the column value.
     *
     * @param column the column value to set
     * @return the ContainerExecRequestTerminalSize object itself.
     */
    public ContainerExecRequestTerminalSize withColumn(Integer column) {
        this.column = column;
        return this;
    }

}
