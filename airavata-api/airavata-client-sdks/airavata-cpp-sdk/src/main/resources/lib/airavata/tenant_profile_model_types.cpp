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
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#include "tenant_profile_model_types.h"

#include <algorithm>
#include <ostream>

#include <thrift/TToString.h>

namespace apache { namespace airavata { namespace model { namespace tenant {

int _kTenantApprovalStatusValues[] = {
  TenantApprovalStatus::REQUESTED,
  TenantApprovalStatus::APPROVED,
  TenantApprovalStatus::ACTIVE,
  TenantApprovalStatus::DEACTIVATED,
  TenantApprovalStatus::CANCELLED,
  TenantApprovalStatus::DENIED,
  TenantApprovalStatus::CREATED,
  TenantApprovalStatus::DEPLOYED
};
const char* _kTenantApprovalStatusNames[] = {
  "REQUESTED",
  "APPROVED",
  "ACTIVE",
  "DEACTIVATED",
  "CANCELLED",
  "DENIED",
  "CREATED",
  "DEPLOYED"
};
const std::map<int, const char*> _TenantApprovalStatus_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(8, _kTenantApprovalStatusValues, _kTenantApprovalStatusNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));


TenantPreferences::~TenantPreferences() throw() {
}


void TenantPreferences::__set_tenantAdminFirstName(const std::string& val) {
  this->tenantAdminFirstName = val;
__isset.tenantAdminFirstName = true;
}

void TenantPreferences::__set_tenantAdminLastName(const std::string& val) {
  this->tenantAdminLastName = val;
__isset.tenantAdminLastName = true;
}

void TenantPreferences::__set_tenantAdminEmail(const std::string& val) {
  this->tenantAdminEmail = val;
__isset.tenantAdminEmail = true;
}

uint32_t TenantPreferences::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 10:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->tenantAdminFirstName);
          this->__isset.tenantAdminFirstName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 11:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->tenantAdminLastName);
          this->__isset.tenantAdminLastName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 12:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->tenantAdminEmail);
          this->__isset.tenantAdminEmail = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t TenantPreferences::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("TenantPreferences");

  if (this->__isset.tenantAdminFirstName) {
    xfer += oprot->writeFieldBegin("tenantAdminFirstName", ::apache::thrift::protocol::T_STRING, 10);
    xfer += oprot->writeString(this->tenantAdminFirstName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.tenantAdminLastName) {
    xfer += oprot->writeFieldBegin("tenantAdminLastName", ::apache::thrift::protocol::T_STRING, 11);
    xfer += oprot->writeString(this->tenantAdminLastName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.tenantAdminEmail) {
    xfer += oprot->writeFieldBegin("tenantAdminEmail", ::apache::thrift::protocol::T_STRING, 12);
    xfer += oprot->writeString(this->tenantAdminEmail);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(TenantPreferences &a, TenantPreferences &b) {
  using ::std::swap;
  swap(a.tenantAdminFirstName, b.tenantAdminFirstName);
  swap(a.tenantAdminLastName, b.tenantAdminLastName);
  swap(a.tenantAdminEmail, b.tenantAdminEmail);
  swap(a.__isset, b.__isset);
}

TenantPreferences::TenantPreferences(const TenantPreferences& other0) {
  tenantAdminFirstName = other0.tenantAdminFirstName;
  tenantAdminLastName = other0.tenantAdminLastName;
  tenantAdminEmail = other0.tenantAdminEmail;
  __isset = other0.__isset;
}
TenantPreferences& TenantPreferences::operator=(const TenantPreferences& other1) {
  tenantAdminFirstName = other1.tenantAdminFirstName;
  tenantAdminLastName = other1.tenantAdminLastName;
  tenantAdminEmail = other1.tenantAdminEmail;
  __isset = other1.__isset;
  return *this;
}
void TenantPreferences::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "TenantPreferences(";
  out << "tenantAdminFirstName="; (__isset.tenantAdminFirstName ? (out << to_string(tenantAdminFirstName)) : (out << "<null>"));
  out << ", " << "tenantAdminLastName="; (__isset.tenantAdminLastName ? (out << to_string(tenantAdminLastName)) : (out << "<null>"));
  out << ", " << "tenantAdminEmail="; (__isset.tenantAdminEmail ? (out << to_string(tenantAdminEmail)) : (out << "<null>"));
  out << ")";
}


TenantConfig::~TenantConfig() throw() {
}


void TenantConfig::__set_oauthClientId(const std::string& val) {
  this->oauthClientId = val;
__isset.oauthClientId = true;
}

void TenantConfig::__set_oauthClientSecret(const std::string& val) {
  this->oauthClientSecret = val;
__isset.oauthClientSecret = true;
}

void TenantConfig::__set_identityServerUserName(const std::string& val) {
  this->identityServerUserName = val;
__isset.identityServerUserName = true;
}

void TenantConfig::__set_identityServerPasswordToken(const std::string& val) {
  this->identityServerPasswordToken = val;
__isset.identityServerPasswordToken = true;
}

uint32_t TenantConfig::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 16:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->oauthClientId);
          this->__isset.oauthClientId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 17:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->oauthClientSecret);
          this->__isset.oauthClientSecret = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 13:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->identityServerUserName);
          this->__isset.identityServerUserName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 14:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->identityServerPasswordToken);
          this->__isset.identityServerPasswordToken = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t TenantConfig::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("TenantConfig");

  if (this->__isset.identityServerUserName) {
    xfer += oprot->writeFieldBegin("identityServerUserName", ::apache::thrift::protocol::T_STRING, 13);
    xfer += oprot->writeString(this->identityServerUserName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.identityServerPasswordToken) {
    xfer += oprot->writeFieldBegin("identityServerPasswordToken", ::apache::thrift::protocol::T_STRING, 14);
    xfer += oprot->writeString(this->identityServerPasswordToken);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.oauthClientId) {
    xfer += oprot->writeFieldBegin("oauthClientId", ::apache::thrift::protocol::T_STRING, 16);
    xfer += oprot->writeString(this->oauthClientId);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.oauthClientSecret) {
    xfer += oprot->writeFieldBegin("oauthClientSecret", ::apache::thrift::protocol::T_STRING, 17);
    xfer += oprot->writeString(this->oauthClientSecret);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(TenantConfig &a, TenantConfig &b) {
  using ::std::swap;
  swap(a.oauthClientId, b.oauthClientId);
  swap(a.oauthClientSecret, b.oauthClientSecret);
  swap(a.identityServerUserName, b.identityServerUserName);
  swap(a.identityServerPasswordToken, b.identityServerPasswordToken);
  swap(a.__isset, b.__isset);
}

TenantConfig::TenantConfig(const TenantConfig& other2) {
  oauthClientId = other2.oauthClientId;
  oauthClientSecret = other2.oauthClientSecret;
  identityServerUserName = other2.identityServerUserName;
  identityServerPasswordToken = other2.identityServerPasswordToken;
  __isset = other2.__isset;
}
TenantConfig& TenantConfig::operator=(const TenantConfig& other3) {
  oauthClientId = other3.oauthClientId;
  oauthClientSecret = other3.oauthClientSecret;
  identityServerUserName = other3.identityServerUserName;
  identityServerPasswordToken = other3.identityServerPasswordToken;
  __isset = other3.__isset;
  return *this;
}
void TenantConfig::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "TenantConfig(";
  out << "oauthClientId="; (__isset.oauthClientId ? (out << to_string(oauthClientId)) : (out << "<null>"));
  out << ", " << "oauthClientSecret="; (__isset.oauthClientSecret ? (out << to_string(oauthClientSecret)) : (out << "<null>"));
  out << ", " << "identityServerUserName="; (__isset.identityServerUserName ? (out << to_string(identityServerUserName)) : (out << "<null>"));
  out << ", " << "identityServerPasswordToken="; (__isset.identityServerPasswordToken ? (out << to_string(identityServerPasswordToken)) : (out << "<null>"));
  out << ")";
}


Tenant::~Tenant() throw() {
}


void Tenant::__set_tenantId(const std::string& val) {
  this->tenantId = val;
}

void Tenant::__set_tenantApprovalStatus(const TenantApprovalStatus::type val) {
  this->tenantApprovalStatus = val;
}

void Tenant::__set_tenantName(const std::string& val) {
  this->tenantName = val;
__isset.tenantName = true;
}

void Tenant::__set_domain(const std::string& val) {
  this->domain = val;
__isset.domain = true;
}

void Tenant::__set_emailAddress(const std::string& val) {
  this->emailAddress = val;
__isset.emailAddress = true;
}

void Tenant::__set_tenantAcronym(const std::string& val) {
  this->tenantAcronym = val;
__isset.tenantAcronym = true;
}

void Tenant::__set_tenantURL(const std::string& val) {
  this->tenantURL = val;
__isset.tenantURL = true;
}

void Tenant::__set_tenantPublicAbstract(const std::string& val) {
  this->tenantPublicAbstract = val;
__isset.tenantPublicAbstract = true;
}

void Tenant::__set_reviewProposalDescription(const std::string& val) {
  this->reviewProposalDescription = val;
__isset.reviewProposalDescription = true;
}

void Tenant::__set_declinedReason(const std::string& val) {
  this->declinedReason = val;
__isset.declinedReason = true;
}

void Tenant::__set_requestCreationTime(const int64_t val) {
  this->requestCreationTime = val;
__isset.requestCreationTime = true;
}

void Tenant::__set_requesterUsername(const std::string& val) {
  this->requesterUsername = val;
__isset.requesterUsername = true;
}

uint32_t Tenant::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_tenantId = false;
  bool isset_tenantApprovalStatus = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->tenantId);
          isset_tenantId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast4;
          xfer += iprot->readI32(ecast4);
          this->tenantApprovalStatus = (TenantApprovalStatus::type)ecast4;
          isset_tenantApprovalStatus = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->tenantName);
          this->__isset.tenantName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 4:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->domain);
          this->__isset.domain = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->emailAddress);
          this->__isset.emailAddress = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->tenantAcronym);
          this->__isset.tenantAcronym = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->tenantURL);
          this->__isset.tenantURL = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 8:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->tenantPublicAbstract);
          this->__isset.tenantPublicAbstract = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 9:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->reviewProposalDescription);
          this->__isset.reviewProposalDescription = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 15:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->declinedReason);
          this->__isset.declinedReason = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 18:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->requestCreationTime);
          this->__isset.requestCreationTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 19:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->requesterUsername);
          this->__isset.requesterUsername = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_tenantId)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_tenantApprovalStatus)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t Tenant::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("Tenant");

  xfer += oprot->writeFieldBegin("tenantId", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->tenantId);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("tenantApprovalStatus", ::apache::thrift::protocol::T_I32, 2);
  xfer += oprot->writeI32((int32_t)this->tenantApprovalStatus);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.tenantName) {
    xfer += oprot->writeFieldBegin("tenantName", ::apache::thrift::protocol::T_STRING, 3);
    xfer += oprot->writeString(this->tenantName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.domain) {
    xfer += oprot->writeFieldBegin("domain", ::apache::thrift::protocol::T_STRING, 4);
    xfer += oprot->writeString(this->domain);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.emailAddress) {
    xfer += oprot->writeFieldBegin("emailAddress", ::apache::thrift::protocol::T_STRING, 5);
    xfer += oprot->writeString(this->emailAddress);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.tenantAcronym) {
    xfer += oprot->writeFieldBegin("tenantAcronym", ::apache::thrift::protocol::T_STRING, 6);
    xfer += oprot->writeString(this->tenantAcronym);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.tenantURL) {
    xfer += oprot->writeFieldBegin("tenantURL", ::apache::thrift::protocol::T_STRING, 7);
    xfer += oprot->writeString(this->tenantURL);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.tenantPublicAbstract) {
    xfer += oprot->writeFieldBegin("tenantPublicAbstract", ::apache::thrift::protocol::T_STRING, 8);
    xfer += oprot->writeString(this->tenantPublicAbstract);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.reviewProposalDescription) {
    xfer += oprot->writeFieldBegin("reviewProposalDescription", ::apache::thrift::protocol::T_STRING, 9);
    xfer += oprot->writeString(this->reviewProposalDescription);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.declinedReason) {
    xfer += oprot->writeFieldBegin("declinedReason", ::apache::thrift::protocol::T_STRING, 15);
    xfer += oprot->writeString(this->declinedReason);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.requestCreationTime) {
    xfer += oprot->writeFieldBegin("requestCreationTime", ::apache::thrift::protocol::T_I64, 18);
    xfer += oprot->writeI64(this->requestCreationTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.requesterUsername) {
    xfer += oprot->writeFieldBegin("requesterUsername", ::apache::thrift::protocol::T_STRING, 19);
    xfer += oprot->writeString(this->requesterUsername);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(Tenant &a, Tenant &b) {
  using ::std::swap;
  swap(a.tenantId, b.tenantId);
  swap(a.tenantApprovalStatus, b.tenantApprovalStatus);
  swap(a.tenantName, b.tenantName);
  swap(a.domain, b.domain);
  swap(a.emailAddress, b.emailAddress);
  swap(a.tenantAcronym, b.tenantAcronym);
  swap(a.tenantURL, b.tenantURL);
  swap(a.tenantPublicAbstract, b.tenantPublicAbstract);
  swap(a.reviewProposalDescription, b.reviewProposalDescription);
  swap(a.declinedReason, b.declinedReason);
  swap(a.requestCreationTime, b.requestCreationTime);
  swap(a.requesterUsername, b.requesterUsername);
  swap(a.__isset, b.__isset);
}

Tenant::Tenant(const Tenant& other5) {
  tenantId = other5.tenantId;
  tenantApprovalStatus = other5.tenantApprovalStatus;
  tenantName = other5.tenantName;
  domain = other5.domain;
  emailAddress = other5.emailAddress;
  tenantAcronym = other5.tenantAcronym;
  tenantURL = other5.tenantURL;
  tenantPublicAbstract = other5.tenantPublicAbstract;
  reviewProposalDescription = other5.reviewProposalDescription;
  declinedReason = other5.declinedReason;
  requestCreationTime = other5.requestCreationTime;
  requesterUsername = other5.requesterUsername;
  __isset = other5.__isset;
}
Tenant& Tenant::operator=(const Tenant& other6) {
  tenantId = other6.tenantId;
  tenantApprovalStatus = other6.tenantApprovalStatus;
  tenantName = other6.tenantName;
  domain = other6.domain;
  emailAddress = other6.emailAddress;
  tenantAcronym = other6.tenantAcronym;
  tenantURL = other6.tenantURL;
  tenantPublicAbstract = other6.tenantPublicAbstract;
  reviewProposalDescription = other6.reviewProposalDescription;
  declinedReason = other6.declinedReason;
  requestCreationTime = other6.requestCreationTime;
  requesterUsername = other6.requesterUsername;
  __isset = other6.__isset;
  return *this;
}
void Tenant::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "Tenant(";
  out << "tenantId=" << to_string(tenantId);
  out << ", " << "tenantApprovalStatus=" << to_string(tenantApprovalStatus);
  out << ", " << "tenantName="; (__isset.tenantName ? (out << to_string(tenantName)) : (out << "<null>"));
  out << ", " << "domain="; (__isset.domain ? (out << to_string(domain)) : (out << "<null>"));
  out << ", " << "emailAddress="; (__isset.emailAddress ? (out << to_string(emailAddress)) : (out << "<null>"));
  out << ", " << "tenantAcronym="; (__isset.tenantAcronym ? (out << to_string(tenantAcronym)) : (out << "<null>"));
  out << ", " << "tenantURL="; (__isset.tenantURL ? (out << to_string(tenantURL)) : (out << "<null>"));
  out << ", " << "tenantPublicAbstract="; (__isset.tenantPublicAbstract ? (out << to_string(tenantPublicAbstract)) : (out << "<null>"));
  out << ", " << "reviewProposalDescription="; (__isset.reviewProposalDescription ? (out << to_string(reviewProposalDescription)) : (out << "<null>"));
  out << ", " << "declinedReason="; (__isset.declinedReason ? (out << to_string(declinedReason)) : (out << "<null>"));
  out << ", " << "requestCreationTime="; (__isset.requestCreationTime ? (out << to_string(requestCreationTime)) : (out << "<null>"));
  out << ", " << "requesterUsername="; (__isset.requesterUsername ? (out << to_string(requesterUsername)) : (out << "<null>"));
  out << ")";
}

}}}} // namespace
