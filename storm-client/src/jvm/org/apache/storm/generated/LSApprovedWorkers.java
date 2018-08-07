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
public class LSApprovedWorkers implements org.apache.storm.thrift.TBase<LSApprovedWorkers, LSApprovedWorkers._Fields>, java.io.Serializable, Cloneable, Comparable<LSApprovedWorkers> {
  private static final org.apache.storm.thrift.protocol.TStruct STRUCT_DESC = new org.apache.storm.thrift.protocol.TStruct("LSApprovedWorkers");

  private static final org.apache.storm.thrift.protocol.TField APPROVED_WORKERS_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("approved_workers", org.apache.storm.thrift.protocol.TType.MAP, (short)1);

  private static final org.apache.storm.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new LSApprovedWorkersStandardSchemeFactory();
  private static final org.apache.storm.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new LSApprovedWorkersTupleSchemeFactory();

  private java.util.Map<java.lang.String,java.lang.Integer> approved_workers; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.storm.thrift.TFieldIdEnum {
    APPROVED_WORKERS((short)1, "approved_workers");

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
        case 1: // APPROVED_WORKERS
          return APPROVED_WORKERS;
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
  public static final java.util.Map<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.storm.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.APPROVED_WORKERS, new org.apache.storm.thrift.meta_data.FieldMetaData("approved_workers", org.apache.storm.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.storm.thrift.meta_data.MapMetaData(org.apache.storm.thrift.protocol.TType.MAP, 
            new org.apache.storm.thrift.meta_data.FieldValueMetaData(org.apache.storm.thrift.protocol.TType.STRING), 
            new org.apache.storm.thrift.meta_data.FieldValueMetaData(org.apache.storm.thrift.protocol.TType.I32))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.storm.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LSApprovedWorkers.class, metaDataMap);
  }

  public LSApprovedWorkers() {
  }

  public LSApprovedWorkers(
    java.util.Map<java.lang.String,java.lang.Integer> approved_workers)
  {
    this();
    this.approved_workers = approved_workers;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LSApprovedWorkers(LSApprovedWorkers other) {
    if (other.is_set_approved_workers()) {
      java.util.Map<java.lang.String,java.lang.Integer> __this__approved_workers = new java.util.HashMap<java.lang.String,java.lang.Integer>(other.approved_workers);
      this.approved_workers = __this__approved_workers;
    }
  }

  public LSApprovedWorkers deepCopy() {
    return new LSApprovedWorkers(this);
  }

  @Override
  public void clear() {
    this.approved_workers = null;
  }

  public int get_approved_workers_size() {
    return (this.approved_workers == null) ? 0 : this.approved_workers.size();
  }

  public void put_to_approved_workers(java.lang.String key, int val) {
    if (this.approved_workers == null) {
      this.approved_workers = new java.util.HashMap<java.lang.String,java.lang.Integer>();
    }
    this.approved_workers.put(key, val);
  }

  public java.util.Map<java.lang.String,java.lang.Integer> get_approved_workers() {
    return this.approved_workers;
  }

  public void set_approved_workers(java.util.Map<java.lang.String,java.lang.Integer> approved_workers) {
    this.approved_workers = approved_workers;
  }

  public void unset_approved_workers() {
    this.approved_workers = null;
  }

  /** Returns true if field approved_workers is set (has been assigned a value) and false otherwise */
  public boolean is_set_approved_workers() {
    return this.approved_workers != null;
  }

  public void set_approved_workers_isSet(boolean value) {
    if (!value) {
      this.approved_workers = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case APPROVED_WORKERS:
      if (value == null) {
        unset_approved_workers();
      } else {
        set_approved_workers((java.util.Map<java.lang.String,java.lang.Integer>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case APPROVED_WORKERS:
      return get_approved_workers();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case APPROVED_WORKERS:
      return is_set_approved_workers();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof LSApprovedWorkers)
      return this.equals((LSApprovedWorkers)that);
    return false;
  }

  public boolean equals(LSApprovedWorkers that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_approved_workers = true && this.is_set_approved_workers();
    boolean that_present_approved_workers = true && that.is_set_approved_workers();
    if (this_present_approved_workers || that_present_approved_workers) {
      if (!(this_present_approved_workers && that_present_approved_workers))
        return false;
      if (!this.approved_workers.equals(that.approved_workers))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((is_set_approved_workers()) ? 131071 : 524287);
    if (is_set_approved_workers())
      hashCode = hashCode * 8191 + approved_workers.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(LSApprovedWorkers other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(is_set_approved_workers()).compareTo(other.is_set_approved_workers());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_approved_workers()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.approved_workers, other.approved_workers);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("LSApprovedWorkers(");
    boolean first = true;

    sb.append("approved_workers:");
    if (this.approved_workers == null) {
      sb.append("null");
    } else {
      sb.append(this.approved_workers);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.storm.thrift.TException {
    // check for required fields
    if (!is_set_approved_workers()) {
      throw new org.apache.storm.thrift.protocol.TProtocolException("Required field 'approved_workers' is unset! Struct:" + toString());
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
      read(new org.apache.storm.thrift.protocol.TCompactProtocol(new org.apache.storm.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.storm.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class LSApprovedWorkersStandardSchemeFactory implements org.apache.storm.thrift.scheme.SchemeFactory {
    public LSApprovedWorkersStandardScheme getScheme() {
      return new LSApprovedWorkersStandardScheme();
    }
  }

  private static class LSApprovedWorkersStandardScheme extends org.apache.storm.thrift.scheme.StandardScheme<LSApprovedWorkers> {

    public void read(org.apache.storm.thrift.protocol.TProtocol iprot, LSApprovedWorkers struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.storm.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // APPROVED_WORKERS
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.MAP) {
              {
                org.apache.storm.thrift.protocol.TMap _map800 = iprot.readMapBegin();
                struct.approved_workers = new java.util.HashMap<java.lang.String,java.lang.Integer>(2*_map800.size);
                java.lang.String _key801;
                int _val802;
                for (int _i803 = 0; _i803 < _map800.size; ++_i803)
                {
                  _key801 = iprot.readString();
                  _val802 = iprot.readI32();
                  struct.approved_workers.put(_key801, _val802);
                }
                iprot.readMapEnd();
              }
              struct.set_approved_workers_isSet(true);
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

    public void write(org.apache.storm.thrift.protocol.TProtocol oprot, LSApprovedWorkers struct) throws org.apache.storm.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.approved_workers != null) {
        oprot.writeFieldBegin(APPROVED_WORKERS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.storm.thrift.protocol.TMap(org.apache.storm.thrift.protocol.TType.STRING, org.apache.storm.thrift.protocol.TType.I32, struct.approved_workers.size()));
          for (java.util.Map.Entry<java.lang.String, java.lang.Integer> _iter804 : struct.approved_workers.entrySet())
          {
            oprot.writeString(_iter804.getKey());
            oprot.writeI32(_iter804.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LSApprovedWorkersTupleSchemeFactory implements org.apache.storm.thrift.scheme.SchemeFactory {
    public LSApprovedWorkersTupleScheme getScheme() {
      return new LSApprovedWorkersTupleScheme();
    }
  }

  private static class LSApprovedWorkersTupleScheme extends org.apache.storm.thrift.scheme.TupleScheme<LSApprovedWorkers> {

    @Override
    public void write(org.apache.storm.thrift.protocol.TProtocol prot, LSApprovedWorkers struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TTupleProtocol oprot = (org.apache.storm.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.approved_workers.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> _iter805 : struct.approved_workers.entrySet())
        {
          oprot.writeString(_iter805.getKey());
          oprot.writeI32(_iter805.getValue());
        }
      }
    }

    @Override
    public void read(org.apache.storm.thrift.protocol.TProtocol prot, LSApprovedWorkers struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TTupleProtocol iprot = (org.apache.storm.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.storm.thrift.protocol.TMap _map806 = new org.apache.storm.thrift.protocol.TMap(org.apache.storm.thrift.protocol.TType.STRING, org.apache.storm.thrift.protocol.TType.I32, iprot.readI32());
        struct.approved_workers = new java.util.HashMap<java.lang.String,java.lang.Integer>(2*_map806.size);
        java.lang.String _key807;
        int _val808;
        for (int _i809 = 0; _i809 < _map806.size; ++_i809)
        {
          _key807 = iprot.readString();
          _val808 = iprot.readI32();
          struct.approved_workers.put(_key807, _val808);
        }
      }
      struct.set_approved_workers_isSet(true);
    }
  }

  private static <S extends org.apache.storm.thrift.scheme.IScheme> S scheme(org.apache.storm.thrift.protocol.TProtocol proto) {
    return (org.apache.storm.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

