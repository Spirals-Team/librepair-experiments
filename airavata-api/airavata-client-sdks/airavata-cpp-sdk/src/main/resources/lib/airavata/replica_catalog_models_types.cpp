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
#include "replica_catalog_models_types.h"

#include <algorithm>
#include <ostream>

#include <thrift/TToString.h>

namespace apache { namespace airavata { namespace model { namespace data { namespace replica {

int _kReplicaLocationCategoryValues[] = {
  ReplicaLocationCategory::GATEWAY_DATA_STORE,
  ReplicaLocationCategory::COMPUTE_RESOURCE,
  ReplicaLocationCategory::LONG_TERM_STORAGE_RESOURCE,
  ReplicaLocationCategory::OTHER
};
const char* _kReplicaLocationCategoryNames[] = {
  "GATEWAY_DATA_STORE",
  "COMPUTE_RESOURCE",
  "LONG_TERM_STORAGE_RESOURCE",
  "OTHER"
};
const std::map<int, const char*> _ReplicaLocationCategory_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(4, _kReplicaLocationCategoryValues, _kReplicaLocationCategoryNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));

int _kReplicaPersistentTypeValues[] = {
  ReplicaPersistentType::TRANSIENT,
  ReplicaPersistentType::PERSISTENT
};
const char* _kReplicaPersistentTypeNames[] = {
  "TRANSIENT",
  "PERSISTENT"
};
const std::map<int, const char*> _ReplicaPersistentType_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(2, _kReplicaPersistentTypeValues, _kReplicaPersistentTypeNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));

int _kDataProductTypeValues[] = {
  DataProductType::FILE,
  DataProductType::COLLECTION
};
const char* _kDataProductTypeNames[] = {
  "FILE",
  "COLLECTION"
};
const std::map<int, const char*> _DataProductType_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(2, _kDataProductTypeValues, _kDataProductTypeNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));


DataReplicaLocationModel::~DataReplicaLocationModel() throw() {
}


void DataReplicaLocationModel::__set_replicaId(const std::string& val) {
  this->replicaId = val;
__isset.replicaId = true;
}

void DataReplicaLocationModel::__set_productUri(const std::string& val) {
  this->productUri = val;
__isset.productUri = true;
}

void DataReplicaLocationModel::__set_replicaName(const std::string& val) {
  this->replicaName = val;
__isset.replicaName = true;
}

void DataReplicaLocationModel::__set_replicaDescription(const std::string& val) {
  this->replicaDescription = val;
__isset.replicaDescription = true;
}

void DataReplicaLocationModel::__set_creationTime(const int64_t val) {
  this->creationTime = val;
__isset.creationTime = true;
}

void DataReplicaLocationModel::__set_lastModifiedTime(const int64_t val) {
  this->lastModifiedTime = val;
__isset.lastModifiedTime = true;
}

void DataReplicaLocationModel::__set_validUntilTime(const int64_t val) {
  this->validUntilTime = val;
__isset.validUntilTime = true;
}

void DataReplicaLocationModel::__set_replicaLocationCategory(const ReplicaLocationCategory::type val) {
  this->replicaLocationCategory = val;
__isset.replicaLocationCategory = true;
}

void DataReplicaLocationModel::__set_replicaPersistentType(const ReplicaPersistentType::type val) {
  this->replicaPersistentType = val;
__isset.replicaPersistentType = true;
}

void DataReplicaLocationModel::__set_storageResourceId(const std::string& val) {
  this->storageResourceId = val;
__isset.storageResourceId = true;
}

void DataReplicaLocationModel::__set_filePath(const std::string& val) {
  this->filePath = val;
__isset.filePath = true;
}

void DataReplicaLocationModel::__set_replicaMetadata(const std::map<std::string, std::string> & val) {
  this->replicaMetadata = val;
__isset.replicaMetadata = true;
}

uint32_t DataReplicaLocationModel::read(::apache::thrift::protocol::TProtocol* iprot) {

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
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->replicaId);
          this->__isset.replicaId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->productUri);
          this->__isset.productUri = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->replicaName);
          this->__isset.replicaName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 4:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->replicaDescription);
          this->__isset.replicaDescription = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->creationTime);
          this->__isset.creationTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->lastModifiedTime);
          this->__isset.lastModifiedTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->validUntilTime);
          this->__isset.validUntilTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 8:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast0;
          xfer += iprot->readI32(ecast0);
          this->replicaLocationCategory = (ReplicaLocationCategory::type)ecast0;
          this->__isset.replicaLocationCategory = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 9:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast1;
          xfer += iprot->readI32(ecast1);
          this->replicaPersistentType = (ReplicaPersistentType::type)ecast1;
          this->__isset.replicaPersistentType = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 10:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->storageResourceId);
          this->__isset.storageResourceId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 11:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->filePath);
          this->__isset.filePath = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 12:
        if (ftype == ::apache::thrift::protocol::T_MAP) {
          {
            this->replicaMetadata.clear();
            uint32_t _size2;
            ::apache::thrift::protocol::TType _ktype3;
            ::apache::thrift::protocol::TType _vtype4;
            xfer += iprot->readMapBegin(_ktype3, _vtype4, _size2);
            uint32_t _i6;
            for (_i6 = 0; _i6 < _size2; ++_i6)
            {
              std::string _key7;
              xfer += iprot->readString(_key7);
              std::string& _val8 = this->replicaMetadata[_key7];
              xfer += iprot->readString(_val8);
            }
            xfer += iprot->readMapEnd();
          }
          this->__isset.replicaMetadata = true;
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

uint32_t DataReplicaLocationModel::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("DataReplicaLocationModel");

  if (this->__isset.replicaId) {
    xfer += oprot->writeFieldBegin("replicaId", ::apache::thrift::protocol::T_STRING, 1);
    xfer += oprot->writeString(this->replicaId);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productUri) {
    xfer += oprot->writeFieldBegin("productUri", ::apache::thrift::protocol::T_STRING, 2);
    xfer += oprot->writeString(this->productUri);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.replicaName) {
    xfer += oprot->writeFieldBegin("replicaName", ::apache::thrift::protocol::T_STRING, 3);
    xfer += oprot->writeString(this->replicaName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.replicaDescription) {
    xfer += oprot->writeFieldBegin("replicaDescription", ::apache::thrift::protocol::T_STRING, 4);
    xfer += oprot->writeString(this->replicaDescription);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.creationTime) {
    xfer += oprot->writeFieldBegin("creationTime", ::apache::thrift::protocol::T_I64, 5);
    xfer += oprot->writeI64(this->creationTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.lastModifiedTime) {
    xfer += oprot->writeFieldBegin("lastModifiedTime", ::apache::thrift::protocol::T_I64, 6);
    xfer += oprot->writeI64(this->lastModifiedTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.validUntilTime) {
    xfer += oprot->writeFieldBegin("validUntilTime", ::apache::thrift::protocol::T_I64, 7);
    xfer += oprot->writeI64(this->validUntilTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.replicaLocationCategory) {
    xfer += oprot->writeFieldBegin("replicaLocationCategory", ::apache::thrift::protocol::T_I32, 8);
    xfer += oprot->writeI32((int32_t)this->replicaLocationCategory);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.replicaPersistentType) {
    xfer += oprot->writeFieldBegin("replicaPersistentType", ::apache::thrift::protocol::T_I32, 9);
    xfer += oprot->writeI32((int32_t)this->replicaPersistentType);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.storageResourceId) {
    xfer += oprot->writeFieldBegin("storageResourceId", ::apache::thrift::protocol::T_STRING, 10);
    xfer += oprot->writeString(this->storageResourceId);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.filePath) {
    xfer += oprot->writeFieldBegin("filePath", ::apache::thrift::protocol::T_STRING, 11);
    xfer += oprot->writeString(this->filePath);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.replicaMetadata) {
    xfer += oprot->writeFieldBegin("replicaMetadata", ::apache::thrift::protocol::T_MAP, 12);
    {
      xfer += oprot->writeMapBegin(::apache::thrift::protocol::T_STRING, ::apache::thrift::protocol::T_STRING, static_cast<uint32_t>(this->replicaMetadata.size()));
      std::map<std::string, std::string> ::const_iterator _iter9;
      for (_iter9 = this->replicaMetadata.begin(); _iter9 != this->replicaMetadata.end(); ++_iter9)
      {
        xfer += oprot->writeString(_iter9->first);
        xfer += oprot->writeString(_iter9->second);
      }
      xfer += oprot->writeMapEnd();
    }
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(DataReplicaLocationModel &a, DataReplicaLocationModel &b) {
  using ::std::swap;
  swap(a.replicaId, b.replicaId);
  swap(a.productUri, b.productUri);
  swap(a.replicaName, b.replicaName);
  swap(a.replicaDescription, b.replicaDescription);
  swap(a.creationTime, b.creationTime);
  swap(a.lastModifiedTime, b.lastModifiedTime);
  swap(a.validUntilTime, b.validUntilTime);
  swap(a.replicaLocationCategory, b.replicaLocationCategory);
  swap(a.replicaPersistentType, b.replicaPersistentType);
  swap(a.storageResourceId, b.storageResourceId);
  swap(a.filePath, b.filePath);
  swap(a.replicaMetadata, b.replicaMetadata);
  swap(a.__isset, b.__isset);
}

DataReplicaLocationModel::DataReplicaLocationModel(const DataReplicaLocationModel& other10) {
  replicaId = other10.replicaId;
  productUri = other10.productUri;
  replicaName = other10.replicaName;
  replicaDescription = other10.replicaDescription;
  creationTime = other10.creationTime;
  lastModifiedTime = other10.lastModifiedTime;
  validUntilTime = other10.validUntilTime;
  replicaLocationCategory = other10.replicaLocationCategory;
  replicaPersistentType = other10.replicaPersistentType;
  storageResourceId = other10.storageResourceId;
  filePath = other10.filePath;
  replicaMetadata = other10.replicaMetadata;
  __isset = other10.__isset;
}
DataReplicaLocationModel& DataReplicaLocationModel::operator=(const DataReplicaLocationModel& other11) {
  replicaId = other11.replicaId;
  productUri = other11.productUri;
  replicaName = other11.replicaName;
  replicaDescription = other11.replicaDescription;
  creationTime = other11.creationTime;
  lastModifiedTime = other11.lastModifiedTime;
  validUntilTime = other11.validUntilTime;
  replicaLocationCategory = other11.replicaLocationCategory;
  replicaPersistentType = other11.replicaPersistentType;
  storageResourceId = other11.storageResourceId;
  filePath = other11.filePath;
  replicaMetadata = other11.replicaMetadata;
  __isset = other11.__isset;
  return *this;
}
void DataReplicaLocationModel::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "DataReplicaLocationModel(";
  out << "replicaId="; (__isset.replicaId ? (out << to_string(replicaId)) : (out << "<null>"));
  out << ", " << "productUri="; (__isset.productUri ? (out << to_string(productUri)) : (out << "<null>"));
  out << ", " << "replicaName="; (__isset.replicaName ? (out << to_string(replicaName)) : (out << "<null>"));
  out << ", " << "replicaDescription="; (__isset.replicaDescription ? (out << to_string(replicaDescription)) : (out << "<null>"));
  out << ", " << "creationTime="; (__isset.creationTime ? (out << to_string(creationTime)) : (out << "<null>"));
  out << ", " << "lastModifiedTime="; (__isset.lastModifiedTime ? (out << to_string(lastModifiedTime)) : (out << "<null>"));
  out << ", " << "validUntilTime="; (__isset.validUntilTime ? (out << to_string(validUntilTime)) : (out << "<null>"));
  out << ", " << "replicaLocationCategory="; (__isset.replicaLocationCategory ? (out << to_string(replicaLocationCategory)) : (out << "<null>"));
  out << ", " << "replicaPersistentType="; (__isset.replicaPersistentType ? (out << to_string(replicaPersistentType)) : (out << "<null>"));
  out << ", " << "storageResourceId="; (__isset.storageResourceId ? (out << to_string(storageResourceId)) : (out << "<null>"));
  out << ", " << "filePath="; (__isset.filePath ? (out << to_string(filePath)) : (out << "<null>"));
  out << ", " << "replicaMetadata="; (__isset.replicaMetadata ? (out << to_string(replicaMetadata)) : (out << "<null>"));
  out << ")";
}


DataProductModel::~DataProductModel() throw() {
}


void DataProductModel::__set_productUri(const std::string& val) {
  this->productUri = val;
__isset.productUri = true;
}

void DataProductModel::__set_gatewayId(const std::string& val) {
  this->gatewayId = val;
__isset.gatewayId = true;
}

void DataProductModel::__set_parentProductUri(const std::string& val) {
  this->parentProductUri = val;
__isset.parentProductUri = true;
}

void DataProductModel::__set_productName(const std::string& val) {
  this->productName = val;
__isset.productName = true;
}

void DataProductModel::__set_productDescription(const std::string& val) {
  this->productDescription = val;
__isset.productDescription = true;
}

void DataProductModel::__set_ownerName(const std::string& val) {
  this->ownerName = val;
__isset.ownerName = true;
}

void DataProductModel::__set_dataProductType(const DataProductType::type val) {
  this->dataProductType = val;
__isset.dataProductType = true;
}

void DataProductModel::__set_productSize(const int32_t val) {
  this->productSize = val;
__isset.productSize = true;
}

void DataProductModel::__set_creationTime(const int64_t val) {
  this->creationTime = val;
__isset.creationTime = true;
}

void DataProductModel::__set_lastModifiedTime(const int64_t val) {
  this->lastModifiedTime = val;
__isset.lastModifiedTime = true;
}

void DataProductModel::__set_productMetadata(const std::map<std::string, std::string> & val) {
  this->productMetadata = val;
__isset.productMetadata = true;
}

void DataProductModel::__set_replicaLocations(const std::vector<DataReplicaLocationModel> & val) {
  this->replicaLocations = val;
__isset.replicaLocations = true;
}

uint32_t DataProductModel::read(::apache::thrift::protocol::TProtocol* iprot) {

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
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->productUri);
          this->__isset.productUri = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->gatewayId);
          this->__isset.gatewayId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->parentProductUri);
          this->__isset.parentProductUri = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 4:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->productName);
          this->__isset.productName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->productDescription);
          this->__isset.productDescription = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->ownerName);
          this->__isset.ownerName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast12;
          xfer += iprot->readI32(ecast12);
          this->dataProductType = (DataProductType::type)ecast12;
          this->__isset.dataProductType = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 8:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          xfer += iprot->readI32(this->productSize);
          this->__isset.productSize = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 9:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->creationTime);
          this->__isset.creationTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 10:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->lastModifiedTime);
          this->__isset.lastModifiedTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 11:
        if (ftype == ::apache::thrift::protocol::T_MAP) {
          {
            this->productMetadata.clear();
            uint32_t _size13;
            ::apache::thrift::protocol::TType _ktype14;
            ::apache::thrift::protocol::TType _vtype15;
            xfer += iprot->readMapBegin(_ktype14, _vtype15, _size13);
            uint32_t _i17;
            for (_i17 = 0; _i17 < _size13; ++_i17)
            {
              std::string _key18;
              xfer += iprot->readString(_key18);
              std::string& _val19 = this->productMetadata[_key18];
              xfer += iprot->readString(_val19);
            }
            xfer += iprot->readMapEnd();
          }
          this->__isset.productMetadata = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 12:
        if (ftype == ::apache::thrift::protocol::T_LIST) {
          {
            this->replicaLocations.clear();
            uint32_t _size20;
            ::apache::thrift::protocol::TType _etype23;
            xfer += iprot->readListBegin(_etype23, _size20);
            this->replicaLocations.resize(_size20);
            uint32_t _i24;
            for (_i24 = 0; _i24 < _size20; ++_i24)
            {
              xfer += this->replicaLocations[_i24].read(iprot);
            }
            xfer += iprot->readListEnd();
          }
          this->__isset.replicaLocations = true;
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

uint32_t DataProductModel::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("DataProductModel");

  if (this->__isset.productUri) {
    xfer += oprot->writeFieldBegin("productUri", ::apache::thrift::protocol::T_STRING, 1);
    xfer += oprot->writeString(this->productUri);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.gatewayId) {
    xfer += oprot->writeFieldBegin("gatewayId", ::apache::thrift::protocol::T_STRING, 2);
    xfer += oprot->writeString(this->gatewayId);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.parentProductUri) {
    xfer += oprot->writeFieldBegin("parentProductUri", ::apache::thrift::protocol::T_STRING, 3);
    xfer += oprot->writeString(this->parentProductUri);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productName) {
    xfer += oprot->writeFieldBegin("productName", ::apache::thrift::protocol::T_STRING, 4);
    xfer += oprot->writeString(this->productName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productDescription) {
    xfer += oprot->writeFieldBegin("productDescription", ::apache::thrift::protocol::T_STRING, 5);
    xfer += oprot->writeString(this->productDescription);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.ownerName) {
    xfer += oprot->writeFieldBegin("ownerName", ::apache::thrift::protocol::T_STRING, 6);
    xfer += oprot->writeString(this->ownerName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.dataProductType) {
    xfer += oprot->writeFieldBegin("dataProductType", ::apache::thrift::protocol::T_I32, 7);
    xfer += oprot->writeI32((int32_t)this->dataProductType);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productSize) {
    xfer += oprot->writeFieldBegin("productSize", ::apache::thrift::protocol::T_I32, 8);
    xfer += oprot->writeI32(this->productSize);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.creationTime) {
    xfer += oprot->writeFieldBegin("creationTime", ::apache::thrift::protocol::T_I64, 9);
    xfer += oprot->writeI64(this->creationTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.lastModifiedTime) {
    xfer += oprot->writeFieldBegin("lastModifiedTime", ::apache::thrift::protocol::T_I64, 10);
    xfer += oprot->writeI64(this->lastModifiedTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productMetadata) {
    xfer += oprot->writeFieldBegin("productMetadata", ::apache::thrift::protocol::T_MAP, 11);
    {
      xfer += oprot->writeMapBegin(::apache::thrift::protocol::T_STRING, ::apache::thrift::protocol::T_STRING, static_cast<uint32_t>(this->productMetadata.size()));
      std::map<std::string, std::string> ::const_iterator _iter25;
      for (_iter25 = this->productMetadata.begin(); _iter25 != this->productMetadata.end(); ++_iter25)
      {
        xfer += oprot->writeString(_iter25->first);
        xfer += oprot->writeString(_iter25->second);
      }
      xfer += oprot->writeMapEnd();
    }
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.replicaLocations) {
    xfer += oprot->writeFieldBegin("replicaLocations", ::apache::thrift::protocol::T_LIST, 12);
    {
      xfer += oprot->writeListBegin(::apache::thrift::protocol::T_STRUCT, static_cast<uint32_t>(this->replicaLocations.size()));
      std::vector<DataReplicaLocationModel> ::const_iterator _iter26;
      for (_iter26 = this->replicaLocations.begin(); _iter26 != this->replicaLocations.end(); ++_iter26)
      {
        xfer += (*_iter26).write(oprot);
      }
      xfer += oprot->writeListEnd();
    }
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(DataProductModel &a, DataProductModel &b) {
  using ::std::swap;
  swap(a.productUri, b.productUri);
  swap(a.gatewayId, b.gatewayId);
  swap(a.parentProductUri, b.parentProductUri);
  swap(a.productName, b.productName);
  swap(a.productDescription, b.productDescription);
  swap(a.ownerName, b.ownerName);
  swap(a.dataProductType, b.dataProductType);
  swap(a.productSize, b.productSize);
  swap(a.creationTime, b.creationTime);
  swap(a.lastModifiedTime, b.lastModifiedTime);
  swap(a.productMetadata, b.productMetadata);
  swap(a.replicaLocations, b.replicaLocations);
  swap(a.__isset, b.__isset);
}

DataProductModel::DataProductModel(const DataProductModel& other27) {
  productUri = other27.productUri;
  gatewayId = other27.gatewayId;
  parentProductUri = other27.parentProductUri;
  productName = other27.productName;
  productDescription = other27.productDescription;
  ownerName = other27.ownerName;
  dataProductType = other27.dataProductType;
  productSize = other27.productSize;
  creationTime = other27.creationTime;
  lastModifiedTime = other27.lastModifiedTime;
  productMetadata = other27.productMetadata;
  replicaLocations = other27.replicaLocations;
  __isset = other27.__isset;
}
DataProductModel& DataProductModel::operator=(const DataProductModel& other28) {
  productUri = other28.productUri;
  gatewayId = other28.gatewayId;
  parentProductUri = other28.parentProductUri;
  productName = other28.productName;
  productDescription = other28.productDescription;
  ownerName = other28.ownerName;
  dataProductType = other28.dataProductType;
  productSize = other28.productSize;
  creationTime = other28.creationTime;
  lastModifiedTime = other28.lastModifiedTime;
  productMetadata = other28.productMetadata;
  replicaLocations = other28.replicaLocations;
  __isset = other28.__isset;
  return *this;
}
void DataProductModel::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "DataProductModel(";
  out << "productUri="; (__isset.productUri ? (out << to_string(productUri)) : (out << "<null>"));
  out << ", " << "gatewayId="; (__isset.gatewayId ? (out << to_string(gatewayId)) : (out << "<null>"));
  out << ", " << "parentProductUri="; (__isset.parentProductUri ? (out << to_string(parentProductUri)) : (out << "<null>"));
  out << ", " << "productName="; (__isset.productName ? (out << to_string(productName)) : (out << "<null>"));
  out << ", " << "productDescription="; (__isset.productDescription ? (out << to_string(productDescription)) : (out << "<null>"));
  out << ", " << "ownerName="; (__isset.ownerName ? (out << to_string(ownerName)) : (out << "<null>"));
  out << ", " << "dataProductType="; (__isset.dataProductType ? (out << to_string(dataProductType)) : (out << "<null>"));
  out << ", " << "productSize="; (__isset.productSize ? (out << to_string(productSize)) : (out << "<null>"));
  out << ", " << "creationTime="; (__isset.creationTime ? (out << to_string(creationTime)) : (out << "<null>"));
  out << ", " << "lastModifiedTime="; (__isset.lastModifiedTime ? (out << to_string(lastModifiedTime)) : (out << "<null>"));
  out << ", " << "productMetadata="; (__isset.productMetadata ? (out << to_string(productMetadata)) : (out << "<null>"));
  out << ", " << "replicaLocations="; (__isset.replicaLocations ? (out << to_string(replicaLocations)) : (out << "<null>"));
  out << ")";
}

}}}}} // namespace
