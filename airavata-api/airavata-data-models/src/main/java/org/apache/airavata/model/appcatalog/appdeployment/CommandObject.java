/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.airavata.model.appcatalog.appdeployment;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * Job commands to be used in Pre Job, Post Job and Module Load Commands
 * 
 * command:
 *   The actual command in string format
 * 
 * commandOrder:
 *   Order of the command in the multiple command situation
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)")
public class CommandObject implements org.apache.thrift.TBase<CommandObject, CommandObject._Fields>, java.io.Serializable, Cloneable, Comparable<CommandObject> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CommandObject");

  private static final org.apache.thrift.protocol.TField COMMAND_FIELD_DESC = new org.apache.thrift.protocol.TField("command", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField COMMAND_ORDER_FIELD_DESC = new org.apache.thrift.protocol.TField("commandOrder", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CommandObjectStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CommandObjectTupleSchemeFactory();

  private java.lang.String command; // required
  private int commandOrder; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COMMAND((short)1, "command"),
    COMMAND_ORDER((short)2, "commandOrder");

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
        case 1: // COMMAND
          return COMMAND;
        case 2: // COMMAND_ORDER
          return COMMAND_ORDER;
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
  private static final int __COMMANDORDER_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.COMMAND_ORDER};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COMMAND, new org.apache.thrift.meta_data.FieldMetaData("command", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMMAND_ORDER, new org.apache.thrift.meta_data.FieldMetaData("commandOrder", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CommandObject.class, metaDataMap);
  }

  public CommandObject() {
  }

  public CommandObject(
    java.lang.String command)
  {
    this();
    this.command = command;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CommandObject(CommandObject other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetCommand()) {
      this.command = other.command;
    }
    this.commandOrder = other.commandOrder;
  }

  public CommandObject deepCopy() {
    return new CommandObject(this);
  }

  @Override
  public void clear() {
    this.command = null;
    setCommandOrderIsSet(false);
    this.commandOrder = 0;
  }

  public java.lang.String getCommand() {
    return this.command;
  }

  public void setCommand(java.lang.String command) {
    this.command = command;
  }

  public void unsetCommand() {
    this.command = null;
  }

  /** Returns true if field command is set (has been assigned a value) and false otherwise */
  public boolean isSetCommand() {
    return this.command != null;
  }

  public void setCommandIsSet(boolean value) {
    if (!value) {
      this.command = null;
    }
  }

  public int getCommandOrder() {
    return this.commandOrder;
  }

  public void setCommandOrder(int commandOrder) {
    this.commandOrder = commandOrder;
    setCommandOrderIsSet(true);
  }

  public void unsetCommandOrder() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COMMANDORDER_ISSET_ID);
  }

  /** Returns true if field commandOrder is set (has been assigned a value) and false otherwise */
  public boolean isSetCommandOrder() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COMMANDORDER_ISSET_ID);
  }

  public void setCommandOrderIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COMMANDORDER_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case COMMAND:
      if (value == null) {
        unsetCommand();
      } else {
        setCommand((java.lang.String)value);
      }
      break;

    case COMMAND_ORDER:
      if (value == null) {
        unsetCommandOrder();
      } else {
        setCommandOrder((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case COMMAND:
      return getCommand();

    case COMMAND_ORDER:
      return getCommandOrder();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case COMMAND:
      return isSetCommand();
    case COMMAND_ORDER:
      return isSetCommandOrder();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof CommandObject)
      return this.equals((CommandObject)that);
    return false;
  }

  public boolean equals(CommandObject that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_command = true && this.isSetCommand();
    boolean that_present_command = true && that.isSetCommand();
    if (this_present_command || that_present_command) {
      if (!(this_present_command && that_present_command))
        return false;
      if (!this.command.equals(that.command))
        return false;
    }

    boolean this_present_commandOrder = true && this.isSetCommandOrder();
    boolean that_present_commandOrder = true && that.isSetCommandOrder();
    if (this_present_commandOrder || that_present_commandOrder) {
      if (!(this_present_commandOrder && that_present_commandOrder))
        return false;
      if (this.commandOrder != that.commandOrder)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetCommand()) ? 131071 : 524287);
    if (isSetCommand())
      hashCode = hashCode * 8191 + command.hashCode();

    hashCode = hashCode * 8191 + ((isSetCommandOrder()) ? 131071 : 524287);
    if (isSetCommandOrder())
      hashCode = hashCode * 8191 + commandOrder;

    return hashCode;
  }

  @Override
  public int compareTo(CommandObject other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetCommand()).compareTo(other.isSetCommand());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommand()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.command, other.command);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCommandOrder()).compareTo(other.isSetCommandOrder());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommandOrder()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.commandOrder, other.commandOrder);
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
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("CommandObject(");
    boolean first = true;

    sb.append("command:");
    if (this.command == null) {
      sb.append("null");
    } else {
      sb.append(this.command);
    }
    first = false;
    if (isSetCommandOrder()) {
      if (!first) sb.append(", ");
      sb.append("commandOrder:");
      sb.append(this.commandOrder);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetCommand()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'command' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CommandObjectStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CommandObjectStandardScheme getScheme() {
      return new CommandObjectStandardScheme();
    }
  }

  private static class CommandObjectStandardScheme extends org.apache.thrift.scheme.StandardScheme<CommandObject> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CommandObject struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COMMAND
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.command = iprot.readString();
              struct.setCommandIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COMMAND_ORDER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.commandOrder = iprot.readI32();
              struct.setCommandOrderIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CommandObject struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.command != null) {
        oprot.writeFieldBegin(COMMAND_FIELD_DESC);
        oprot.writeString(struct.command);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCommandOrder()) {
        oprot.writeFieldBegin(COMMAND_ORDER_FIELD_DESC);
        oprot.writeI32(struct.commandOrder);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CommandObjectTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CommandObjectTupleScheme getScheme() {
      return new CommandObjectTupleScheme();
    }
  }

  private static class CommandObjectTupleScheme extends org.apache.thrift.scheme.TupleScheme<CommandObject> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CommandObject struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.command);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetCommandOrder()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetCommandOrder()) {
        oprot.writeI32(struct.commandOrder);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CommandObject struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.command = iprot.readString();
      struct.setCommandIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.commandOrder = iprot.readI32();
        struct.setCommandOrderIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

