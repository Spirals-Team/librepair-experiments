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
package org.apache.airavata.model.messaging.event;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)")
public class Message implements org.apache.thrift.TBase<Message, Message._Fields>, java.io.Serializable, Cloneable, Comparable<Message> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Message");

  private static final org.apache.thrift.protocol.TField EVENT_FIELD_DESC = new org.apache.thrift.protocol.TField("event", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField MESSAGE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("messageId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MESSAGE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("messageType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField UPDATED_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("updatedTime", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField MESSAGE_LEVEL_FIELD_DESC = new org.apache.thrift.protocol.TField("messageLevel", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MessageStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MessageTupleSchemeFactory();

  private java.nio.ByteBuffer event; // required
  private java.lang.String messageId; // required
  private MessageType messageType; // required
  private long updatedTime; // optional
  private MessageLevel messageLevel; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    EVENT((short)1, "event"),
    MESSAGE_ID((short)2, "messageId"),
    /**
     * 
     * @see MessageType
     */
    MESSAGE_TYPE((short)3, "messageType"),
    UPDATED_TIME((short)4, "updatedTime"),
    /**
     * 
     * @see MessageLevel
     */
    MESSAGE_LEVEL((short)5, "messageLevel");

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
        case 1: // EVENT
          return EVENT;
        case 2: // MESSAGE_ID
          return MESSAGE_ID;
        case 3: // MESSAGE_TYPE
          return MESSAGE_TYPE;
        case 4: // UPDATED_TIME
          return UPDATED_TIME;
        case 5: // MESSAGE_LEVEL
          return MESSAGE_LEVEL;
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
  private static final int __UPDATEDTIME_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.UPDATED_TIME,_Fields.MESSAGE_LEVEL};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.EVENT, new org.apache.thrift.meta_data.FieldMetaData("event", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.MESSAGE_ID, new org.apache.thrift.meta_data.FieldMetaData("messageId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MESSAGE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("messageType", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, MessageType.class)));
    tmpMap.put(_Fields.UPDATED_TIME, new org.apache.thrift.meta_data.FieldMetaData("updatedTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.MESSAGE_LEVEL, new org.apache.thrift.meta_data.FieldMetaData("messageLevel", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, MessageLevel.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Message.class, metaDataMap);
  }

  public Message() {
    this.messageId = "DO_NOT_SET_AT_CLIENTS";

  }

  public Message(
    java.nio.ByteBuffer event,
    java.lang.String messageId,
    MessageType messageType)
  {
    this();
    this.event = org.apache.thrift.TBaseHelper.copyBinary(event);
    this.messageId = messageId;
    this.messageType = messageType;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Message(Message other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetEvent()) {
      this.event = org.apache.thrift.TBaseHelper.copyBinary(other.event);
    }
    if (other.isSetMessageId()) {
      this.messageId = other.messageId;
    }
    if (other.isSetMessageType()) {
      this.messageType = other.messageType;
    }
    this.updatedTime = other.updatedTime;
    if (other.isSetMessageLevel()) {
      this.messageLevel = other.messageLevel;
    }
  }

  public Message deepCopy() {
    return new Message(this);
  }

  @Override
  public void clear() {
    this.event = null;
    this.messageId = "DO_NOT_SET_AT_CLIENTS";

    this.messageType = null;
    setUpdatedTimeIsSet(false);
    this.updatedTime = 0;
    this.messageLevel = null;
  }

  public byte[] getEvent() {
    setEvent(org.apache.thrift.TBaseHelper.rightSize(event));
    return event == null ? null : event.array();
  }

  public java.nio.ByteBuffer bufferForEvent() {
    return org.apache.thrift.TBaseHelper.copyBinary(event);
  }

  public void setEvent(byte[] event) {
    this.event = event == null ? (java.nio.ByteBuffer)null : java.nio.ByteBuffer.wrap(event.clone());
  }

  public void setEvent(java.nio.ByteBuffer event) {
    this.event = org.apache.thrift.TBaseHelper.copyBinary(event);
  }

  public void unsetEvent() {
    this.event = null;
  }

  /** Returns true if field event is set (has been assigned a value) and false otherwise */
  public boolean isSetEvent() {
    return this.event != null;
  }

  public void setEventIsSet(boolean value) {
    if (!value) {
      this.event = null;
    }
  }

  public java.lang.String getMessageId() {
    return this.messageId;
  }

  public void setMessageId(java.lang.String messageId) {
    this.messageId = messageId;
  }

  public void unsetMessageId() {
    this.messageId = null;
  }

  /** Returns true if field messageId is set (has been assigned a value) and false otherwise */
  public boolean isSetMessageId() {
    return this.messageId != null;
  }

  public void setMessageIdIsSet(boolean value) {
    if (!value) {
      this.messageId = null;
    }
  }

  /**
   * 
   * @see MessageType
   */
  public MessageType getMessageType() {
    return this.messageType;
  }

  /**
   * 
   * @see MessageType
   */
  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }

  public void unsetMessageType() {
    this.messageType = null;
  }

  /** Returns true if field messageType is set (has been assigned a value) and false otherwise */
  public boolean isSetMessageType() {
    return this.messageType != null;
  }

  public void setMessageTypeIsSet(boolean value) {
    if (!value) {
      this.messageType = null;
    }
  }

  public long getUpdatedTime() {
    return this.updatedTime;
  }

  public void setUpdatedTime(long updatedTime) {
    this.updatedTime = updatedTime;
    setUpdatedTimeIsSet(true);
  }

  public void unsetUpdatedTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __UPDATEDTIME_ISSET_ID);
  }

  /** Returns true if field updatedTime is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdatedTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __UPDATEDTIME_ISSET_ID);
  }

  public void setUpdatedTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __UPDATEDTIME_ISSET_ID, value);
  }

  /**
   * 
   * @see MessageLevel
   */
  public MessageLevel getMessageLevel() {
    return this.messageLevel;
  }

  /**
   * 
   * @see MessageLevel
   */
  public void setMessageLevel(MessageLevel messageLevel) {
    this.messageLevel = messageLevel;
  }

  public void unsetMessageLevel() {
    this.messageLevel = null;
  }

  /** Returns true if field messageLevel is set (has been assigned a value) and false otherwise */
  public boolean isSetMessageLevel() {
    return this.messageLevel != null;
  }

  public void setMessageLevelIsSet(boolean value) {
    if (!value) {
      this.messageLevel = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case EVENT:
      if (value == null) {
        unsetEvent();
      } else {
        if (value instanceof byte[]) {
          setEvent((byte[])value);
        } else {
          setEvent((java.nio.ByteBuffer)value);
        }
      }
      break;

    case MESSAGE_ID:
      if (value == null) {
        unsetMessageId();
      } else {
        setMessageId((java.lang.String)value);
      }
      break;

    case MESSAGE_TYPE:
      if (value == null) {
        unsetMessageType();
      } else {
        setMessageType((MessageType)value);
      }
      break;

    case UPDATED_TIME:
      if (value == null) {
        unsetUpdatedTime();
      } else {
        setUpdatedTime((java.lang.Long)value);
      }
      break;

    case MESSAGE_LEVEL:
      if (value == null) {
        unsetMessageLevel();
      } else {
        setMessageLevel((MessageLevel)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case EVENT:
      return getEvent();

    case MESSAGE_ID:
      return getMessageId();

    case MESSAGE_TYPE:
      return getMessageType();

    case UPDATED_TIME:
      return getUpdatedTime();

    case MESSAGE_LEVEL:
      return getMessageLevel();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case EVENT:
      return isSetEvent();
    case MESSAGE_ID:
      return isSetMessageId();
    case MESSAGE_TYPE:
      return isSetMessageType();
    case UPDATED_TIME:
      return isSetUpdatedTime();
    case MESSAGE_LEVEL:
      return isSetMessageLevel();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof Message)
      return this.equals((Message)that);
    return false;
  }

  public boolean equals(Message that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_event = true && this.isSetEvent();
    boolean that_present_event = true && that.isSetEvent();
    if (this_present_event || that_present_event) {
      if (!(this_present_event && that_present_event))
        return false;
      if (!this.event.equals(that.event))
        return false;
    }

    boolean this_present_messageId = true && this.isSetMessageId();
    boolean that_present_messageId = true && that.isSetMessageId();
    if (this_present_messageId || that_present_messageId) {
      if (!(this_present_messageId && that_present_messageId))
        return false;
      if (!this.messageId.equals(that.messageId))
        return false;
    }

    boolean this_present_messageType = true && this.isSetMessageType();
    boolean that_present_messageType = true && that.isSetMessageType();
    if (this_present_messageType || that_present_messageType) {
      if (!(this_present_messageType && that_present_messageType))
        return false;
      if (!this.messageType.equals(that.messageType))
        return false;
    }

    boolean this_present_updatedTime = true && this.isSetUpdatedTime();
    boolean that_present_updatedTime = true && that.isSetUpdatedTime();
    if (this_present_updatedTime || that_present_updatedTime) {
      if (!(this_present_updatedTime && that_present_updatedTime))
        return false;
      if (this.updatedTime != that.updatedTime)
        return false;
    }

    boolean this_present_messageLevel = true && this.isSetMessageLevel();
    boolean that_present_messageLevel = true && that.isSetMessageLevel();
    if (this_present_messageLevel || that_present_messageLevel) {
      if (!(this_present_messageLevel && that_present_messageLevel))
        return false;
      if (!this.messageLevel.equals(that.messageLevel))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetEvent()) ? 131071 : 524287);
    if (isSetEvent())
      hashCode = hashCode * 8191 + event.hashCode();

    hashCode = hashCode * 8191 + ((isSetMessageId()) ? 131071 : 524287);
    if (isSetMessageId())
      hashCode = hashCode * 8191 + messageId.hashCode();

    hashCode = hashCode * 8191 + ((isSetMessageType()) ? 131071 : 524287);
    if (isSetMessageType())
      hashCode = hashCode * 8191 + messageType.getValue();

    hashCode = hashCode * 8191 + ((isSetUpdatedTime()) ? 131071 : 524287);
    if (isSetUpdatedTime())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(updatedTime);

    hashCode = hashCode * 8191 + ((isSetMessageLevel()) ? 131071 : 524287);
    if (isSetMessageLevel())
      hashCode = hashCode * 8191 + messageLevel.getValue();

    return hashCode;
  }

  @Override
  public int compareTo(Message other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetEvent()).compareTo(other.isSetEvent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEvent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.event, other.event);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMessageId()).compareTo(other.isSetMessageId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessageId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageId, other.messageId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMessageType()).compareTo(other.isSetMessageType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessageType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageType, other.messageType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUpdatedTime()).compareTo(other.isSetUpdatedTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdatedTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.updatedTime, other.updatedTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMessageLevel()).compareTo(other.isSetMessageLevel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessageLevel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageLevel, other.messageLevel);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Message(");
    boolean first = true;

    sb.append("event:");
    if (this.event == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.event, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("messageId:");
    if (this.messageId == null) {
      sb.append("null");
    } else {
      sb.append(this.messageId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("messageType:");
    if (this.messageType == null) {
      sb.append("null");
    } else {
      sb.append(this.messageType);
    }
    first = false;
    if (isSetUpdatedTime()) {
      if (!first) sb.append(", ");
      sb.append("updatedTime:");
      sb.append(this.updatedTime);
      first = false;
    }
    if (isSetMessageLevel()) {
      if (!first) sb.append(", ");
      sb.append("messageLevel:");
      if (this.messageLevel == null) {
        sb.append("null");
      } else {
        sb.append(this.messageLevel);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetEvent()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'event' is unset! Struct:" + toString());
    }

    if (!isSetMessageId()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'messageId' is unset! Struct:" + toString());
    }

    if (!isSetMessageType()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'messageType' is unset! Struct:" + toString());
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

  private static class MessageStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MessageStandardScheme getScheme() {
      return new MessageStandardScheme();
    }
  }

  private static class MessageStandardScheme extends org.apache.thrift.scheme.StandardScheme<Message> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Message struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // EVENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.event = iprot.readBinary();
              struct.setEventIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MESSAGE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.messageId = iprot.readString();
              struct.setMessageIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MESSAGE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.messageType = org.apache.airavata.model.messaging.event.MessageType.findByValue(iprot.readI32());
              struct.setMessageTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // UPDATED_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.updatedTime = iprot.readI64();
              struct.setUpdatedTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MESSAGE_LEVEL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.messageLevel = org.apache.airavata.model.messaging.event.MessageLevel.findByValue(iprot.readI32());
              struct.setMessageLevelIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Message struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.event != null) {
        oprot.writeFieldBegin(EVENT_FIELD_DESC);
        oprot.writeBinary(struct.event);
        oprot.writeFieldEnd();
      }
      if (struct.messageId != null) {
        oprot.writeFieldBegin(MESSAGE_ID_FIELD_DESC);
        oprot.writeString(struct.messageId);
        oprot.writeFieldEnd();
      }
      if (struct.messageType != null) {
        oprot.writeFieldBegin(MESSAGE_TYPE_FIELD_DESC);
        oprot.writeI32(struct.messageType.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.isSetUpdatedTime()) {
        oprot.writeFieldBegin(UPDATED_TIME_FIELD_DESC);
        oprot.writeI64(struct.updatedTime);
        oprot.writeFieldEnd();
      }
      if (struct.messageLevel != null) {
        if (struct.isSetMessageLevel()) {
          oprot.writeFieldBegin(MESSAGE_LEVEL_FIELD_DESC);
          oprot.writeI32(struct.messageLevel.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MessageTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MessageTupleScheme getScheme() {
      return new MessageTupleScheme();
    }
  }

  private static class MessageTupleScheme extends org.apache.thrift.scheme.TupleScheme<Message> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Message struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeBinary(struct.event);
      oprot.writeString(struct.messageId);
      oprot.writeI32(struct.messageType.getValue());
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetUpdatedTime()) {
        optionals.set(0);
      }
      if (struct.isSetMessageLevel()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetUpdatedTime()) {
        oprot.writeI64(struct.updatedTime);
      }
      if (struct.isSetMessageLevel()) {
        oprot.writeI32(struct.messageLevel.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Message struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.event = iprot.readBinary();
      struct.setEventIsSet(true);
      struct.messageId = iprot.readString();
      struct.setMessageIdIsSet(true);
      struct.messageType = org.apache.airavata.model.messaging.event.MessageType.findByValue(iprot.readI32());
      struct.setMessageTypeIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.updatedTime = iprot.readI64();
        struct.setUpdatedTimeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.messageLevel = org.apache.airavata.model.messaging.event.MessageLevel.findByValue(iprot.readI32());
        struct.setMessageLevelIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

