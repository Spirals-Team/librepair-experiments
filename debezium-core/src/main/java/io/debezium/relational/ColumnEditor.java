/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.relational;

import java.sql.Types;

import io.debezium.annotation.NotThreadSafe;

/**
 * An editor for {@link Column} instances.
 * 
 * @author Randall Hauch
 */
@NotThreadSafe
public interface ColumnEditor extends Comparable<Column> {

    /**
     * Get the name of the column.
     * 
     * @return the column name; may be null if not set
     */
    String name();

    /**
     * Get the position of the column in the table.
     * 
     * @return the 1-based position
     */
    int position();

    /**
     * Get the {@link Types JDBC type} for this column
     * 
     * @return the type constant
     */
    int jdbcType();

    /**
     * Get the internal database-specific type identifier for this column.
     *
     *
     * @return a type constant for the specific database
     */
    int nativeType();

    /**
     * Get the database-specific name of the column's data type.
     * 
     * @return the name of the type; may be null if not set
     */
    String typeName();

    /**
     * Get the database-specific complete expression defining the column's data type, including dimensions, length, precision,
     * character sets, constraints, etc.
     * 
     * @return the complete type expression
     */
    String typeExpression();

    /**
     * Get the database-specific name of the character set used by this column.
     * 
     * @return the database-specific character set name, or null if the column's data type doesn't use character sets or no
     * character set is specified
     */
    String charsetName();

    /**
     * Get the database-specific name of the character set defined by this column's table, which is used if a character set is
     * not explicitly set on this column.
     * 
     * @return the database-specific character set name defined for this column's table, or null if not defined
     */
    String charsetNameOfTable();

    /**
     * Get the maximum length of this column's values. For numeric columns, this represents the precision.
     * 
     * @return the length of the column
     */
    int length();

    /**
     * Get the scale of the column.
     * 
     * @return the scale, or -1 if the scale does not apply to this type
     */
    int scale();

    /**
     * Determine whether this column is optional.
     * 
     * @return {@code true} if it is optional, or {@code false} otherwise
     */
    boolean isOptional();

    /**
     * Determine whether this column's values are automatically incremented by the database.
     * 
     * @return {@code true} if the values are auto-incremented, or {@code false} otherwise
     */
    boolean isAutoIncremented();

    /**
     * Determine whether this column's values are generated by the database.
     * 
     * @return {@code true} if the values are generated, or {@code false} otherwise
     */
    boolean isGenerated();

    /**
     * Set the name of the column.
     * 
     * @param name the column name
     * @return this editor so callers can chain methods together
     */
    ColumnEditor name(String name);

    /**
     * Set the database-specific name of the column's data type.
     * 
     * @param typeName the column's type name
     * @return this editor so callers can chain methods together
     */
    ColumnEditor type(String typeName);

    /**
     * Set the database-specific name of the column's data type. The expression includes the column's
     * {@link #typeName() type name} and also any dimensions, lengths, precisions, character sets, etc.
     * 
     * 
     * @param typeName the column's type name
     * @param typeExpression the column's complete type expression
     * @return this editor so callers can chain methods together
     */
    ColumnEditor type(String typeName, String typeExpression);

    /**
     * Set the {@link Types JDBC type} of this column.
     * 
     * @param jdbcType {@link Types JDBC type} for this column
     * @return this editor so callers can chain methods together
     */
    ColumnEditor jdbcType(int jdbcType);

    /**
     * Set the native type for this column . This is database specific.
     *
     * @return a type constant for the specific database
     */
    ColumnEditor nativeType(int nativeType);

    /**
     * Set the database-specific name of the character set used by this column.
     * 
     * @param charsetName the database-specific character set name; may be null
     * @return this editor so callers can chain methods together
     */
    ColumnEditor charsetName(String charsetName);

    /**
     * Set the database-specific name of the character set defined by this column's table.
     * 
     * @param charsetName the database-specific character set name; may be null
     * @return this editor so callers can chain methods together
     */
    ColumnEditor charsetNameOfTable(String charsetName);

    /**
     * Set the maximum length of this column's values. For numeric columns, this represents the precision.
     * 
     * @param length the column's length
     * @return this editor so callers can chain methods together
     */
    ColumnEditor length(int length);

    /**
     * Set the scale of the column.
     * 
     * @param scale the scale, or -1 if the scale does not apply to this type
     * @return this editor so callers can chain methods together
     */
    ColumnEditor scale(int scale);

    /**
     * Set whether the column's values are optional (e.g., can contain nulls).
     * 
     * @param optional {@code true} if the column's values are optional, or {@code false} otherwise
     * @return this editor so callers can chain methods together
     */
    ColumnEditor optional(boolean optional);

    /**
     * Set whether the column's values are automatically incremented.
     * 
     * @param autoIncremented {@code true} if the column's values are automatically incremented by the database, or {@code false}
     *            otherwise
     * @return this editor so callers can chain methods together
     */
    ColumnEditor autoIncremented(boolean autoIncremented);

    /**
     * Set whether the column's values are generated by the database.
     * 
     * @param generated {@code true} if the column's values are generated by the database, or {@code false} otherwise
     * @return this editor so callers can chain methods together
     */
    ColumnEditor generated(boolean generated);

    /**
     * Set the position of the column within the table definition.
     * 
     * @param position the new column position
     * @return this editor so callers can chain methods together
     */
    ColumnEditor position(int position);

    /**
     * Obtain an immutable column definition representing the current state of this editor. Typically, an editor is created and
     * used to build a column, and then discarded. However, this editor with its current state can be reused after this method,
     * since the resulting column definition no longer refers to any of the data used in this editor.
     * 
     * @return the immutable column definition; never null
     */
    Column create();
}
