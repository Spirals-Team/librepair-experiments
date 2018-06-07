/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.pog;

import java.util.UUID;

import net.atf4j.core.TestResultsReporting;

/**
 * A Class to represent a field of the class to be Generated.
 */
public class ClassField extends TestResultsReporting {

    /** Code Template for a field. */
    private static final String FIELD_CODE = "%s %s %s;";

    /** Code Template for a field with assignment. */
    private static final String ASSIGN_FIELD = "%s %s %s = %s;";

    /** access modifier for field. */
    private AccessModifier accessModifier = AccessModifier.DEFAULT;

    /** access modifier as String. */
    private String visibility = accessModifier.toString();

    /** type of field . */
    private FieldType fieldType = FieldType.OBJECT;

    /** type of field as String. */
    private String type = fieldType.toString();

    /** name of field. */
    private String name = null;

    /** The initial value. */
    private String initialValue = null;

    /**
     * Instantiates a new class field.
     */
    protected ClassField() {
        setAccessModifier(AccessModifier.DEFAULT);
        final String uniqueness = UUID.randomUUID().toString().substring(0, 8);
        setFieldName(String.format("object%s", uniqueness));
    }

    /**
     * Instantiates a new class field.
     *
     * @param fieldType the type
     * @param fieldName the name
     */
    protected ClassField(final FieldType fieldType, final String fieldName) {
        setAccessModifier(AccessModifier.DEFAULT);
        setFieldType(fieldType);
        setFieldName(fieldName);
    }

    /**
     * Instantiates a new class field.
     *
     * @param fieldType the field type
     * @param fieldName the field name
     */
    protected ClassField(final String fieldType, final String fieldName) {
        setAccessModifier(AccessModifier.DEFAULT);
        setFieldType(fieldType);
        setFieldName(fieldName);
    }

    /**
     * Factory method to create a new instance of ClassField.
     *
     * @return new instance of ClassField
     */
    public static ClassField create() {
        return new ClassField();
    }

    /**
     * Make boolean.
     *
     * @param fieldName the field name
     * @return the class field
     */
    public static ClassField makeBoolean(final String fieldName) {
        return new ClassField(FieldType.BOOLEAN, fieldName);
    }

    /**
     * Make byte.
     *
     * @param fieldName the field name
     * @return the class field
     */
    public static ClassField makeByte(final String fieldName) {
        return new ClassField(FieldType.BYTE, fieldName);
    }

    /**
     * Make date.
     *
     * @param fieldName the field name
     * @return the class field
     */
    public static ClassField makeDate(final String fieldName) {
        return new ClassField(FieldType.DATE, fieldName);
    }

    /**
     * Make number.
     *
     * @param fieldName the field name
     * @return the class field
     */
    public static ClassField makeNumber(final String fieldName) {
        return new ClassField(FieldType.NUMBER, fieldName);
    }

    /**
     * Make object.
     *
     * @param fieldName the field name
     * @return the class field
     */
    public static ClassField makeObject(final String fieldName) {
        return new ClassField(FieldType.OBJECT, fieldName);
    }

    /**
     * Make string.
     *
     * @param fieldName the field name
     * @return the class field
     */
    public static ClassField makeString(final String fieldName) {
        return new ClassField(FieldType.STRING, fieldName);
    }

    /**
     * Make other.
     *
     * @param type the type
     * @param fieldName the field name
     * @return the class field
     */
    public static ClassField makeOther(final String type, final String fieldName) {
        return new ClassField(type, fieldName);
    }

    /**
     * Sets the access modifier.
     *
     * @param accessModifier the access modifier
     * @return the class field
     */
    protected ClassField setAccessModifier(final AccessModifier accessModifier) {
        this.accessModifier = accessModifier;
        visibility = this.accessModifier.toString();
        return this;
    }

    /**
     * Sets the access modifier.
     *
     * @param accessModifier the access modifier
     * @return the class field
     */
    protected ClassField setAccessModifier(final String accessModifier) {
        visibility = accessModifier;
        this.accessModifier = AccessModifier.fromString(accessModifier);
        return this;
    }

    /**
     * Sets the field type.
     *
     * @param fieldType the field type
     * @return the class field
     */
    protected ClassField setFieldType(final FieldType fieldType) {
        this.fieldType = fieldType;
        type = fieldType.toString();
        return this;
    }

    /**
     * Sets the field type.
     *
     * @param fieldTypeString the field type string
     * @return the class field
     */
    protected ClassField setFieldType(final String fieldTypeString) {
        fieldType = FieldType.fromString(fieldTypeString);
        if (fieldType == FieldType.Other) {
            type = fieldTypeString;
        } else {
            type = fieldType.toString();
        }
        return this;
    }

    /**
     * Sets the field name.
     *
     * @param fieldName the field name
     * @return the class field
     */
    protected ClassField setFieldName(final String fieldName) {
        name = fieldCase(fieldName);
        return this;
    }

    /**
     * Sets the initial value.
     *
     * @param initialValue the initial value
     * @return the class field
     */
    protected ClassField setInitialValue(final String initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    /**
     * Gets the access modifier.
     *
     * @return the access modifier
     */
    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    /**
     * Gets the access modifier value.
     *
     * @return the access modifier value
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * Gets the field type string.
     *
     * @return the field type string
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the field name.
     *
     * @return the field name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the initial value.
     *
     * @return the initial value
     */
    public String getInitialValue() {
        return initialValue;
    }

    /**
     * Field case.
     *
     * @param string the string
     * @return the string
     */
    protected String fieldCase(final String string) {
        final char[] charArray = string.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }

    /**
     * To code.
     *
     * @return the string
     */
    public String getField() {
        final String code;
        if (initialValue == null) {
            code = String.format(FIELD_CODE,
                    accessModifier,
                    type,
                    name);
        } else {
            code = String.format(ASSIGN_FIELD,
                    accessModifier,
                    fieldType,
                    type,
                    initialValue);
        }
        return code;
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    protected String debugString() {
        final String debugString = String.format(
                "%s [accessModifier=%s, visibility=%s, fieldType=%s, type=%s, name=%s, initialValue=%s]",
                this.getClass().getSimpleName(),
                accessModifier.name(),
                visibility,
                fieldType.name(),
                type,
                name,
                initialValue);
        return debugString;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (log.isDebugEnabled()) {
            return debugString();
        } else {
            return getField();
        }
    }
}
