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
#include "data_catalog_models_types.h"

#include <algorithm>
#include <ostream>

#include <thrift/TToString.h>

namespace apache { namespace airavata { namespace model { namespace data { namespace product {

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
  DataProductType::DIR,
  DataProductType::FILE,
  DataProductType::COLLECTION
};
const char* _kDataProductTypeNames[] = {
  "DIR",
  "FILE",
  "COLLECTION"
};
const std::map<int, const char*> _DataProductType_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(3, _kDataProductTypeValues, _kDataProductTypeNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));


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

void DataProductModel::__set_logicalPath(const std::string& val) {
  this->logicalPath = val;
__isset.logicalPath = true;
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

void DataProductModel::__set_childProducts(const std::vector<DataProductModel> & val) {
  this->childProducts = val;
__isset.childProducts = true;
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
          xfer += iprot->readString(this->logicalPath);
          this->__isset.logicalPath = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->productName);
          this->__isset.productName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->productDescription);
          this->__isset.productDescription = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->ownerName);
          this->__isset.ownerName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 8:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast0;
          xfer += iprot->readI32(ecast0);
          this->dataProductType = (DataProductType::type)ecast0;
          this->__isset.dataProductType = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 9:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          xfer += iprot->readI32(this->productSize);
          this->__isset.productSize = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 10:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->creationTime);
          this->__isset.creationTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 11:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->lastModifiedTime);
          this->__isset.lastModifiedTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 12:
        if (ftype == ::apache::thrift::protocol::T_MAP) {
          {
            this->productMetadata.clear();
            uint32_t _size1;
            ::apache::thrift::protocol::TType _ktype2;
            ::apache::thrift::protocol::TType _vtype3;
            xfer += iprot->readMapBegin(_ktype2, _vtype3, _size1);
            uint32_t _i5;
            for (_i5 = 0; _i5 < _size1; ++_i5)
            {
              std::string _key6;
              xfer += iprot->readString(_key6);
              std::string& _val7 = this->productMetadata[_key6];
              xfer += iprot->readString(_val7);
            }
            xfer += iprot->readMapEnd();
          }
          this->__isset.productMetadata = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 13:
        if (ftype == ::apache::thrift::protocol::T_LIST) {
          {
            this->replicaLocations.clear();
            uint32_t _size8;
            ::apache::thrift::protocol::TType _etype11;
            xfer += iprot->readListBegin(_etype11, _size8);
            this->replicaLocations.resize(_size8);
            uint32_t _i12;
            for (_i12 = 0; _i12 < _size8; ++_i12)
            {
              xfer += this->replicaLocations[_i12].read(iprot);
            }
            xfer += iprot->readListEnd();
          }
          this->__isset.replicaLocations = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 14:
        if (ftype == ::apache::thrift::protocol::T_LIST) {
          {
            this->childProducts.clear();
            uint32_t _size13;
            ::apache::thrift::protocol::TType _etype16;
            xfer += iprot->readListBegin(_etype16, _size13);
            this->childProducts.resize(_size13);
            uint32_t _i17;
            for (_i17 = 0; _i17 < _size13; ++_i17)
            {
              xfer += this->childProducts[_i17].read(iprot);
            }
            xfer += iprot->readListEnd();
          }
          this->__isset.childProducts = true;
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
  if (this->__isset.logicalPath) {
    xfer += oprot->writeFieldBegin("logicalPath", ::apache::thrift::protocol::T_STRING, 4);
    xfer += oprot->writeString(this->logicalPath);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productName) {
    xfer += oprot->writeFieldBegin("productName", ::apache::thrift::protocol::T_STRING, 5);
    xfer += oprot->writeString(this->productName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productDescription) {
    xfer += oprot->writeFieldBegin("productDescription", ::apache::thrift::protocol::T_STRING, 6);
    xfer += oprot->writeString(this->productDescription);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.ownerName) {
    xfer += oprot->writeFieldBegin("ownerName", ::apache::thrift::protocol::T_STRING, 7);
    xfer += oprot->writeString(this->ownerName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.dataProductType) {
    xfer += oprot->writeFieldBegin("dataProductType", ::apache::thrift::protocol::T_I32, 8);
    xfer += oprot->writeI32((int32_t)this->dataProductType);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productSize) {
    xfer += oprot->writeFieldBegin("productSize", ::apache::thrift::protocol::T_I32, 9);
    xfer += oprot->writeI32(this->productSize);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.creationTime) {
    xfer += oprot->writeFieldBegin("creationTime", ::apache::thrift::protocol::T_I64, 10);
    xfer += oprot->writeI64(this->creationTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.lastModifiedTime) {
    xfer += oprot->writeFieldBegin("lastModifiedTime", ::apache::thrift::protocol::T_I64, 11);
    xfer += oprot->writeI64(this->lastModifiedTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.productMetadata) {
    xfer += oprot->writeFieldBegin("productMetadata", ::apache::thrift::protocol::T_MAP, 12);
    {
      xfer += oprot->writeMapBegin(::apache::thrift::protocol::T_STRING, ::apache::thrift::protocol::T_STRING, static_cast<uint32_t>(this->productMetadata.size()));
      std::map<std::string, std::string> ::const_iterator _iter18;
      for (_iter18 = this->productMetadata.begin(); _iter18 != this->productMetadata.end(); ++_iter18)
      {
        xfer += oprot->writeString(_iter18->first);
        xfer += oprot->writeString(_iter18->second);
      }
      xfer += oprot->writeMapEnd();
    }
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.replicaLocations) {
    xfer += oprot->writeFieldBegin("replicaLocations", ::apache::thrift::protocol::T_LIST, 13);
    {
      xfer += oprot->writeListBegin(::apache::thrift::protocol::T_STRUCT, static_cast<uint32_t>(this->replicaLocations.size()));
      std::vector<DataReplicaLocationModel> ::const_iterator _iter19;
      for (_iter19 = this->replicaLocations.begin(); _iter19 != this->replicaLocations.end(); ++_iter19)
      {
        xfer += (*_iter19).write(oprot);
      }
      xfer += oprot->writeListEnd();
    }
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.childProducts) {
    xfer += oprot->writeFieldBegin("childProducts", ::apache::thrift::protocol::T_LIST, 14);
    {
      xfer += oprot->writeListBegin(::apache::thrift::protocol::T_STRUCT, static_cast<uint32_t>(this->childProducts.size()));
      std::vector<DataProductModel> ::const_iterator _iter20;
      for (_iter20 = this->childProducts.begin(); _iter20 != this->childProducts.end(); ++_iter20)
      {
        xfer += (*_iter20).write(oprot);
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
  swap(a.logicalPath, b.logicalPath);
  swap(a.productName, b.productName);
  swap(a.productDescription, b.productDescription);
  swap(a.ownerName, b.ownerName);
  swap(a.dataProductType, b.dataProductType);
  swap(a.productSize, b.productSize);
  swap(a.creationTime, b.creationTime);
  swap(a.lastModifiedTime, b.lastModifiedTime);
  swap(a.productMetadata, b.productMetadata);
  swap(a.replicaLocations, b.replicaLocations);
  swap(a.childProducts, b.childProducts);
  swap(a.__isset, b.__isset);
}

DataProductModel::DataProductModel(const DataProductModel& other21) {
  productUri = other21.productUri;
  gatewayId = other21.gatewayId;
  parentProductUri = other21.parentProductUri;
  logicalPath = other21.logicalPath;
  productName = other21.productName;
  productDescription = other21.productDescription;
  ownerName = other21.ownerName;
  dataProductType = other21.dataProductType;
  productSize = other21.productSize;
  creationTime = other21.creationTime;
  lastModifiedTime = other21.lastModifiedTime;
  productMetadata = other21.productMetadata;
  replicaLocations = other21.replicaLocations;
  childProducts = other21.childProducts;
  __isset = other21.__isset;
}
DataProductModel& DataProductModel::operator=(const DataProductModel& other22) {
  productUri = other22.productUri;
  gatewayId = other22.gatewayId;
  parentProductUri = other22.parentProductUri;
  logicalPath = other22.logicalPath;
  productName = other22.productName;
  productDescription = other22.productDescription;
  ownerName = other22.ownerName;
  dataProductType = other22.dataProductType;
  productSize = other22.productSize;
  creationTime = other22.creationTime;
  lastModifiedTime = other22.lastModifiedTime;
  productMetadata = other22.productMetadata;
  replicaLocations = other22.replicaLocations;
  childProducts = other22.childProducts;
  __isset = other22.__isset;
  return *this;
}
void DataProductModel::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "DataProductModel(";
  out << "productUri="; (__isset.productUri ? (out << to_string(productUri)) : (out << "<null>"));
  out << ", " << "gatewayId="; (__isset.gatewayId ? (out << to_string(gatewayId)) : (out << "<null>"));
  out << ", " << "parentProductUri="; (__isset.parentProductUri ? (out << to_string(parentProductUri)) : (out << "<null>"));
  out << ", " << "logicalPath="; (__isset.logicalPath ? (out << to_string(logicalPath)) : (out << "<null>"));
  out << ", " << "productName="; (__isset.productName ? (out << to_string(productName)) : (out << "<null>"));
  out << ", " << "productDescription="; (__isset.productDescription ? (out << to_string(productDescription)) : (out << "<null>"));
  out << ", " << "ownerName="; (__isset.ownerName ? (out << to_string(ownerName)) : (out << "<null>"));
  out << ", " << "dataProductType="; (__isset.dataProductType ? (out << to_string(dataProductType)) : (out << "<null>"));
  out << ", " << "productSize="; (__isset.productSize ? (out << to_string(productSize)) : (out << "<null>"));
  out << ", " << "creationTime="; (__isset.creationTime ? (out << to_string(creationTime)) : (out << "<null>"));
  out << ", " << "lastModifiedTime="; (__isset.lastModifiedTime ? (out << to_string(lastModifiedTime)) : (out << "<null>"));
  out << ", " << "productMetadata="; (__isset.productMetadata ? (out << to_string(productMetadata)) : (out << "<null>"));
  out << ", " << "replicaLocations="; (__isset.replicaLocations ? (out << to_string(replicaLocations)) : (out << "<null>"));
  out << ", " << "childProducts="; (__isset.childProducts ? (out << to_string(childProducts)) : (out << "<null>"));
  out << ")";
}


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
          int32_t ecast23;
          xfer += iprot->readI32(ecast23);
          this->replicaLocationCategory = (ReplicaLocationCategory::type)ecast23;
          this->__isset.replicaLocationCategory = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 9:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast24;
          xfer += iprot->readI32(ecast24);
          this->replicaPersistentType = (ReplicaPersistentType::type)ecast24;
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
            uint32_t _size25;
            ::apache::thrift::protocol::TType _ktype26;
            ::apache::thrift::protocol::TType _vtype27;
            xfer += iprot->readMapBegin(_ktype26, _vtype27, _size25);
            uint32_t _i29;
            for (_i29 = 0; _i29 < _size25; ++_i29)
            {
              std::string _key30;
              xfer += iprot->readString(_key30);
              std::string& _val31 = this->replicaMetadata[_key30];
              xfer += iprot->readString(_val31);
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
      std::map<std::string, std::string> ::const_iterator _iter32;
      for (_iter32 = this->replicaMetadata.begin(); _iter32 != this->replicaMetadata.end(); ++_iter32)
      {
        xfer += oprot->writeString(_iter32->first);
        xfer += oprot->writeString(_iter32->second);
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

DataReplicaLocationModel::DataReplicaLocationModel(const DataReplicaLocationModel& other33) {
  replicaId = other33.replicaId;
  productUri = other33.productUri;
  replicaName = other33.replicaName;
  replicaDescription = other33.replicaDescription;
  creationTime = other33.creationTime;
  lastModifiedTime = other33.lastModifiedTime;
  validUntilTime = other33.validUntilTime;
  replicaLocationCategory = other33.replicaLocationCategory;
  replicaPersistentType = other33.replicaPersistentType;
  storageResourceId = other33.storageResourceId;
  filePath = other33.filePath;
  replicaMetadata = other33.replicaMetadata;
  __isset = other33.__isset;
}
DataReplicaLocationModel& DataReplicaLocationModel::operator=(const DataReplicaLocationModel& other34) {
  replicaId = other34.replicaId;
  productUri = other34.productUri;
  replicaName = other34.replicaName;
  replicaDescription = other34.replicaDescription;
  creationTime = other34.creationTime;
  lastModifiedTime = other34.lastModifiedTime;
  validUntilTime = other34.validUntilTime;
  replicaLocationCategory = other34.replicaLocationCategory;
  replicaPersistentType = other34.replicaPersistentType;
  storageResourceId = other34.storageResourceId;
  filePath = other34.filePath;
  replicaMetadata = other34.replicaMetadata;
  __isset = other34.__isset;
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

}}}}} // namespace
