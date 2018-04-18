/**
 * Copyright (C) 2014-2016 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.common.utils;

import com.linkedin.pinot.common.data.FieldSpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import javax.annotation.Nonnull;


/**
 * The <code>DataSchema</code> class describes the schema of {@link DataTable}.
 */
public class DataSchema {
  private static final Charset UTF_8 = Charset.forName("UTF-8");

  private String[] _columnNames;
  private ColumnType[] _columnTypes;

  public DataSchema(@Nonnull String[] columnNames, @Nonnull ColumnType[] columnTypes) {
    _columnNames = columnNames;
    _columnTypes = columnTypes;
  }

  public int size() {
    return _columnNames.length;
  }

  @Nonnull
  public String getColumnName(int index) {
    return _columnNames[index];
  }

  @Nonnull
  public ColumnType getColumnType(int index) {
    return _columnTypes[index];
  }

  /**
   * Indicates whether the given {@link DataSchema} is type compatible with this one.
   * <ul>
   *   <li>All numbers are type compatible.</li>
   *   <li>Number is not type compatible with String.</li>
   *   <li>Single-value is not type compatible with multi-value.</li>
   * </ul>
   *
   * @param anotherDataSchema data schema to compare.
   * @return whether the two data schemas are type compatible.
   */
  public boolean isTypeCompatibleWith(@Nonnull DataSchema anotherDataSchema) {
    if (!Arrays.equals(_columnNames, anotherDataSchema._columnNames)) {
      return false;
    }
    int numColumns = _columnNames.length;
    for (int i = 0; i < numColumns; i++) {
      if (!_columnTypes[i].isCompatible(anotherDataSchema._columnTypes[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Upgrade the current data schema to cover the column types in the given data schema.
   * <p>Type <code>LONG</code> can cover <code>INT</code> and <code>LONG</code>.
   * <p>Type <code>DOUBLE</code> can cover all numbers, but with potential precision loss when use it to cover
   * <code>LONG</code>.
   * <p>The given data schema should be type compatible with this one.
   *
   * @param anotherDataSchema data schema to be covered.
   */
  public void upgradeToCover(@Nonnull DataSchema anotherDataSchema) {
    int numColumns = _columnTypes.length;
    for (int i = 0; i < numColumns; i++) {
      ColumnType thisColumnType = _columnTypes[i];
      ColumnType thatColumnType = anotherDataSchema._columnTypes[i];
      if (thisColumnType != thatColumnType) {
        if (thisColumnType.isArray()) {
          if (thisColumnType.isWholeNumberArray() && thatColumnType.isWholeNumberArray()) {
            _columnTypes[i] = ColumnType.LONG_ARRAY;
          } else {
            _columnTypes[i] = ColumnType.DOUBLE_ARRAY;
          }
        } else {
          if (thisColumnType.isWholeNumber() && thatColumnType.isWholeNumber()) {
            _columnTypes[i] = ColumnType.LONG;
          } else {
            _columnTypes[i] = ColumnType.DOUBLE;
          }
        }
      }
    }
  }

  @Nonnull
  public byte[] toBytes() throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    int length = _columnNames.length;

    // Write the number of columns.
    dataOutputStream.writeInt(length);

    // Write the column names.
    for (String columnName : _columnNames) {
      byte[] bytes = columnName.getBytes(UTF_8);
      dataOutputStream.writeInt(bytes.length);
      dataOutputStream.write(bytes);
    }

    // Write the column types.
    for (ColumnType columnType : _columnTypes) {
      // We don't want to use ordinal of the enum since adding a new data type will break things if server and broker
      // use different versions of DataType class.
      byte[] bytes = columnType.name().getBytes(UTF_8);
      dataOutputStream.writeInt(bytes.length);
      dataOutputStream.write(bytes);
    }
    return byteArrayOutputStream.toByteArray();
  }

  @Nonnull
  public static DataSchema fromBytes(@Nonnull byte[] buffer) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
    DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

    // Read the number of columns.
    int numColumns = dataInputStream.readInt();
    String[] columnNames = new String[numColumns];
    ColumnType[] columnTypes = new ColumnType[numColumns];

    // Read the column names.
    int readLength;
    for (int i = 0; i < numColumns; i++) {
      int length = dataInputStream.readInt();
      byte[] bytes = new byte[length];
      readLength = dataInputStream.read(bytes);
      assert readLength == length;
      columnNames[i] = new String(bytes, UTF_8);
    }

    // Read the column types.
    for (int i = 0; i < numColumns; i++) {
      int length = dataInputStream.readInt();
      byte[] bytes = new byte[length];
      readLength = dataInputStream.read(bytes);
      assert readLength == length;
      columnTypes[i] = ColumnType.valueOf(new String(bytes, UTF_8));
    }

    return new DataSchema(columnNames, columnTypes);
  }

  @SuppressWarnings("CloneDoesntCallSuperClone")
  @Override
  public DataSchema clone() {
    return new DataSchema(_columnNames.clone(), _columnTypes.clone());
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('[');
    int numColumns = _columnNames.length;
    for (int i = 0; i < numColumns; i++) {
      stringBuilder.append(_columnNames[i]).append('(').append(_columnTypes[i]).append(')').append(',');
    }
    stringBuilder.setCharAt(stringBuilder.length() - 1, ']');
    return stringBuilder.toString();
  }

  @Override
  public boolean equals(Object anObject) {
    if (this == anObject) {
      return true;
    }
    if (anObject instanceof DataSchema) {
      DataSchema anotherDataSchema = (DataSchema) anObject;
      return Arrays.equals(_columnNames, anotherDataSchema._columnNames) && Arrays.equals(_columnTypes,
          anotherDataSchema._columnTypes);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = EqualityUtils.hashCodeOf(_columnNames);
    hashCode = EqualityUtils.hashCodeOf(hashCode, _columnTypes);
    return hashCode;
  }

  public enum ColumnType {
    // NOTE: don't change the order
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    STRING,
    OBJECT,
    INT_ARRAY,
    LONG_ARRAY,
    FLOAT_ARRAY,
    DOUBLE_ARRAY,
    STRING_ARRAY;

    public boolean isArray() {
      return this.ordinal() >= INT_ARRAY.ordinal();
    }

    public boolean isNumber() {
      return this.ordinal() <= DOUBLE.ordinal();
    }

    public boolean isNumberArray() {
      return isArray() && this != STRING_ARRAY;
    }

    public boolean isWholeNumber() {
      return this == INT || this == LONG;
    }

    public boolean isWholeNumberArray() {
      return this == INT_ARRAY || this == LONG_ARRAY;
    }

    public boolean isCompatible(ColumnType anotherColumnType) {
      // All numbers are compatible with each other
      return this == anotherColumnType || (this.isNumber() && anotherColumnType.isNumber()) || (this.isNumberArray()
          && anotherColumnType.isNumberArray());
    }

    public static ColumnType fromDataType(FieldSpec.DataType dataType, boolean isSingleValue) {
      switch (dataType) {
        case INT:
          return isSingleValue ? INT : INT_ARRAY;
        case LONG:
          return isSingleValue ? LONG : LONG_ARRAY;
        case FLOAT:
          return isSingleValue ? FLOAT : FLOAT_ARRAY;
        case DOUBLE:
          return isSingleValue ? DOUBLE : DOUBLE_ARRAY;
        case STRING:
          return isSingleValue ? STRING : STRING_ARRAY;
        default:
          throw new UnsupportedOperationException("Unsupported data type: " + dataType);
      }
    }
  }
}
