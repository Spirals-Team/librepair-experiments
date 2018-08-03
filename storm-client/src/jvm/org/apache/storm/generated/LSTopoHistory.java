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
public class LSTopoHistory implements org.apache.storm.thrift.TBase<LSTopoHistory, LSTopoHistory._Fields>, java.io.Serializable, Cloneable, Comparable<LSTopoHistory> {
  private static final org.apache.storm.thrift.protocol.TStruct STRUCT_DESC = new org.apache.storm.thrift.protocol.TStruct("LSTopoHistory");

  private static final org.apache.storm.thrift.protocol.TField TOPOLOGY_ID_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("topology_id", org.apache.storm.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.storm.thrift.protocol.TField TIME_STAMP_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("time_stamp", org.apache.storm.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.storm.thrift.protocol.TField USERS_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("users", org.apache.storm.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.storm.thrift.protocol.TField GROUPS_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("groups", org.apache.storm.thrift.protocol.TType.LIST, (short)4);

  private static final org.apache.storm.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new LSTopoHistoryStandardSchemeFactory();
  private static final org.apache.storm.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new LSTopoHistoryTupleSchemeFactory();

  private java.lang.String topology_id; // required
  private long time_stamp; // required
  private java.util.List<java.lang.String> users; // required
  private java.util.List<java.lang.String> groups; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.storm.thrift.TFieldIdEnum {
    TOPOLOGY_ID((short)1, "topology_id"),
    TIME_STAMP((short)2, "time_stamp"),
    USERS((short)3, "users"),
    GROUPS((short)4, "groups");

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
        case 1: // TOPOLOGY_ID
          return TOPOLOGY_ID;
        case 2: // TIME_STAMP
          return TIME_STAMP;
        case 3: // USERS
          return USERS;
        case 4: // GROUPS
          return GROUPS;
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
  private static final int __TIME_STAMP_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOPOLOGY_ID, new org.apache.storm.thrift.meta_data.FieldMetaData("topology_id", org.apache.storm.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.storm.thrift.meta_data.FieldValueMetaData(org.apache.storm.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TIME_STAMP, new org.apache.storm.thrift.meta_data.FieldMetaData("time_stamp", org.apache.storm.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.storm.thrift.meta_data.FieldValueMetaData(org.apache.storm.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.USERS, new org.apache.storm.thrift.meta_data.FieldMetaData("users", org.apache.storm.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.storm.thrift.meta_data.ListMetaData(org.apache.storm.thrift.protocol.TType.LIST, 
            new org.apache.storm.thrift.meta_data.FieldValueMetaData(org.apache.storm.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.GROUPS, new org.apache.storm.thrift.meta_data.FieldMetaData("groups", org.apache.storm.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.storm.thrift.meta_data.ListMetaData(org.apache.storm.thrift.protocol.TType.LIST, 
            new org.apache.storm.thrift.meta_data.FieldValueMetaData(org.apache.storm.thrift.protocol.TType.STRING))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.storm.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LSTopoHistory.class, metaDataMap);
  }

  public LSTopoHistory() {
  }

  public LSTopoHistory(
    java.lang.String topology_id,
    long time_stamp,
    java.util.List<java.lang.String> users,
    java.util.List<java.lang.String> groups)
  {
    this();
    this.topology_id = topology_id;
    this.time_stamp = time_stamp;
    set_time_stamp_isSet(true);
    this.users = users;
    this.groups = groups;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LSTopoHistory(LSTopoHistory other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.is_set_topology_id()) {
      this.topology_id = other.topology_id;
    }
    this.time_stamp = other.time_stamp;
    if (other.is_set_users()) {
      java.util.List<java.lang.String> __this__users = new java.util.ArrayList<java.lang.String>(other.users);
      this.users = __this__users;
    }
    if (other.is_set_groups()) {
      java.util.List<java.lang.String> __this__groups = new java.util.ArrayList<java.lang.String>(other.groups);
      this.groups = __this__groups;
    }
  }

  public LSTopoHistory deepCopy() {
    return new LSTopoHistory(this);
  }

  @Override
  public void clear() {
    this.topology_id = null;
    set_time_stamp_isSet(false);
    this.time_stamp = 0;
    this.users = null;
    this.groups = null;
  }

  public java.lang.String get_topology_id() {
    return this.topology_id;
  }

  public void set_topology_id(java.lang.String topology_id) {
    this.topology_id = topology_id;
  }

  public void unset_topology_id() {
    this.topology_id = null;
  }

  /** Returns true if field topology_id is set (has been assigned a value) and false otherwise */
  public boolean is_set_topology_id() {
    return this.topology_id != null;
  }

  public void set_topology_id_isSet(boolean value) {
    if (!value) {
      this.topology_id = null;
    }
  }

  public long get_time_stamp() {
    return this.time_stamp;
  }

  public void set_time_stamp(long time_stamp) {
    this.time_stamp = time_stamp;
    set_time_stamp_isSet(true);
  }

  public void unset_time_stamp() {
    __isset_bitfield = org.apache.storm.thrift.EncodingUtils.clearBit(__isset_bitfield, __TIME_STAMP_ISSET_ID);
  }

  /** Returns true if field time_stamp is set (has been assigned a value) and false otherwise */
  public boolean is_set_time_stamp() {
    return org.apache.storm.thrift.EncodingUtils.testBit(__isset_bitfield, __TIME_STAMP_ISSET_ID);
  }

  public void set_time_stamp_isSet(boolean value) {
    __isset_bitfield = org.apache.storm.thrift.EncodingUtils.setBit(__isset_bitfield, __TIME_STAMP_ISSET_ID, value);
  }

  public int get_users_size() {
    return (this.users == null) ? 0 : this.users.size();
  }

  public java.util.Iterator<java.lang.String> get_users_iterator() {
    return (this.users == null) ? null : this.users.iterator();
  }

  public void add_to_users(java.lang.String elem) {
    if (this.users == null) {
      this.users = new java.util.ArrayList<java.lang.String>();
    }
    this.users.add(elem);
  }

  public java.util.List<java.lang.String> get_users() {
    return this.users;
  }

  public void set_users(java.util.List<java.lang.String> users) {
    this.users = users;
  }

  public void unset_users() {
    this.users = null;
  }

  /** Returns true if field users is set (has been assigned a value) and false otherwise */
  public boolean is_set_users() {
    return this.users != null;
  }

  public void set_users_isSet(boolean value) {
    if (!value) {
      this.users = null;
    }
  }

  public int get_groups_size() {
    return (this.groups == null) ? 0 : this.groups.size();
  }

  public java.util.Iterator<java.lang.String> get_groups_iterator() {
    return (this.groups == null) ? null : this.groups.iterator();
  }

  public void add_to_groups(java.lang.String elem) {
    if (this.groups == null) {
      this.groups = new java.util.ArrayList<java.lang.String>();
    }
    this.groups.add(elem);
  }

  public java.util.List<java.lang.String> get_groups() {
    return this.groups;
  }

  public void set_groups(java.util.List<java.lang.String> groups) {
    this.groups = groups;
  }

  public void unset_groups() {
    this.groups = null;
  }

  /** Returns true if field groups is set (has been assigned a value) and false otherwise */
  public boolean is_set_groups() {
    return this.groups != null;
  }

  public void set_groups_isSet(boolean value) {
    if (!value) {
      this.groups = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TOPOLOGY_ID:
      if (value == null) {
        unset_topology_id();
      } else {
        set_topology_id((java.lang.String)value);
      }
      break;

    case TIME_STAMP:
      if (value == null) {
        unset_time_stamp();
      } else {
        set_time_stamp((java.lang.Long)value);
      }
      break;

    case USERS:
      if (value == null) {
        unset_users();
      } else {
        set_users((java.util.List<java.lang.String>)value);
      }
      break;

    case GROUPS:
      if (value == null) {
        unset_groups();
      } else {
        set_groups((java.util.List<java.lang.String>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TOPOLOGY_ID:
      return get_topology_id();

    case TIME_STAMP:
      return get_time_stamp();

    case USERS:
      return get_users();

    case GROUPS:
      return get_groups();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TOPOLOGY_ID:
      return is_set_topology_id();
    case TIME_STAMP:
      return is_set_time_stamp();
    case USERS:
      return is_set_users();
    case GROUPS:
      return is_set_groups();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof LSTopoHistory)
      return this.equals((LSTopoHistory)that);
    return false;
  }

  public boolean equals(LSTopoHistory that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_topology_id = true && this.is_set_topology_id();
    boolean that_present_topology_id = true && that.is_set_topology_id();
    if (this_present_topology_id || that_present_topology_id) {
      if (!(this_present_topology_id && that_present_topology_id))
        return false;
      if (!this.topology_id.equals(that.topology_id))
        return false;
    }

    boolean this_present_time_stamp = true;
    boolean that_present_time_stamp = true;
    if (this_present_time_stamp || that_present_time_stamp) {
      if (!(this_present_time_stamp && that_present_time_stamp))
        return false;
      if (this.time_stamp != that.time_stamp)
        return false;
    }

    boolean this_present_users = true && this.is_set_users();
    boolean that_present_users = true && that.is_set_users();
    if (this_present_users || that_present_users) {
      if (!(this_present_users && that_present_users))
        return false;
      if (!this.users.equals(that.users))
        return false;
    }

    boolean this_present_groups = true && this.is_set_groups();
    boolean that_present_groups = true && that.is_set_groups();
    if (this_present_groups || that_present_groups) {
      if (!(this_present_groups && that_present_groups))
        return false;
      if (!this.groups.equals(that.groups))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((is_set_topology_id()) ? 131071 : 524287);
    if (is_set_topology_id())
      hashCode = hashCode * 8191 + topology_id.hashCode();

    hashCode = hashCode * 8191 + org.apache.storm.thrift.TBaseHelper.hashCode(time_stamp);

    hashCode = hashCode * 8191 + ((is_set_users()) ? 131071 : 524287);
    if (is_set_users())
      hashCode = hashCode * 8191 + users.hashCode();

    hashCode = hashCode * 8191 + ((is_set_groups()) ? 131071 : 524287);
    if (is_set_groups())
      hashCode = hashCode * 8191 + groups.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(LSTopoHistory other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(is_set_topology_id()).compareTo(other.is_set_topology_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_topology_id()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.topology_id, other.topology_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(is_set_time_stamp()).compareTo(other.is_set_time_stamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_time_stamp()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.time_stamp, other.time_stamp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(is_set_users()).compareTo(other.is_set_users());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_users()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.users, other.users);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(is_set_groups()).compareTo(other.is_set_groups());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_groups()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.groups, other.groups);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("LSTopoHistory(");
    boolean first = true;

    sb.append("topology_id:");
    if (this.topology_id == null) {
      sb.append("null");
    } else {
      sb.append(this.topology_id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("time_stamp:");
    sb.append(this.time_stamp);
    first = false;
    if (!first) sb.append(", ");
    sb.append("users:");
    if (this.users == null) {
      sb.append("null");
    } else {
      sb.append(this.users);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("groups:");
    if (this.groups == null) {
      sb.append("null");
    } else {
      sb.append(this.groups);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.storm.thrift.TException {
    // check for required fields
    if (!is_set_topology_id()) {
      throw new org.apache.storm.thrift.protocol.TProtocolException("Required field 'topology_id' is unset! Struct:" + toString());
    }

    if (!is_set_time_stamp()) {
      throw new org.apache.storm.thrift.protocol.TProtocolException("Required field 'time_stamp' is unset! Struct:" + toString());
    }

    if (!is_set_users()) {
      throw new org.apache.storm.thrift.protocol.TProtocolException("Required field 'users' is unset! Struct:" + toString());
    }

    if (!is_set_groups()) {
      throw new org.apache.storm.thrift.protocol.TProtocolException("Required field 'groups' is unset! Struct:" + toString());
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

  private static class LSTopoHistoryStandardSchemeFactory implements org.apache.storm.thrift.scheme.SchemeFactory {
    public LSTopoHistoryStandardScheme getScheme() {
      return new LSTopoHistoryStandardScheme();
    }
  }

  private static class LSTopoHistoryStandardScheme extends org.apache.storm.thrift.scheme.StandardScheme<LSTopoHistory> {

    public void read(org.apache.storm.thrift.protocol.TProtocol iprot, LSTopoHistory struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.storm.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOPOLOGY_ID
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.STRING) {
              struct.topology_id = iprot.readString();
              struct.set_topology_id_isSet(true);
            } else { 
              org.apache.storm.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TIME_STAMP
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.I64) {
              struct.time_stamp = iprot.readI64();
              struct.set_time_stamp_isSet(true);
            } else { 
              org.apache.storm.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USERS
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.LIST) {
              {
                org.apache.storm.thrift.protocol.TList _list828 = iprot.readListBegin();
                struct.users = new java.util.ArrayList<java.lang.String>(_list828.size);
                java.lang.String _elem829;
                for (int _i830 = 0; _i830 < _list828.size; ++_i830)
                {
                  _elem829 = iprot.readString();
                  struct.users.add(_elem829);
                }
                iprot.readListEnd();
              }
              struct.set_users_isSet(true);
            } else { 
              org.apache.storm.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // GROUPS
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.LIST) {
              {
                org.apache.storm.thrift.protocol.TList _list831 = iprot.readListBegin();
                struct.groups = new java.util.ArrayList<java.lang.String>(_list831.size);
                java.lang.String _elem832;
                for (int _i833 = 0; _i833 < _list831.size; ++_i833)
                {
                  _elem832 = iprot.readString();
                  struct.groups.add(_elem832);
                }
                iprot.readListEnd();
              }
              struct.set_groups_isSet(true);
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

    public void write(org.apache.storm.thrift.protocol.TProtocol oprot, LSTopoHistory struct) throws org.apache.storm.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.topology_id != null) {
        oprot.writeFieldBegin(TOPOLOGY_ID_FIELD_DESC);
        oprot.writeString(struct.topology_id);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TIME_STAMP_FIELD_DESC);
      oprot.writeI64(struct.time_stamp);
      oprot.writeFieldEnd();
      if (struct.users != null) {
        oprot.writeFieldBegin(USERS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.storm.thrift.protocol.TList(org.apache.storm.thrift.protocol.TType.STRING, struct.users.size()));
          for (java.lang.String _iter834 : struct.users)
          {
            oprot.writeString(_iter834);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.groups != null) {
        oprot.writeFieldBegin(GROUPS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.storm.thrift.protocol.TList(org.apache.storm.thrift.protocol.TType.STRING, struct.groups.size()));
          for (java.lang.String _iter835 : struct.groups)
          {
            oprot.writeString(_iter835);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LSTopoHistoryTupleSchemeFactory implements org.apache.storm.thrift.scheme.SchemeFactory {
    public LSTopoHistoryTupleScheme getScheme() {
      return new LSTopoHistoryTupleScheme();
    }
  }

  private static class LSTopoHistoryTupleScheme extends org.apache.storm.thrift.scheme.TupleScheme<LSTopoHistory> {

    @Override
    public void write(org.apache.storm.thrift.protocol.TProtocol prot, LSTopoHistory struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TTupleProtocol oprot = (org.apache.storm.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.topology_id);
      oprot.writeI64(struct.time_stamp);
      {
        oprot.writeI32(struct.users.size());
        for (java.lang.String _iter836 : struct.users)
        {
          oprot.writeString(_iter836);
        }
      }
      {
        oprot.writeI32(struct.groups.size());
        for (java.lang.String _iter837 : struct.groups)
        {
          oprot.writeString(_iter837);
        }
      }
    }

    @Override
    public void read(org.apache.storm.thrift.protocol.TProtocol prot, LSTopoHistory struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TTupleProtocol iprot = (org.apache.storm.thrift.protocol.TTupleProtocol) prot;
      struct.topology_id = iprot.readString();
      struct.set_topology_id_isSet(true);
      struct.time_stamp = iprot.readI64();
      struct.set_time_stamp_isSet(true);
      {
        org.apache.storm.thrift.protocol.TList _list838 = new org.apache.storm.thrift.protocol.TList(org.apache.storm.thrift.protocol.TType.STRING, iprot.readI32());
        struct.users = new java.util.ArrayList<java.lang.String>(_list838.size);
        java.lang.String _elem839;
        for (int _i840 = 0; _i840 < _list838.size; ++_i840)
        {
          _elem839 = iprot.readString();
          struct.users.add(_elem839);
        }
      }
      struct.set_users_isSet(true);
      {
        org.apache.storm.thrift.protocol.TList _list841 = new org.apache.storm.thrift.protocol.TList(org.apache.storm.thrift.protocol.TType.STRING, iprot.readI32());
        struct.groups = new java.util.ArrayList<java.lang.String>(_list841.size);
        java.lang.String _elem842;
        for (int _i843 = 0; _i843 < _list841.size; ++_i843)
        {
          _elem842 = iprot.readString();
          struct.groups.add(_elem842);
        }
      }
      struct.set_groups_isSet(true);
    }
  }

  private static <S extends org.apache.storm.thrift.scheme.IScheme> S scheme(org.apache.storm.thrift.protocol.TProtocol proto) {
    return (org.apache.storm.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

