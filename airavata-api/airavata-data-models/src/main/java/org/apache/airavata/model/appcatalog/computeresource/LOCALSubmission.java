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
package org.apache.airavata.model.appcatalog.computeresource;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * Locally Fork Jobs as OS processes
 * 
 * alternativeSSHHostName:
 *  If the login to ssh is different than the hostname itself, specify it here
 * 
 * sshPort:
 *  If a non-default port needs to used, specify it.
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)")
public class LOCALSubmission implements org.apache.thrift.TBase<LOCALSubmission, LOCALSubmission._Fields>, java.io.Serializable, Cloneable, Comparable<LOCALSubmission> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LOCALSubmission");

  private static final org.apache.thrift.protocol.TField JOB_SUBMISSION_INTERFACE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("jobSubmissionInterfaceId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RESOURCE_JOB_MANAGER_FIELD_DESC = new org.apache.thrift.protocol.TField("resourceJobManager", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField SECURITY_PROTOCOL_FIELD_DESC = new org.apache.thrift.protocol.TField("securityProtocol", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new LOCALSubmissionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new LOCALSubmissionTupleSchemeFactory();

  private java.lang.String jobSubmissionInterfaceId; // required
  private ResourceJobManager resourceJobManager; // required
  private org.apache.airavata.model.data.movement.SecurityProtocol securityProtocol; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    JOB_SUBMISSION_INTERFACE_ID((short)1, "jobSubmissionInterfaceId"),
    RESOURCE_JOB_MANAGER((short)2, "resourceJobManager"),
    /**
     * 
     * @see org.apache.airavata.model.data.movement.SecurityProtocol
     */
    SECURITY_PROTOCOL((short)3, "securityProtocol");

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
        case 1: // JOB_SUBMISSION_INTERFACE_ID
          return JOB_SUBMISSION_INTERFACE_ID;
        case 2: // RESOURCE_JOB_MANAGER
          return RESOURCE_JOB_MANAGER;
        case 3: // SECURITY_PROTOCOL
          return SECURITY_PROTOCOL;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.JOB_SUBMISSION_INTERFACE_ID, new org.apache.thrift.meta_data.FieldMetaData("jobSubmissionInterfaceId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RESOURCE_JOB_MANAGER, new org.apache.thrift.meta_data.FieldMetaData("resourceJobManager", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ResourceJobManager.class)));
    tmpMap.put(_Fields.SECURITY_PROTOCOL, new org.apache.thrift.meta_data.FieldMetaData("securityProtocol", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, org.apache.airavata.model.data.movement.SecurityProtocol.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LOCALSubmission.class, metaDataMap);
  }

  public LOCALSubmission() {
    this.jobSubmissionInterfaceId = "DO_NOT_SET_AT_CLIENTS";

  }

  public LOCALSubmission(
    java.lang.String jobSubmissionInterfaceId,
    ResourceJobManager resourceJobManager,
    org.apache.airavata.model.data.movement.SecurityProtocol securityProtocol)
  {
    this();
    this.jobSubmissionInterfaceId = jobSubmissionInterfaceId;
    this.resourceJobManager = resourceJobManager;
    this.securityProtocol = securityProtocol;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LOCALSubmission(LOCALSubmission other) {
    if (other.isSetJobSubmissionInterfaceId()) {
      this.jobSubmissionInterfaceId = other.jobSubmissionInterfaceId;
    }
    if (other.isSetResourceJobManager()) {
      this.resourceJobManager = new ResourceJobManager(other.resourceJobManager);
    }
    if (other.isSetSecurityProtocol()) {
      this.securityProtocol = other.securityProtocol;
    }
  }

  public LOCALSubmission deepCopy() {
    return new LOCALSubmission(this);
  }

  @Override
  public void clear() {
    this.jobSubmissionInterfaceId = "DO_NOT_SET_AT_CLIENTS";

    this.resourceJobManager = null;
    this.securityProtocol = null;
  }

  public java.lang.String getJobSubmissionInterfaceId() {
    return this.jobSubmissionInterfaceId;
  }

  public void setJobSubmissionInterfaceId(java.lang.String jobSubmissionInterfaceId) {
    this.jobSubmissionInterfaceId = jobSubmissionInterfaceId;
  }

  public void unsetJobSubmissionInterfaceId() {
    this.jobSubmissionInterfaceId = null;
  }

  /** Returns true if field jobSubmissionInterfaceId is set (has been assigned a value) and false otherwise */
  public boolean isSetJobSubmissionInterfaceId() {
    return this.jobSubmissionInterfaceId != null;
  }

  public void setJobSubmissionInterfaceIdIsSet(boolean value) {
    if (!value) {
      this.jobSubmissionInterfaceId = null;
    }
  }

  public ResourceJobManager getResourceJobManager() {
    return this.resourceJobManager;
  }

  public void setResourceJobManager(ResourceJobManager resourceJobManager) {
    this.resourceJobManager = resourceJobManager;
  }

  public void unsetResourceJobManager() {
    this.resourceJobManager = null;
  }

  /** Returns true if field resourceJobManager is set (has been assigned a value) and false otherwise */
  public boolean isSetResourceJobManager() {
    return this.resourceJobManager != null;
  }

  public void setResourceJobManagerIsSet(boolean value) {
    if (!value) {
      this.resourceJobManager = null;
    }
  }

  /**
   * 
   * @see org.apache.airavata.model.data.movement.SecurityProtocol
   */
  public org.apache.airavata.model.data.movement.SecurityProtocol getSecurityProtocol() {
    return this.securityProtocol;
  }

  /**
   * 
   * @see org.apache.airavata.model.data.movement.SecurityProtocol
   */
  public void setSecurityProtocol(org.apache.airavata.model.data.movement.SecurityProtocol securityProtocol) {
    this.securityProtocol = securityProtocol;
  }

  public void unsetSecurityProtocol() {
    this.securityProtocol = null;
  }

  /** Returns true if field securityProtocol is set (has been assigned a value) and false otherwise */
  public boolean isSetSecurityProtocol() {
    return this.securityProtocol != null;
  }

  public void setSecurityProtocolIsSet(boolean value) {
    if (!value) {
      this.securityProtocol = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case JOB_SUBMISSION_INTERFACE_ID:
      if (value == null) {
        unsetJobSubmissionInterfaceId();
      } else {
        setJobSubmissionInterfaceId((java.lang.String)value);
      }
      break;

    case RESOURCE_JOB_MANAGER:
      if (value == null) {
        unsetResourceJobManager();
      } else {
        setResourceJobManager((ResourceJobManager)value);
      }
      break;

    case SECURITY_PROTOCOL:
      if (value == null) {
        unsetSecurityProtocol();
      } else {
        setSecurityProtocol((org.apache.airavata.model.data.movement.SecurityProtocol)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case JOB_SUBMISSION_INTERFACE_ID:
      return getJobSubmissionInterfaceId();

    case RESOURCE_JOB_MANAGER:
      return getResourceJobManager();

    case SECURITY_PROTOCOL:
      return getSecurityProtocol();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case JOB_SUBMISSION_INTERFACE_ID:
      return isSetJobSubmissionInterfaceId();
    case RESOURCE_JOB_MANAGER:
      return isSetResourceJobManager();
    case SECURITY_PROTOCOL:
      return isSetSecurityProtocol();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof LOCALSubmission)
      return this.equals((LOCALSubmission)that);
    return false;
  }

  public boolean equals(LOCALSubmission that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_jobSubmissionInterfaceId = true && this.isSetJobSubmissionInterfaceId();
    boolean that_present_jobSubmissionInterfaceId = true && that.isSetJobSubmissionInterfaceId();
    if (this_present_jobSubmissionInterfaceId || that_present_jobSubmissionInterfaceId) {
      if (!(this_present_jobSubmissionInterfaceId && that_present_jobSubmissionInterfaceId))
        return false;
      if (!this.jobSubmissionInterfaceId.equals(that.jobSubmissionInterfaceId))
        return false;
    }

    boolean this_present_resourceJobManager = true && this.isSetResourceJobManager();
    boolean that_present_resourceJobManager = true && that.isSetResourceJobManager();
    if (this_present_resourceJobManager || that_present_resourceJobManager) {
      if (!(this_present_resourceJobManager && that_present_resourceJobManager))
        return false;
      if (!this.resourceJobManager.equals(that.resourceJobManager))
        return false;
    }

    boolean this_present_securityProtocol = true && this.isSetSecurityProtocol();
    boolean that_present_securityProtocol = true && that.isSetSecurityProtocol();
    if (this_present_securityProtocol || that_present_securityProtocol) {
      if (!(this_present_securityProtocol && that_present_securityProtocol))
        return false;
      if (!this.securityProtocol.equals(that.securityProtocol))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetJobSubmissionInterfaceId()) ? 131071 : 524287);
    if (isSetJobSubmissionInterfaceId())
      hashCode = hashCode * 8191 + jobSubmissionInterfaceId.hashCode();

    hashCode = hashCode * 8191 + ((isSetResourceJobManager()) ? 131071 : 524287);
    if (isSetResourceJobManager())
      hashCode = hashCode * 8191 + resourceJobManager.hashCode();

    hashCode = hashCode * 8191 + ((isSetSecurityProtocol()) ? 131071 : 524287);
    if (isSetSecurityProtocol())
      hashCode = hashCode * 8191 + securityProtocol.getValue();

    return hashCode;
  }

  @Override
  public int compareTo(LOCALSubmission other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetJobSubmissionInterfaceId()).compareTo(other.isSetJobSubmissionInterfaceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJobSubmissionInterfaceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobSubmissionInterfaceId, other.jobSubmissionInterfaceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetResourceJobManager()).compareTo(other.isSetResourceJobManager());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResourceJobManager()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resourceJobManager, other.resourceJobManager);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSecurityProtocol()).compareTo(other.isSetSecurityProtocol());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSecurityProtocol()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.securityProtocol, other.securityProtocol);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("LOCALSubmission(");
    boolean first = true;

    sb.append("jobSubmissionInterfaceId:");
    if (this.jobSubmissionInterfaceId == null) {
      sb.append("null");
    } else {
      sb.append(this.jobSubmissionInterfaceId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("resourceJobManager:");
    if (this.resourceJobManager == null) {
      sb.append("null");
    } else {
      sb.append(this.resourceJobManager);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("securityProtocol:");
    if (this.securityProtocol == null) {
      sb.append("null");
    } else {
      sb.append(this.securityProtocol);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetJobSubmissionInterfaceId()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'jobSubmissionInterfaceId' is unset! Struct:" + toString());
    }

    if (!isSetResourceJobManager()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'resourceJobManager' is unset! Struct:" + toString());
    }

    if (!isSetSecurityProtocol()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'securityProtocol' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
    if (resourceJobManager != null) {
      resourceJobManager.validate();
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class LOCALSubmissionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LOCALSubmissionStandardScheme getScheme() {
      return new LOCALSubmissionStandardScheme();
    }
  }

  private static class LOCALSubmissionStandardScheme extends org.apache.thrift.scheme.StandardScheme<LOCALSubmission> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LOCALSubmission struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // JOB_SUBMISSION_INTERFACE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.jobSubmissionInterfaceId = iprot.readString();
              struct.setJobSubmissionInterfaceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RESOURCE_JOB_MANAGER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.resourceJobManager = new ResourceJobManager();
              struct.resourceJobManager.read(iprot);
              struct.setResourceJobManagerIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SECURITY_PROTOCOL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.securityProtocol = org.apache.airavata.model.data.movement.SecurityProtocol.findByValue(iprot.readI32());
              struct.setSecurityProtocolIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LOCALSubmission struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.jobSubmissionInterfaceId != null) {
        oprot.writeFieldBegin(JOB_SUBMISSION_INTERFACE_ID_FIELD_DESC);
        oprot.writeString(struct.jobSubmissionInterfaceId);
        oprot.writeFieldEnd();
      }
      if (struct.resourceJobManager != null) {
        oprot.writeFieldBegin(RESOURCE_JOB_MANAGER_FIELD_DESC);
        struct.resourceJobManager.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.securityProtocol != null) {
        oprot.writeFieldBegin(SECURITY_PROTOCOL_FIELD_DESC);
        oprot.writeI32(struct.securityProtocol.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LOCALSubmissionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LOCALSubmissionTupleScheme getScheme() {
      return new LOCALSubmissionTupleScheme();
    }
  }

  private static class LOCALSubmissionTupleScheme extends org.apache.thrift.scheme.TupleScheme<LOCALSubmission> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LOCALSubmission struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.jobSubmissionInterfaceId);
      struct.resourceJobManager.write(oprot);
      oprot.writeI32(struct.securityProtocol.getValue());
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LOCALSubmission struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.jobSubmissionInterfaceId = iprot.readString();
      struct.setJobSubmissionInterfaceIdIsSet(true);
      struct.resourceJobManager = new ResourceJobManager();
      struct.resourceJobManager.read(iprot);
      struct.setResourceJobManagerIsSet(true);
      struct.securityProtocol = org.apache.airavata.model.data.movement.SecurityProtocol.findByValue(iprot.readI32());
      struct.setSecurityProtocolIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

