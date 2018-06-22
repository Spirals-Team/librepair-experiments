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
#ifndef credential_summary_TYPES_H
#define credential_summary_TYPES_H

#include <iosfwd>

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <thrift/cxxfunctional.h>


namespace apache { namespace airavata { namespace model { namespace appcatalog { namespace credentialsummary {

class CredentialSummary;

typedef struct _CredentialSummary__isset {
  _CredentialSummary__isset() : publicKey(false), persistedTime(false), description(false) {}
  bool publicKey :1;
  bool persistedTime :1;
  bool description :1;
} _CredentialSummary__isset;

class CredentialSummary {
 public:

  CredentialSummary(const CredentialSummary&);
  CredentialSummary& operator=(const CredentialSummary&);
  CredentialSummary() : gatewayId(), username(), token(), publicKey(), persistedTime(0), description() {
  }

  virtual ~CredentialSummary() throw();
  std::string gatewayId;
  std::string username;
  std::string token;
  std::string publicKey;
  int64_t persistedTime;
  std::string description;

  _CredentialSummary__isset __isset;

  void __set_gatewayId(const std::string& val);

  void __set_username(const std::string& val);

  void __set_token(const std::string& val);

  void __set_publicKey(const std::string& val);

  void __set_persistedTime(const int64_t val);

  void __set_description(const std::string& val);

  bool operator == (const CredentialSummary & rhs) const
  {
    if (!(gatewayId == rhs.gatewayId))
      return false;
    if (!(username == rhs.username))
      return false;
    if (!(token == rhs.token))
      return false;
    if (__isset.publicKey != rhs.__isset.publicKey)
      return false;
    else if (__isset.publicKey && !(publicKey == rhs.publicKey))
      return false;
    if (__isset.persistedTime != rhs.__isset.persistedTime)
      return false;
    else if (__isset.persistedTime && !(persistedTime == rhs.persistedTime))
      return false;
    if (__isset.description != rhs.__isset.description)
      return false;
    else if (__isset.description && !(description == rhs.description))
      return false;
    return true;
  }
  bool operator != (const CredentialSummary &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const CredentialSummary & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(CredentialSummary &a, CredentialSummary &b);

inline std::ostream& operator<<(std::ostream& out, const CredentialSummary& obj)
{
  obj.printTo(out);
  return out;
}

}}}}} // namespace

#endif
