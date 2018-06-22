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
#include "credential_store_data_models_types.h"

#include <algorithm>
#include <ostream>

#include <thrift/TToString.h>



int _kCredentialOwnerTypeValues[] = {
  CredentialOwnerType::GATEWAY,
  CredentialOwnerType::USER
};
const char* _kCredentialOwnerTypeNames[] = {
  "GATEWAY",
  "USER"
};
const std::map<int, const char*> _CredentialOwnerType_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(2, _kCredentialOwnerTypeValues, _kCredentialOwnerTypeNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));

int _kSummaryTypeValues[] = {
  SummaryType::SSH,
  SummaryType::PASSWD,
  SummaryType::CERT
};
const char* _kSummaryTypeNames[] = {
  "SSH",
  "PASSWD",
  "CERT"
};
const std::map<int, const char*> _SummaryType_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(3, _kSummaryTypeValues, _kSummaryTypeNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));


SSHCredential::~SSHCredential() throw() {
}


void SSHCredential::__set_gatewayId(const std::string& val) {
  this->gatewayId = val;
}

void SSHCredential::__set_username(const std::string& val) {
  this->username = val;
}

void SSHCredential::__set_passphrase(const std::string& val) {
  this->passphrase = val;
__isset.passphrase = true;
}

void SSHCredential::__set_publicKey(const std::string& val) {
  this->publicKey = val;
__isset.publicKey = true;
}

void SSHCredential::__set_privateKey(const std::string& val) {
  this->privateKey = val;
__isset.privateKey = true;
}

void SSHCredential::__set_persistedTime(const int64_t val) {
  this->persistedTime = val;
__isset.persistedTime = true;
}

void SSHCredential::__set_token(const std::string& val) {
  this->token = val;
__isset.token = true;
}

void SSHCredential::__set_description(const std::string& val) {
  this->description = val;
__isset.description = true;
}

void SSHCredential::__set_credentialOwnerType(const CredentialOwnerType::type val) {
  this->credentialOwnerType = val;
__isset.credentialOwnerType = true;
}

uint32_t SSHCredential::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_gatewayId = false;
  bool isset_username = false;

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
          xfer += iprot->readString(this->gatewayId);
          isset_gatewayId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->username);
          isset_username = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->passphrase);
          this->__isset.passphrase = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 4:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->publicKey);
          this->__isset.publicKey = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->privateKey);
          this->__isset.privateKey = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->persistedTime);
          this->__isset.persistedTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->token);
          this->__isset.token = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 8:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->description);
          this->__isset.description = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 9:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast0;
          xfer += iprot->readI32(ecast0);
          this->credentialOwnerType = (CredentialOwnerType::type)ecast0;
          this->__isset.credentialOwnerType = true;
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

  if (!isset_gatewayId)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_username)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t SSHCredential::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("SSHCredential");

  xfer += oprot->writeFieldBegin("gatewayId", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->gatewayId);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("username", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->username);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.passphrase) {
    xfer += oprot->writeFieldBegin("passphrase", ::apache::thrift::protocol::T_STRING, 3);
    xfer += oprot->writeString(this->passphrase);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.publicKey) {
    xfer += oprot->writeFieldBegin("publicKey", ::apache::thrift::protocol::T_STRING, 4);
    xfer += oprot->writeString(this->publicKey);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.privateKey) {
    xfer += oprot->writeFieldBegin("privateKey", ::apache::thrift::protocol::T_STRING, 5);
    xfer += oprot->writeString(this->privateKey);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.persistedTime) {
    xfer += oprot->writeFieldBegin("persistedTime", ::apache::thrift::protocol::T_I64, 6);
    xfer += oprot->writeI64(this->persistedTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.token) {
    xfer += oprot->writeFieldBegin("token", ::apache::thrift::protocol::T_STRING, 7);
    xfer += oprot->writeString(this->token);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.description) {
    xfer += oprot->writeFieldBegin("description", ::apache::thrift::protocol::T_STRING, 8);
    xfer += oprot->writeString(this->description);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.credentialOwnerType) {
    xfer += oprot->writeFieldBegin("credentialOwnerType", ::apache::thrift::protocol::T_I32, 9);
    xfer += oprot->writeI32((int32_t)this->credentialOwnerType);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(SSHCredential &a, SSHCredential &b) {
  using ::std::swap;
  swap(a.gatewayId, b.gatewayId);
  swap(a.username, b.username);
  swap(a.passphrase, b.passphrase);
  swap(a.publicKey, b.publicKey);
  swap(a.privateKey, b.privateKey);
  swap(a.persistedTime, b.persistedTime);
  swap(a.token, b.token);
  swap(a.description, b.description);
  swap(a.credentialOwnerType, b.credentialOwnerType);
  swap(a.__isset, b.__isset);
}

SSHCredential::SSHCredential(const SSHCredential& other1) {
  gatewayId = other1.gatewayId;
  username = other1.username;
  passphrase = other1.passphrase;
  publicKey = other1.publicKey;
  privateKey = other1.privateKey;
  persistedTime = other1.persistedTime;
  token = other1.token;
  description = other1.description;
  credentialOwnerType = other1.credentialOwnerType;
  __isset = other1.__isset;
}
SSHCredential& SSHCredential::operator=(const SSHCredential& other2) {
  gatewayId = other2.gatewayId;
  username = other2.username;
  passphrase = other2.passphrase;
  publicKey = other2.publicKey;
  privateKey = other2.privateKey;
  persistedTime = other2.persistedTime;
  token = other2.token;
  description = other2.description;
  credentialOwnerType = other2.credentialOwnerType;
  __isset = other2.__isset;
  return *this;
}
void SSHCredential::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "SSHCredential(";
  out << "gatewayId=" << to_string(gatewayId);
  out << ", " << "username=" << to_string(username);
  out << ", " << "passphrase="; (__isset.passphrase ? (out << to_string(passphrase)) : (out << "<null>"));
  out << ", " << "publicKey="; (__isset.publicKey ? (out << to_string(publicKey)) : (out << "<null>"));
  out << ", " << "privateKey="; (__isset.privateKey ? (out << to_string(privateKey)) : (out << "<null>"));
  out << ", " << "persistedTime="; (__isset.persistedTime ? (out << to_string(persistedTime)) : (out << "<null>"));
  out << ", " << "token="; (__isset.token ? (out << to_string(token)) : (out << "<null>"));
  out << ", " << "description="; (__isset.description ? (out << to_string(description)) : (out << "<null>"));
  out << ", " << "credentialOwnerType="; (__isset.credentialOwnerType ? (out << to_string(credentialOwnerType)) : (out << "<null>"));
  out << ")";
}


CredentialSummary::~CredentialSummary() throw() {
}


void CredentialSummary::__set_type(const SummaryType::type val) {
  this->type = val;
}

void CredentialSummary::__set_gatewayId(const std::string& val) {
  this->gatewayId = val;
}

void CredentialSummary::__set_username(const std::string& val) {
  this->username = val;
}

void CredentialSummary::__set_publicKey(const std::string& val) {
  this->publicKey = val;
__isset.publicKey = true;
}

void CredentialSummary::__set_persistedTime(const int64_t val) {
  this->persistedTime = val;
__isset.persistedTime = true;
}

void CredentialSummary::__set_token(const std::string& val) {
  this->token = val;
}

void CredentialSummary::__set_description(const std::string& val) {
  this->description = val;
__isset.description = true;
}

uint32_t CredentialSummary::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_type = false;
  bool isset_gatewayId = false;
  bool isset_username = false;
  bool isset_token = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast3;
          xfer += iprot->readI32(ecast3);
          this->type = (SummaryType::type)ecast3;
          isset_type = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->gatewayId);
          isset_gatewayId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->username);
          isset_username = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 4:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->publicKey);
          this->__isset.publicKey = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->persistedTime);
          this->__isset.persistedTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->token);
          isset_token = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->description);
          this->__isset.description = true;
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

  if (!isset_type)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_gatewayId)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_username)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_token)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t CredentialSummary::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("CredentialSummary");

  xfer += oprot->writeFieldBegin("type", ::apache::thrift::protocol::T_I32, 1);
  xfer += oprot->writeI32((int32_t)this->type);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("gatewayId", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->gatewayId);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("username", ::apache::thrift::protocol::T_STRING, 3);
  xfer += oprot->writeString(this->username);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.publicKey) {
    xfer += oprot->writeFieldBegin("publicKey", ::apache::thrift::protocol::T_STRING, 4);
    xfer += oprot->writeString(this->publicKey);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.persistedTime) {
    xfer += oprot->writeFieldBegin("persistedTime", ::apache::thrift::protocol::T_I64, 5);
    xfer += oprot->writeI64(this->persistedTime);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldBegin("token", ::apache::thrift::protocol::T_STRING, 6);
  xfer += oprot->writeString(this->token);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.description) {
    xfer += oprot->writeFieldBegin("description", ::apache::thrift::protocol::T_STRING, 7);
    xfer += oprot->writeString(this->description);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(CredentialSummary &a, CredentialSummary &b) {
  using ::std::swap;
  swap(a.type, b.type);
  swap(a.gatewayId, b.gatewayId);
  swap(a.username, b.username);
  swap(a.publicKey, b.publicKey);
  swap(a.persistedTime, b.persistedTime);
  swap(a.token, b.token);
  swap(a.description, b.description);
  swap(a.__isset, b.__isset);
}

CredentialSummary::CredentialSummary(const CredentialSummary& other4) {
  type = other4.type;
  gatewayId = other4.gatewayId;
  username = other4.username;
  publicKey = other4.publicKey;
  persistedTime = other4.persistedTime;
  token = other4.token;
  description = other4.description;
  __isset = other4.__isset;
}
CredentialSummary& CredentialSummary::operator=(const CredentialSummary& other5) {
  type = other5.type;
  gatewayId = other5.gatewayId;
  username = other5.username;
  publicKey = other5.publicKey;
  persistedTime = other5.persistedTime;
  token = other5.token;
  description = other5.description;
  __isset = other5.__isset;
  return *this;
}
void CredentialSummary::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "CredentialSummary(";
  out << "type=" << to_string(type);
  out << ", " << "gatewayId=" << to_string(gatewayId);
  out << ", " << "username=" << to_string(username);
  out << ", " << "publicKey="; (__isset.publicKey ? (out << to_string(publicKey)) : (out << "<null>"));
  out << ", " << "persistedTime="; (__isset.persistedTime ? (out << to_string(persistedTime)) : (out << "<null>"));
  out << ", " << "token=" << to_string(token);
  out << ", " << "description="; (__isset.description ? (out << to_string(description)) : (out << "<null>"));
  out << ")";
}


CommunityUser::~CommunityUser() throw() {
}


void CommunityUser::__set_gatewayName(const std::string& val) {
  this->gatewayName = val;
}

void CommunityUser::__set_username(const std::string& val) {
  this->username = val;
}

void CommunityUser::__set_userEmail(const std::string& val) {
  this->userEmail = val;
}

uint32_t CommunityUser::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_gatewayName = false;
  bool isset_username = false;
  bool isset_userEmail = false;

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
          xfer += iprot->readString(this->gatewayName);
          isset_gatewayName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->username);
          isset_username = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->userEmail);
          isset_userEmail = true;
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

  if (!isset_gatewayName)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_username)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_userEmail)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t CommunityUser::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("CommunityUser");

  xfer += oprot->writeFieldBegin("gatewayName", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->gatewayName);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("username", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->username);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("userEmail", ::apache::thrift::protocol::T_STRING, 3);
  xfer += oprot->writeString(this->userEmail);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(CommunityUser &a, CommunityUser &b) {
  using ::std::swap;
  swap(a.gatewayName, b.gatewayName);
  swap(a.username, b.username);
  swap(a.userEmail, b.userEmail);
}

CommunityUser::CommunityUser(const CommunityUser& other6) {
  gatewayName = other6.gatewayName;
  username = other6.username;
  userEmail = other6.userEmail;
}
CommunityUser& CommunityUser::operator=(const CommunityUser& other7) {
  gatewayName = other7.gatewayName;
  username = other7.username;
  userEmail = other7.userEmail;
  return *this;
}
void CommunityUser::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "CommunityUser(";
  out << "gatewayName=" << to_string(gatewayName);
  out << ", " << "username=" << to_string(username);
  out << ", " << "userEmail=" << to_string(userEmail);
  out << ")";
}


CertificateCredential::~CertificateCredential() throw() {
}


void CertificateCredential::__set_communityUser(const CommunityUser& val) {
  this->communityUser = val;
}

void CertificateCredential::__set_x509Cert(const std::string& val) {
  this->x509Cert = val;
}

void CertificateCredential::__set_notAfter(const std::string& val) {
  this->notAfter = val;
__isset.notAfter = true;
}

void CertificateCredential::__set_privateKey(const std::string& val) {
  this->privateKey = val;
__isset.privateKey = true;
}

void CertificateCredential::__set_lifeTime(const int64_t val) {
  this->lifeTime = val;
__isset.lifeTime = true;
}

void CertificateCredential::__set_notBefore(const std::string& val) {
  this->notBefore = val;
__isset.notBefore = true;
}

void CertificateCredential::__set_persistedTime(const int64_t val) {
  this->persistedTime = val;
__isset.persistedTime = true;
}

void CertificateCredential::__set_token(const std::string& val) {
  this->token = val;
__isset.token = true;
}

uint32_t CertificateCredential::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_communityUser = false;
  bool isset_x509Cert = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRUCT) {
          xfer += this->communityUser.read(iprot);
          isset_communityUser = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->x509Cert);
          isset_x509Cert = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->notAfter);
          this->__isset.notAfter = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 4:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->privateKey);
          this->__isset.privateKey = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->lifeTime);
          this->__isset.lifeTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->notBefore);
          this->__isset.notBefore = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->persistedTime);
          this->__isset.persistedTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 8:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->token);
          this->__isset.token = true;
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

  if (!isset_communityUser)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_x509Cert)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t CertificateCredential::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("CertificateCredential");

  xfer += oprot->writeFieldBegin("communityUser", ::apache::thrift::protocol::T_STRUCT, 1);
  xfer += this->communityUser.write(oprot);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("x509Cert", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->x509Cert);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.notAfter) {
    xfer += oprot->writeFieldBegin("notAfter", ::apache::thrift::protocol::T_STRING, 3);
    xfer += oprot->writeString(this->notAfter);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.privateKey) {
    xfer += oprot->writeFieldBegin("privateKey", ::apache::thrift::protocol::T_STRING, 4);
    xfer += oprot->writeString(this->privateKey);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.lifeTime) {
    xfer += oprot->writeFieldBegin("lifeTime", ::apache::thrift::protocol::T_I64, 5);
    xfer += oprot->writeI64(this->lifeTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.notBefore) {
    xfer += oprot->writeFieldBegin("notBefore", ::apache::thrift::protocol::T_STRING, 6);
    xfer += oprot->writeString(this->notBefore);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.persistedTime) {
    xfer += oprot->writeFieldBegin("persistedTime", ::apache::thrift::protocol::T_I64, 7);
    xfer += oprot->writeI64(this->persistedTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.token) {
    xfer += oprot->writeFieldBegin("token", ::apache::thrift::protocol::T_STRING, 8);
    xfer += oprot->writeString(this->token);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(CertificateCredential &a, CertificateCredential &b) {
  using ::std::swap;
  swap(a.communityUser, b.communityUser);
  swap(a.x509Cert, b.x509Cert);
  swap(a.notAfter, b.notAfter);
  swap(a.privateKey, b.privateKey);
  swap(a.lifeTime, b.lifeTime);
  swap(a.notBefore, b.notBefore);
  swap(a.persistedTime, b.persistedTime);
  swap(a.token, b.token);
  swap(a.__isset, b.__isset);
}

CertificateCredential::CertificateCredential(const CertificateCredential& other8) {
  communityUser = other8.communityUser;
  x509Cert = other8.x509Cert;
  notAfter = other8.notAfter;
  privateKey = other8.privateKey;
  lifeTime = other8.lifeTime;
  notBefore = other8.notBefore;
  persistedTime = other8.persistedTime;
  token = other8.token;
  __isset = other8.__isset;
}
CertificateCredential& CertificateCredential::operator=(const CertificateCredential& other9) {
  communityUser = other9.communityUser;
  x509Cert = other9.x509Cert;
  notAfter = other9.notAfter;
  privateKey = other9.privateKey;
  lifeTime = other9.lifeTime;
  notBefore = other9.notBefore;
  persistedTime = other9.persistedTime;
  token = other9.token;
  __isset = other9.__isset;
  return *this;
}
void CertificateCredential::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "CertificateCredential(";
  out << "communityUser=" << to_string(communityUser);
  out << ", " << "x509Cert=" << to_string(x509Cert);
  out << ", " << "notAfter="; (__isset.notAfter ? (out << to_string(notAfter)) : (out << "<null>"));
  out << ", " << "privateKey="; (__isset.privateKey ? (out << to_string(privateKey)) : (out << "<null>"));
  out << ", " << "lifeTime="; (__isset.lifeTime ? (out << to_string(lifeTime)) : (out << "<null>"));
  out << ", " << "notBefore="; (__isset.notBefore ? (out << to_string(notBefore)) : (out << "<null>"));
  out << ", " << "persistedTime="; (__isset.persistedTime ? (out << to_string(persistedTime)) : (out << "<null>"));
  out << ", " << "token="; (__isset.token ? (out << to_string(token)) : (out << "<null>"));
  out << ")";
}


PasswordCredential::~PasswordCredential() throw() {
}


void PasswordCredential::__set_gatewayId(const std::string& val) {
  this->gatewayId = val;
}

void PasswordCredential::__set_portalUserName(const std::string& val) {
  this->portalUserName = val;
}

void PasswordCredential::__set_loginUserName(const std::string& val) {
  this->loginUserName = val;
}

void PasswordCredential::__set_password(const std::string& val) {
  this->password = val;
}

void PasswordCredential::__set_description(const std::string& val) {
  this->description = val;
__isset.description = true;
}

void PasswordCredential::__set_persistedTime(const int64_t val) {
  this->persistedTime = val;
__isset.persistedTime = true;
}

void PasswordCredential::__set_token(const std::string& val) {
  this->token = val;
__isset.token = true;
}

uint32_t PasswordCredential::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_gatewayId = false;
  bool isset_portalUserName = false;
  bool isset_loginUserName = false;
  bool isset_password = false;

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
          xfer += iprot->readString(this->gatewayId);
          isset_gatewayId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->portalUserName);
          isset_portalUserName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->loginUserName);
          isset_loginUserName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 4:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->password);
          isset_password = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->description);
          this->__isset.description = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->persistedTime);
          this->__isset.persistedTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->token);
          this->__isset.token = true;
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

  if (!isset_gatewayId)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_portalUserName)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_loginUserName)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_password)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t PasswordCredential::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("PasswordCredential");

  xfer += oprot->writeFieldBegin("gatewayId", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->gatewayId);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("portalUserName", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->portalUserName);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("loginUserName", ::apache::thrift::protocol::T_STRING, 3);
  xfer += oprot->writeString(this->loginUserName);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("password", ::apache::thrift::protocol::T_STRING, 4);
  xfer += oprot->writeString(this->password);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.description) {
    xfer += oprot->writeFieldBegin("description", ::apache::thrift::protocol::T_STRING, 5);
    xfer += oprot->writeString(this->description);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.persistedTime) {
    xfer += oprot->writeFieldBegin("persistedTime", ::apache::thrift::protocol::T_I64, 6);
    xfer += oprot->writeI64(this->persistedTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.token) {
    xfer += oprot->writeFieldBegin("token", ::apache::thrift::protocol::T_STRING, 7);
    xfer += oprot->writeString(this->token);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(PasswordCredential &a, PasswordCredential &b) {
  using ::std::swap;
  swap(a.gatewayId, b.gatewayId);
  swap(a.portalUserName, b.portalUserName);
  swap(a.loginUserName, b.loginUserName);
  swap(a.password, b.password);
  swap(a.description, b.description);
  swap(a.persistedTime, b.persistedTime);
  swap(a.token, b.token);
  swap(a.__isset, b.__isset);
}

PasswordCredential::PasswordCredential(const PasswordCredential& other10) {
  gatewayId = other10.gatewayId;
  portalUserName = other10.portalUserName;
  loginUserName = other10.loginUserName;
  password = other10.password;
  description = other10.description;
  persistedTime = other10.persistedTime;
  token = other10.token;
  __isset = other10.__isset;
}
PasswordCredential& PasswordCredential::operator=(const PasswordCredential& other11) {
  gatewayId = other11.gatewayId;
  portalUserName = other11.portalUserName;
  loginUserName = other11.loginUserName;
  password = other11.password;
  description = other11.description;
  persistedTime = other11.persistedTime;
  token = other11.token;
  __isset = other11.__isset;
  return *this;
}
void PasswordCredential::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "PasswordCredential(";
  out << "gatewayId=" << to_string(gatewayId);
  out << ", " << "portalUserName=" << to_string(portalUserName);
  out << ", " << "loginUserName=" << to_string(loginUserName);
  out << ", " << "password=" << to_string(password);
  out << ", " << "description="; (__isset.description ? (out << to_string(description)) : (out << "<null>"));
  out << ", " << "persistedTime="; (__isset.persistedTime ? (out << to_string(persistedTime)) : (out << "<null>"));
  out << ", " << "token="; (__isset.token ? (out << to_string(token)) : (out << "<null>"));
  out << ")";
}


