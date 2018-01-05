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
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.storm.generated;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)")
public class HBNodes implements org.apache.thrift.TBase<HBNodes, HBNodes._Fields>, java.io.Serializable, Cloneable, Comparable<HBNodes> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HBNodes");

  private static final org.apache.thrift.protocol.TField PULSE_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("pulseIds", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HBNodesStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HBNodesTupleSchemeFactory());
  }

  private List<String> pulseIds; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PULSE_IDS((short)1, "pulseIds");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // PULSE_IDS
          return PULSE_IDS;
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
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PULSE_IDS, new org.apache.thrift.meta_data.FieldMetaData("pulseIds", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HBNodes.class, metaDataMap);
  }

  public HBNodes() {
  }

  public HBNodes(
    List<String> pulseIds)
  {
    this();
    this.pulseIds = pulseIds;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HBNodes(HBNodes other) {
    if (other.is_set_pulseIds()) {
      List<String> __this__pulseIds = new ArrayList<String>(other.pulseIds);
      this.pulseIds = __this__pulseIds;
    }
  }

  public HBNodes deepCopy() {
    return new HBNodes(this);
  }

  @Override
  public void clear() {
    this.pulseIds = null;
  }

  public int get_pulseIds_size() {
    return (this.pulseIds == null) ? 0 : this.pulseIds.size();
  }

  public java.util.Iterator<String> get_pulseIds_iterator() {
    return (this.pulseIds == null) ? null : this.pulseIds.iterator();
  }

  public void add_to_pulseIds(String elem) {
    if (this.pulseIds == null) {
      this.pulseIds = new ArrayList<String>();
    }
    this.pulseIds.add(elem);
  }

  public List<String> get_pulseIds() {
    return this.pulseIds;
  }

  public void set_pulseIds(List<String> pulseIds) {
    this.pulseIds = pulseIds;
  }

  public void unset_pulseIds() {
    this.pulseIds = null;
  }

  /** Returns true if field pulseIds is set (has been assigned a value) and false otherwise */
  public boolean is_set_pulseIds() {
    return this.pulseIds != null;
  }

  public void set_pulseIds_isSet(boolean value) {
    if (!value) {
      this.pulseIds = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PULSE_IDS:
      if (value == null) {
        unset_pulseIds();
      } else {
        set_pulseIds((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PULSE_IDS:
      return get_pulseIds();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PULSE_IDS:
      return is_set_pulseIds();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HBNodes)
      return this.equals((HBNodes)that);
    return false;
  }

  public boolean equals(HBNodes that) {
    if (that == null)
      return false;

    boolean this_present_pulseIds = true && this.is_set_pulseIds();
    boolean that_present_pulseIds = true && that.is_set_pulseIds();
    if (this_present_pulseIds || that_present_pulseIds) {
      if (!(this_present_pulseIds && that_present_pulseIds))
        return false;
      if (!this.pulseIds.equals(that.pulseIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_pulseIds = true && (is_set_pulseIds());
    list.add(present_pulseIds);
    if (present_pulseIds)
      list.add(pulseIds);

    return list.hashCode();
  }

  @Override
  public int compareTo(HBNodes other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(is_set_pulseIds()).compareTo(other.is_set_pulseIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_pulseIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pulseIds, other.pulseIds);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("HBNodes(");
    boolean first = true;

    sb.append("pulseIds:");
    if (this.pulseIds == null) {
      sb.append("null");
    } else {
      sb.append(this.pulseIds);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class HBNodesStandardSchemeFactory implements SchemeFactory {
    public HBNodesStandardScheme getScheme() {
      return new HBNodesStandardScheme();
    }
  }

  private static class HBNodesStandardScheme extends StandardScheme<HBNodes> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HBNodes struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PULSE_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list878 = iprot.readListBegin();
                struct.pulseIds = new ArrayList<String>(_list878.size);
                String _elem879;
                for (int _i880 = 0; _i880 < _list878.size; ++_i880)
                {
                  _elem879 = iprot.readString();
                  struct.pulseIds.add(_elem879);
                }
                iprot.readListEnd();
              }
              struct.set_pulseIds_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, HBNodes struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pulseIds != null) {
        oprot.writeFieldBegin(PULSE_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.pulseIds.size()));
          for (String _iter881 : struct.pulseIds)
          {
            oprot.writeString(_iter881);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HBNodesTupleSchemeFactory implements SchemeFactory {
    public HBNodesTupleScheme getScheme() {
      return new HBNodesTupleScheme();
    }
  }

  private static class HBNodesTupleScheme extends TupleScheme<HBNodes> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HBNodes struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.is_set_pulseIds()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.is_set_pulseIds()) {
        {
          oprot.writeI32(struct.pulseIds.size());
          for (String _iter882 : struct.pulseIds)
          {
            oprot.writeString(_iter882);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HBNodes struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list883 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.pulseIds = new ArrayList<String>(_list883.size);
          String _elem884;
          for (int _i885 = 0; _i885 < _list883.size; ++_i885)
          {
            _elem884 = iprot.readString();
            struct.pulseIds.add(_elem884);
          }
        }
        struct.set_pulseIds_isSet(true);
      }
    }
  }

}

