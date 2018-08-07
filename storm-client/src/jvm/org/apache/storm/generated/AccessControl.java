/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.storm.generated;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)")
public class AccessControl implements org.apache.storm.thrift.TBase<AccessControl, AccessControl._Fields>, java.io.Serializable, Cloneable, Comparable<AccessControl> {
  private static final org.apache.storm.thrift.protocol.TStruct STRUCT_DESC = new org.apache.storm.thrift.protocol.TStruct("AccessControl");

  private static final org.apache.storm.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("type", org.apache.storm.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.storm.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("name", org.apache.storm.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.storm.thrift.protocol.TField ACCESS_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("access", org.apache.storm.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.storm.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new AccessControlStandardSchemeFactory();
  private static final org.apache.storm.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new AccessControlTupleSchemeFactory();

  private AccessControlType type; // required
  private java.lang.String name; // optional
  private int access; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.storm.thrift.TFieldIdEnum {
    /**
     * 
     * @see AccessControlType
     */
    TYPE((short)1, "type"),
    NAME((short)2, "name"),
    ACCESS((short)3, "access");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TYPE
          return TYPE;
        case 2: // NAME
          return NAME;
        case 3: // ACCESS
          return ACCESS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ACCESS_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.NAME};
  public static final java.util.Map<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TYPE, new org.apache.storm.thrift.meta_data.FieldMetaData("type", org.apache.storm.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.storm.thrift.meta_data.EnumMetaData(org.apache.storm.thrift.protocol.TType.ENUM, AccessControlType.class)));
    tmpMap.put(_Fields.NAME, new org.apache.storm.thrift.meta_data.FieldMetaData("name", org.apache.storm.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.storm.thrift.meta_data.FieldValueMetaData(org.apache.storm.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACCESS, new org.apache.storm.thrift.meta_data.FieldMetaData("access", org.apache.storm.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.storm.thrift.meta_data.FieldValueMetaData(org.apache.storm.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.storm.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AccessControl.class, metaDataMap);
  }

  public AccessControl() {
  }

  public AccessControl(
    AccessControlType type,
    int access)
  {
    this();
    this.type = type;
    this.access = access;
    set_access_isSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AccessControl(AccessControl other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.is_set_type()) {
      this.type = other.type;
    }
    if (other.is_set_name()) {
      this.name = other.name;
    }
    this.access = other.access;
  }

  public AccessControl deepCopy() {
    return new AccessControl(this);
  }

  @Override
  public void clear() {
    this.type = null;
    this.name = null;
    set_access_isSet(false);
    this.access = 0;
  }

  /**
   * 
   * @see AccessControlType
   */
  public AccessControlType get_type() {
    return this.type;
  }

  /**
   * 
   * @see AccessControlType
   */
  public void set_type(AccessControlType type) {
    this.type = type;
  }

  public void unset_type() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean is_set_type() {
    return this.type != null;
  }

  public void set_type_isSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public java.lang.String get_name() {
    return this.name;
  }

  public void set_name(java.lang.String name) {
    this.name = name;
  }

  public void unset_name() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean is_set_name() {
    return this.name != null;
  }

  public void set_name_isSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public int get_access() {
    return this.access;
  }

  public void set_access(int access) {
    this.access = access;
    set_access_isSet(true);
  }

  public void unset_access() {
    __isset_bitfield = org.apache.storm.thrift.EncodingUtils.clearBit(__isset_bitfield, __ACCESS_ISSET_ID);
  }

  /** Returns true if field access is set (has been assigned a value) and false otherwise */
  public boolean is_set_access() {
    return org.apache.storm.thrift.EncodingUtils.testBit(__isset_bitfield, __ACCESS_ISSET_ID);
  }

  public void set_access_isSet(boolean value) {
    __isset_bitfield = org.apache.storm.thrift.EncodingUtils.setBit(__isset_bitfield, __ACCESS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TYPE:
      if (value == null) {
        unset_type();
      } else {
        set_type((AccessControlType)value);
      }
      break;

    case NAME:
      if (value == null) {
        unset_name();
      } else {
        set_name((java.lang.String)value);
      }
      break;

    case ACCESS:
      if (value == null) {
        unset_access();
      } else {
        set_access((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TYPE:
      return get_type();

    case NAME:
      return get_name();

    case ACCESS:
      return get_access();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TYPE:
      return is_set_type();
    case NAME:
      return is_set_name();
    case ACCESS:
      return is_set_access();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof AccessControl)
      return this.equals((AccessControl)that);
    return false;
  }

  public boolean equals(AccessControl that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_type = true && this.is_set_type();
    boolean that_present_type = true && that.is_set_type();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_name = true && this.is_set_name();
    boolean that_present_name = true && that.is_set_name();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_access = true;
    boolean that_present_access = true;
    if (this_present_access || that_present_access) {
      if (!(this_present_access && that_present_access))
        return false;
      if (this.access != that.access)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((is_set_type()) ? 131071 : 524287);
    if (is_set_type())
      hashCode = hashCode * 8191 + type.getValue();

    hashCode = hashCode * 8191 + ((is_set_name()) ? 131071 : 524287);
    if (is_set_name())
      hashCode = hashCode * 8191 + name.hashCode();

    hashCode = hashCode * 8191 + access;

    return hashCode;
  }

  @Override
  public int compareTo(AccessControl other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(is_set_type()).compareTo(other.is_set_type());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_type()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(is_set_name()).compareTo(other.is_set_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_name()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(is_set_access()).compareTo(other.is_set_access());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_access()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.access, other.access);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.storm.thrift.protocol.TProtocol iprot) throws org.apache.storm.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.storm.thrift.protocol.TProtocol oprot) throws org.apache.storm.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("AccessControl(");
    boolean first = true;

    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (is_set_name()) {
      if (!first) sb.append(", ");
      sb.append("name:");
      if (this.name == null) {
        sb.append("null");
      } else {
        sb.append(this.name);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("access:");
    sb.append(this.access);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.storm.thrift.TException {
    // check for required fields
    if (!is_set_type()) {
      throw new org.apache.storm.thrift.protocol.TProtocolException("Required field 'type' is unset! Struct:" + toString());
    }

    if (!is_set_access()) {
      throw new org.apache.storm.thrift.protocol.TProtocolException("Required field 'access' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.storm.thrift.protocol.TCompactProtocol(new org.apache.storm.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.storm.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.storm.thrift.protocol.TCompactProtocol(new org.apache.storm.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.storm.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class AccessControlStandardSchemeFactory implements org.apache.storm.thrift.scheme.SchemeFactory {
    public AccessControlStandardScheme getScheme() {
      return new AccessControlStandardScheme();
    }
  }

  private static class AccessControlStandardScheme extends org.apache.storm.thrift.scheme.StandardScheme<AccessControl> {

    public void read(org.apache.storm.thrift.protocol.TProtocol iprot, AccessControl struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.storm.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TYPE
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.I32) {
              struct.type = org.apache.storm.generated.AccessControlType.findByValue(iprot.readI32());
              struct.set_type_isSet(true);
            } else { 
              org.apache.storm.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NAME
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.set_name_isSet(true);
            } else { 
              org.apache.storm.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ACCESS
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.I32) {
              struct.access = iprot.readI32();
              struct.set_access_isSet(true);
            } else { 
              org.apache.storm.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.storm.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.storm.thrift.protocol.TProtocol oprot, AccessControl struct) throws org.apache.storm.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.name != null) {
        if (struct.is_set_name()) {
          oprot.writeFieldBegin(NAME_FIELD_DESC);
          oprot.writeString(struct.name);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldBegin(ACCESS_FIELD_DESC);
      oprot.writeI32(struct.access);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AccessControlTupleSchemeFactory implements org.apache.storm.thrift.scheme.SchemeFactory {
    public AccessControlTupleScheme getScheme() {
      return new AccessControlTupleScheme();
    }
  }

  private static class AccessControlTupleScheme extends org.apache.storm.thrift.scheme.TupleScheme<AccessControl> {

    @Override
    public void write(org.apache.storm.thrift.protocol.TProtocol prot, AccessControl struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TTupleProtocol oprot = (org.apache.storm.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.type.getValue());
      oprot.writeI32(struct.access);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.is_set_name()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.is_set_name()) {
        oprot.writeString(struct.name);
      }
    }

    @Override
    public void read(org.apache.storm.thrift.protocol.TProtocol prot, AccessControl struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TTupleProtocol iprot = (org.apache.storm.thrift.protocol.TTupleProtocol) prot;
      struct.type = org.apache.storm.generated.AccessControlType.findByValue(iprot.readI32());
      struct.set_type_isSet(true);
      struct.access = iprot.readI32();
      struct.set_access_isSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.name = iprot.readString();
        struct.set_name_isSet(true);
      }
    }
  }

  private static <S extends org.apache.storm.thrift.scheme.IScheme> S scheme(org.apache.storm.thrift.protocol.TProtocol proto) {
    return (org.apache.storm.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

