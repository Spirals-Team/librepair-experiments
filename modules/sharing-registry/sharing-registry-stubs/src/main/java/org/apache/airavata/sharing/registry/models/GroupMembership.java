/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.airavata.sharing.registry.models;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * <p>System internal data type to map group memberships</p>
 * 
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)")
public class GroupMembership implements org.apache.thrift.TBase<GroupMembership, GroupMembership._Fields>, java.io.Serializable, Cloneable, Comparable<GroupMembership> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GroupMembership");

  private static final org.apache.thrift.protocol.TField PARENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("parentId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CHILD_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("childId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField DOMAIN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("domainId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CHILD_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("childType", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField CREATED_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createdTime", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField UPDATED_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("updatedTime", org.apache.thrift.protocol.TType.I64, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GroupMembershipStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GroupMembershipTupleSchemeFactory();

  public java.lang.String parentId; // optional
  public java.lang.String childId; // optional
  public java.lang.String domainId; // optional
  /**
   * 
   * @see GroupChildType
   */
  public GroupChildType childType; // optional
  public long createdTime; // optional
  public long updatedTime; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PARENT_ID((short)1, "parentId"),
    CHILD_ID((short)2, "childId"),
    DOMAIN_ID((short)3, "domainId"),
    /**
     * 
     * @see GroupChildType
     */
    CHILD_TYPE((short)4, "childType"),
    CREATED_TIME((short)5, "createdTime"),
    UPDATED_TIME((short)6, "updatedTime");

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
        case 1: // PARENT_ID
          return PARENT_ID;
        case 2: // CHILD_ID
          return CHILD_ID;
        case 3: // DOMAIN_ID
          return DOMAIN_ID;
        case 4: // CHILD_TYPE
          return CHILD_TYPE;
        case 5: // CREATED_TIME
          return CREATED_TIME;
        case 6: // UPDATED_TIME
          return UPDATED_TIME;
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
  private static final int __CREATEDTIME_ISSET_ID = 0;
  private static final int __UPDATEDTIME_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PARENT_ID,_Fields.CHILD_ID,_Fields.DOMAIN_ID,_Fields.CHILD_TYPE,_Fields.CREATED_TIME,_Fields.UPDATED_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PARENT_ID, new org.apache.thrift.meta_data.FieldMetaData("parentId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHILD_ID, new org.apache.thrift.meta_data.FieldMetaData("childId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DOMAIN_ID, new org.apache.thrift.meta_data.FieldMetaData("domainId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHILD_TYPE, new org.apache.thrift.meta_data.FieldMetaData("childType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, GroupChildType.class)));
    tmpMap.put(_Fields.CREATED_TIME, new org.apache.thrift.meta_data.FieldMetaData("createdTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.UPDATED_TIME, new org.apache.thrift.meta_data.FieldMetaData("updatedTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GroupMembership.class, metaDataMap);
  }

  public GroupMembership() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GroupMembership(GroupMembership other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetParentId()) {
      this.parentId = other.parentId;
    }
    if (other.isSetChildId()) {
      this.childId = other.childId;
    }
    if (other.isSetDomainId()) {
      this.domainId = other.domainId;
    }
    if (other.isSetChildType()) {
      this.childType = other.childType;
    }
    this.createdTime = other.createdTime;
    this.updatedTime = other.updatedTime;
  }

  public GroupMembership deepCopy() {
    return new GroupMembership(this);
  }

  @Override
  public void clear() {
    this.parentId = null;
    this.childId = null;
    this.domainId = null;
    this.childType = null;
    setCreatedTimeIsSet(false);
    this.createdTime = 0;
    setUpdatedTimeIsSet(false);
    this.updatedTime = 0;
  }

  public java.lang.String getParentId() {
    return this.parentId;
  }

  public GroupMembership setParentId(java.lang.String parentId) {
    this.parentId = parentId;
    return this;
  }

  public void unsetParentId() {
    this.parentId = null;
  }

  /** Returns true if field parentId is set (has been assigned a value) and false otherwise */
  public boolean isSetParentId() {
    return this.parentId != null;
  }

  public void setParentIdIsSet(boolean value) {
    if (!value) {
      this.parentId = null;
    }
  }

  public java.lang.String getChildId() {
    return this.childId;
  }

  public GroupMembership setChildId(java.lang.String childId) {
    this.childId = childId;
    return this;
  }

  public void unsetChildId() {
    this.childId = null;
  }

  /** Returns true if field childId is set (has been assigned a value) and false otherwise */
  public boolean isSetChildId() {
    return this.childId != null;
  }

  public void setChildIdIsSet(boolean value) {
    if (!value) {
      this.childId = null;
    }
  }

  public java.lang.String getDomainId() {
    return this.domainId;
  }

  public GroupMembership setDomainId(java.lang.String domainId) {
    this.domainId = domainId;
    return this;
  }

  public void unsetDomainId() {
    this.domainId = null;
  }

  /** Returns true if field domainId is set (has been assigned a value) and false otherwise */
  public boolean isSetDomainId() {
    return this.domainId != null;
  }

  public void setDomainIdIsSet(boolean value) {
    if (!value) {
      this.domainId = null;
    }
  }

  /**
   * 
   * @see GroupChildType
   */
  public GroupChildType getChildType() {
    return this.childType;
  }

  /**
   * 
   * @see GroupChildType
   */
  public GroupMembership setChildType(GroupChildType childType) {
    this.childType = childType;
    return this;
  }

  public void unsetChildType() {
    this.childType = null;
  }

  /** Returns true if field childType is set (has been assigned a value) and false otherwise */
  public boolean isSetChildType() {
    return this.childType != null;
  }

  public void setChildTypeIsSet(boolean value) {
    if (!value) {
      this.childType = null;
    }
  }

  public long getCreatedTime() {
    return this.createdTime;
  }

  public GroupMembership setCreatedTime(long createdTime) {
    this.createdTime = createdTime;
    setCreatedTimeIsSet(true);
    return this;
  }

  public void unsetCreatedTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CREATEDTIME_ISSET_ID);
  }

  /** Returns true if field createdTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreatedTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CREATEDTIME_ISSET_ID);
  }

  public void setCreatedTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CREATEDTIME_ISSET_ID, value);
  }

  public long getUpdatedTime() {
    return this.updatedTime;
  }

  public GroupMembership setUpdatedTime(long updatedTime) {
    this.updatedTime = updatedTime;
    setUpdatedTimeIsSet(true);
    return this;
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

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PARENT_ID:
      if (value == null) {
        unsetParentId();
      } else {
        setParentId((java.lang.String)value);
      }
      break;

    case CHILD_ID:
      if (value == null) {
        unsetChildId();
      } else {
        setChildId((java.lang.String)value);
      }
      break;

    case DOMAIN_ID:
      if (value == null) {
        unsetDomainId();
      } else {
        setDomainId((java.lang.String)value);
      }
      break;

    case CHILD_TYPE:
      if (value == null) {
        unsetChildType();
      } else {
        setChildType((GroupChildType)value);
      }
      break;

    case CREATED_TIME:
      if (value == null) {
        unsetCreatedTime();
      } else {
        setCreatedTime((java.lang.Long)value);
      }
      break;

    case UPDATED_TIME:
      if (value == null) {
        unsetUpdatedTime();
      } else {
        setUpdatedTime((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PARENT_ID:
      return getParentId();

    case CHILD_ID:
      return getChildId();

    case DOMAIN_ID:
      return getDomainId();

    case CHILD_TYPE:
      return getChildType();

    case CREATED_TIME:
      return getCreatedTime();

    case UPDATED_TIME:
      return getUpdatedTime();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PARENT_ID:
      return isSetParentId();
    case CHILD_ID:
      return isSetChildId();
    case DOMAIN_ID:
      return isSetDomainId();
    case CHILD_TYPE:
      return isSetChildType();
    case CREATED_TIME:
      return isSetCreatedTime();
    case UPDATED_TIME:
      return isSetUpdatedTime();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof GroupMembership)
      return this.equals((GroupMembership)that);
    return false;
  }

  public boolean equals(GroupMembership that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_parentId = true && this.isSetParentId();
    boolean that_present_parentId = true && that.isSetParentId();
    if (this_present_parentId || that_present_parentId) {
      if (!(this_present_parentId && that_present_parentId))
        return false;
      if (!this.parentId.equals(that.parentId))
        return false;
    }

    boolean this_present_childId = true && this.isSetChildId();
    boolean that_present_childId = true && that.isSetChildId();
    if (this_present_childId || that_present_childId) {
      if (!(this_present_childId && that_present_childId))
        return false;
      if (!this.childId.equals(that.childId))
        return false;
    }

    boolean this_present_domainId = true && this.isSetDomainId();
    boolean that_present_domainId = true && that.isSetDomainId();
    if (this_present_domainId || that_present_domainId) {
      if (!(this_present_domainId && that_present_domainId))
        return false;
      if (!this.domainId.equals(that.domainId))
        return false;
    }

    boolean this_present_childType = true && this.isSetChildType();
    boolean that_present_childType = true && that.isSetChildType();
    if (this_present_childType || that_present_childType) {
      if (!(this_present_childType && that_present_childType))
        return false;
      if (!this.childType.equals(that.childType))
        return false;
    }

    boolean this_present_createdTime = true && this.isSetCreatedTime();
    boolean that_present_createdTime = true && that.isSetCreatedTime();
    if (this_present_createdTime || that_present_createdTime) {
      if (!(this_present_createdTime && that_present_createdTime))
        return false;
      if (this.createdTime != that.createdTime)
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

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetParentId()) ? 131071 : 524287);
    if (isSetParentId())
      hashCode = hashCode * 8191 + parentId.hashCode();

    hashCode = hashCode * 8191 + ((isSetChildId()) ? 131071 : 524287);
    if (isSetChildId())
      hashCode = hashCode * 8191 + childId.hashCode();

    hashCode = hashCode * 8191 + ((isSetDomainId()) ? 131071 : 524287);
    if (isSetDomainId())
      hashCode = hashCode * 8191 + domainId.hashCode();

    hashCode = hashCode * 8191 + ((isSetChildType()) ? 131071 : 524287);
    if (isSetChildType())
      hashCode = hashCode * 8191 + childType.getValue();

    hashCode = hashCode * 8191 + ((isSetCreatedTime()) ? 131071 : 524287);
    if (isSetCreatedTime())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(createdTime);

    hashCode = hashCode * 8191 + ((isSetUpdatedTime()) ? 131071 : 524287);
    if (isSetUpdatedTime())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(updatedTime);

    return hashCode;
  }

  @Override
  public int compareTo(GroupMembership other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetParentId()).compareTo(other.isSetParentId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParentId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parentId, other.parentId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetChildId()).compareTo(other.isSetChildId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChildId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.childId, other.childId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDomainId()).compareTo(other.isSetDomainId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDomainId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.domainId, other.domainId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetChildType()).compareTo(other.isSetChildType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChildType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.childType, other.childType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCreatedTime()).compareTo(other.isSetCreatedTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreatedTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createdTime, other.createdTime);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GroupMembership(");
    boolean first = true;

    if (isSetParentId()) {
      sb.append("parentId:");
      if (this.parentId == null) {
        sb.append("null");
      } else {
        sb.append(this.parentId);
      }
      first = false;
    }
    if (isSetChildId()) {
      if (!first) sb.append(", ");
      sb.append("childId:");
      if (this.childId == null) {
        sb.append("null");
      } else {
        sb.append(this.childId);
      }
      first = false;
    }
    if (isSetDomainId()) {
      if (!first) sb.append(", ");
      sb.append("domainId:");
      if (this.domainId == null) {
        sb.append("null");
      } else {
        sb.append(this.domainId);
      }
      first = false;
    }
    if (isSetChildType()) {
      if (!first) sb.append(", ");
      sb.append("childType:");
      if (this.childType == null) {
        sb.append("null");
      } else {
        sb.append(this.childType);
      }
      first = false;
    }
    if (isSetCreatedTime()) {
      if (!first) sb.append(", ");
      sb.append("createdTime:");
      sb.append(this.createdTime);
      first = false;
    }
    if (isSetUpdatedTime()) {
      if (!first) sb.append(", ");
      sb.append("updatedTime:");
      sb.append(this.updatedTime);
      first = false;
    }
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class GroupMembershipStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GroupMembershipStandardScheme getScheme() {
      return new GroupMembershipStandardScheme();
    }
  }

  private static class GroupMembershipStandardScheme extends org.apache.thrift.scheme.StandardScheme<GroupMembership> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GroupMembership struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PARENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.parentId = iprot.readString();
              struct.setParentIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CHILD_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.childId = iprot.readString();
              struct.setChildIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DOMAIN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.domainId = iprot.readString();
              struct.setDomainIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CHILD_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.childType = org.apache.airavata.sharing.registry.models.GroupChildType.findByValue(iprot.readI32());
              struct.setChildTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATED_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createdTime = iprot.readI64();
              struct.setCreatedTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // UPDATED_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.updatedTime = iprot.readI64();
              struct.setUpdatedTimeIsSet(true);
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

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, GroupMembership struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.parentId != null) {
        if (struct.isSetParentId()) {
          oprot.writeFieldBegin(PARENT_ID_FIELD_DESC);
          oprot.writeString(struct.parentId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.childId != null) {
        if (struct.isSetChildId()) {
          oprot.writeFieldBegin(CHILD_ID_FIELD_DESC);
          oprot.writeString(struct.childId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.domainId != null) {
        if (struct.isSetDomainId()) {
          oprot.writeFieldBegin(DOMAIN_ID_FIELD_DESC);
          oprot.writeString(struct.domainId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.childType != null) {
        if (struct.isSetChildType()) {
          oprot.writeFieldBegin(CHILD_TYPE_FIELD_DESC);
          oprot.writeI32(struct.childType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetCreatedTime()) {
        oprot.writeFieldBegin(CREATED_TIME_FIELD_DESC);
        oprot.writeI64(struct.createdTime);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUpdatedTime()) {
        oprot.writeFieldBegin(UPDATED_TIME_FIELD_DESC);
        oprot.writeI64(struct.updatedTime);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GroupMembershipTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GroupMembershipTupleScheme getScheme() {
      return new GroupMembershipTupleScheme();
    }
  }

  private static class GroupMembershipTupleScheme extends org.apache.thrift.scheme.TupleScheme<GroupMembership> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GroupMembership struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetParentId()) {
        optionals.set(0);
      }
      if (struct.isSetChildId()) {
        optionals.set(1);
      }
      if (struct.isSetDomainId()) {
        optionals.set(2);
      }
      if (struct.isSetChildType()) {
        optionals.set(3);
      }
      if (struct.isSetCreatedTime()) {
        optionals.set(4);
      }
      if (struct.isSetUpdatedTime()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetParentId()) {
        oprot.writeString(struct.parentId);
      }
      if (struct.isSetChildId()) {
        oprot.writeString(struct.childId);
      }
      if (struct.isSetDomainId()) {
        oprot.writeString(struct.domainId);
      }
      if (struct.isSetChildType()) {
        oprot.writeI32(struct.childType.getValue());
      }
      if (struct.isSetCreatedTime()) {
        oprot.writeI64(struct.createdTime);
      }
      if (struct.isSetUpdatedTime()) {
        oprot.writeI64(struct.updatedTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GroupMembership struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.parentId = iprot.readString();
        struct.setParentIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.childId = iprot.readString();
        struct.setChildIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.domainId = iprot.readString();
        struct.setDomainIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.childType = org.apache.airavata.sharing.registry.models.GroupChildType.findByValue(iprot.readI32());
        struct.setChildTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createdTime = iprot.readI64();
        struct.setCreatedTimeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.updatedTime = iprot.readI64();
        struct.setUpdatedTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

