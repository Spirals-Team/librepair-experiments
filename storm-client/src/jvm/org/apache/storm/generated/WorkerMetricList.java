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
public class WorkerMetricList implements org.apache.storm.thrift.TBase<WorkerMetricList, WorkerMetricList._Fields>, java.io.Serializable, Cloneable, Comparable<WorkerMetricList> {
  private static final org.apache.storm.thrift.protocol.TStruct STRUCT_DESC = new org.apache.storm.thrift.protocol.TStruct("WorkerMetricList");

  private static final org.apache.storm.thrift.protocol.TField METRICS_FIELD_DESC = new org.apache.storm.thrift.protocol.TField("metrics", org.apache.storm.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.storm.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WorkerMetricListStandardSchemeFactory();
  private static final org.apache.storm.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WorkerMetricListTupleSchemeFactory();

  private java.util.List<WorkerMetricPoint> metrics; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.storm.thrift.TFieldIdEnum {
    METRICS((short)1, "metrics");

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
        case 1: // METRICS
          return METRICS;
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
    tmpMap.put(_Fields.METRICS, new org.apache.storm.thrift.meta_data.FieldMetaData("metrics", org.apache.storm.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.storm.thrift.meta_data.ListMetaData(org.apache.storm.thrift.protocol.TType.LIST, 
            new org.apache.storm.thrift.meta_data.StructMetaData(org.apache.storm.thrift.protocol.TType.STRUCT, WorkerMetricPoint.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.storm.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WorkerMetricList.class, metaDataMap);
  }

  public WorkerMetricList() {
  }

  public WorkerMetricList(
    java.util.List<WorkerMetricPoint> metrics)
  {
    this();
    this.metrics = metrics;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WorkerMetricList(WorkerMetricList other) {
    if (other.is_set_metrics()) {
      java.util.List<WorkerMetricPoint> __this__metrics = new java.util.ArrayList<WorkerMetricPoint>(other.metrics.size());
      for (WorkerMetricPoint other_element : other.metrics) {
        __this__metrics.add(new WorkerMetricPoint(other_element));
      }
      this.metrics = __this__metrics;
    }
  }

  public WorkerMetricList deepCopy() {
    return new WorkerMetricList(this);
  }

  @Override
  public void clear() {
    this.metrics = null;
  }

  public int get_metrics_size() {
    return (this.metrics == null) ? 0 : this.metrics.size();
  }

  public java.util.Iterator<WorkerMetricPoint> get_metrics_iterator() {
    return (this.metrics == null) ? null : this.metrics.iterator();
  }

  public void add_to_metrics(WorkerMetricPoint elem) {
    if (this.metrics == null) {
      this.metrics = new java.util.ArrayList<WorkerMetricPoint>();
    }
    this.metrics.add(elem);
  }

  public java.util.List<WorkerMetricPoint> get_metrics() {
    return this.metrics;
  }

  public void set_metrics(java.util.List<WorkerMetricPoint> metrics) {
    this.metrics = metrics;
  }

  public void unset_metrics() {
    this.metrics = null;
  }

  /** Returns true if field metrics is set (has been assigned a value) and false otherwise */
  public boolean is_set_metrics() {
    return this.metrics != null;
  }

  public void set_metrics_isSet(boolean value) {
    if (!value) {
      this.metrics = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case METRICS:
      if (value == null) {
        unset_metrics();
      } else {
        set_metrics((java.util.List<WorkerMetricPoint>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case METRICS:
      return get_metrics();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case METRICS:
      return is_set_metrics();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof WorkerMetricList)
      return this.equals((WorkerMetricList)that);
    return false;
  }

  public boolean equals(WorkerMetricList that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_metrics = true && this.is_set_metrics();
    boolean that_present_metrics = true && that.is_set_metrics();
    if (this_present_metrics || that_present_metrics) {
      if (!(this_present_metrics && that_present_metrics))
        return false;
      if (!this.metrics.equals(that.metrics))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((is_set_metrics()) ? 131071 : 524287);
    if (is_set_metrics())
      hashCode = hashCode * 8191 + metrics.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(WorkerMetricList other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(is_set_metrics()).compareTo(other.is_set_metrics());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_metrics()) {
      lastComparison = org.apache.storm.thrift.TBaseHelper.compareTo(this.metrics, other.metrics);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WorkerMetricList(");
    boolean first = true;

    sb.append("metrics:");
    if (this.metrics == null) {
      sb.append("null");
    } else {
      sb.append(this.metrics);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.storm.thrift.TException {
    // check for required fields
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

  private static class WorkerMetricListStandardSchemeFactory implements org.apache.storm.thrift.scheme.SchemeFactory {
    public WorkerMetricListStandardScheme getScheme() {
      return new WorkerMetricListStandardScheme();
    }
  }

  private static class WorkerMetricListStandardScheme extends org.apache.storm.thrift.scheme.StandardScheme<WorkerMetricList> {

    public void read(org.apache.storm.thrift.protocol.TProtocol iprot, WorkerMetricList struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.storm.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // METRICS
            if (schemeField.type == org.apache.storm.thrift.protocol.TType.LIST) {
              {
                org.apache.storm.thrift.protocol.TList _list896 = iprot.readListBegin();
                struct.metrics = new java.util.ArrayList<WorkerMetricPoint>(_list896.size);
                WorkerMetricPoint _elem897;
                for (int _i898 = 0; _i898 < _list896.size; ++_i898)
                {
                  _elem897 = new WorkerMetricPoint();
                  _elem897.read(iprot);
                  struct.metrics.add(_elem897);
                }
                iprot.readListEnd();
              }
              struct.set_metrics_isSet(true);
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

    public void write(org.apache.storm.thrift.protocol.TProtocol oprot, WorkerMetricList struct) throws org.apache.storm.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.metrics != null) {
        oprot.writeFieldBegin(METRICS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.storm.thrift.protocol.TList(org.apache.storm.thrift.protocol.TType.STRUCT, struct.metrics.size()));
          for (WorkerMetricPoint _iter899 : struct.metrics)
          {
            _iter899.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WorkerMetricListTupleSchemeFactory implements org.apache.storm.thrift.scheme.SchemeFactory {
    public WorkerMetricListTupleScheme getScheme() {
      return new WorkerMetricListTupleScheme();
    }
  }

  private static class WorkerMetricListTupleScheme extends org.apache.storm.thrift.scheme.TupleScheme<WorkerMetricList> {

    @Override
    public void write(org.apache.storm.thrift.protocol.TProtocol prot, WorkerMetricList struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TTupleProtocol oprot = (org.apache.storm.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.is_set_metrics()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.is_set_metrics()) {
        {
          oprot.writeI32(struct.metrics.size());
          for (WorkerMetricPoint _iter900 : struct.metrics)
          {
            _iter900.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.storm.thrift.protocol.TProtocol prot, WorkerMetricList struct) throws org.apache.storm.thrift.TException {
      org.apache.storm.thrift.protocol.TTupleProtocol iprot = (org.apache.storm.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.storm.thrift.protocol.TList _list901 = new org.apache.storm.thrift.protocol.TList(org.apache.storm.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.metrics = new java.util.ArrayList<WorkerMetricPoint>(_list901.size);
          WorkerMetricPoint _elem902;
          for (int _i903 = 0; _i903 < _list901.size; ++_i903)
          {
            _elem902 = new WorkerMetricPoint();
            _elem902.read(iprot);
            struct.metrics.add(_elem902);
          }
        }
        struct.set_metrics_isSet(true);
      }
    }
  }

  private static <S extends org.apache.storm.thrift.scheme.IScheme> S scheme(org.apache.storm.thrift.protocol.TProtocol proto) {
    return (org.apache.storm.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

