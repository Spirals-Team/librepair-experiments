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
#ifndef sharing_models_TYPES_H
#define sharing_models_TYPES_H

#include <iosfwd>

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <thrift/cxxfunctional.h>




struct GroupCardinality {
  enum type {
    SINGLE_USER = 0,
    MULTI_USER = 1
  };
};

extern const std::map<int, const char*> _GroupCardinality_VALUES_TO_NAMES;

struct GroupType {
  enum type {
    DOMAIN_LEVEL_GROUP = 0,
    USER_LEVEL_GROUP = 1
  };
};

extern const std::map<int, const char*> _GroupType_VALUES_TO_NAMES;

struct GroupChildType {
  enum type {
    USER = 0,
    GROUP = 1
  };
};

extern const std::map<int, const char*> _GroupChildType_VALUES_TO_NAMES;

struct EntitySearchField {
  enum type {
    NAME = 0,
    DESCRIPTION = 1,
    FULL_TEXT = 2,
    PARRENT_ENTITY_ID = 3,
    OWNER_ID = 4,
    PERMISSION_TYPE_ID = 5,
    CREATED_TIME = 6,
    UPDATED_TIME = 7,
    ENTITY_TYPE_ID = 8,
    SHARED_COUNT = 9
  };
};

extern const std::map<int, const char*> _EntitySearchField_VALUES_TO_NAMES;

struct SearchCondition {
  enum type {
    EQUAL = 0,
    LIKE = 1,
    FULL_TEXT = 2,
    GTE = 3,
    LTE = 4,
    NOT = 5
  };
};

extern const std::map<int, const char*> _SearchCondition_VALUES_TO_NAMES;

struct SharingType {
  enum type {
    DIRECT_NON_CASCADING = 0,
    DIRECT_CASCADING = 1,
    INDIRECT_CASCADING = 2
  };
};

extern const std::map<int, const char*> _SharingType_VALUES_TO_NAMES;

class Domain;

class User;

class UserGroup;

class GroupMembership;

class EntityType;

class SearchCriteria;

class Entity;

class PermissionType;

class Sharing;

class SharingRegistryException;

typedef struct _Domain__isset {
  _Domain__isset() : domainId(true), name(false), description(false), createdTime(false), updatedTime(false) {}
  bool domainId :1;
  bool name :1;
  bool description :1;
  bool createdTime :1;
  bool updatedTime :1;
} _Domain__isset;

class Domain {
 public:

  Domain(const Domain&);
  Domain& operator=(const Domain&);
  Domain() : domainId("DO_NOT_SET_AT_CLIENTS_ID"), name(), description(), createdTime(0), updatedTime(0) {
  }

  virtual ~Domain() throw();
  std::string domainId;
  std::string name;
  std::string description;
  int64_t createdTime;
  int64_t updatedTime;

  _Domain__isset __isset;

  void __set_domainId(const std::string& val);

  void __set_name(const std::string& val);

  void __set_description(const std::string& val);

  void __set_createdTime(const int64_t val);

  void __set_updatedTime(const int64_t val);

  bool operator == (const Domain & rhs) const
  {
    if (__isset.domainId != rhs.__isset.domainId)
      return false;
    else if (__isset.domainId && !(domainId == rhs.domainId))
      return false;
    if (__isset.name != rhs.__isset.name)
      return false;
    else if (__isset.name && !(name == rhs.name))
      return false;
    if (__isset.description != rhs.__isset.description)
      return false;
    else if (__isset.description && !(description == rhs.description))
      return false;
    if (__isset.createdTime != rhs.__isset.createdTime)
      return false;
    else if (__isset.createdTime && !(createdTime == rhs.createdTime))
      return false;
    if (__isset.updatedTime != rhs.__isset.updatedTime)
      return false;
    else if (__isset.updatedTime && !(updatedTime == rhs.updatedTime))
      return false;
    return true;
  }
  bool operator != (const Domain &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const Domain & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(Domain &a, Domain &b);

inline std::ostream& operator<<(std::ostream& out, const Domain& obj)
{
  obj.printTo(out);
  return out;
}

typedef struct _User__isset {
  _User__isset() : userId(false), domainId(false), userName(false), firstName(false), lastName(false), email(false), icon(false), createdTime(false), updatedTime(false) {}
  bool userId :1;
  bool domainId :1;
  bool userName :1;
  bool firstName :1;
  bool lastName :1;
  bool email :1;
  bool icon :1;
  bool createdTime :1;
  bool updatedTime :1;
} _User__isset;

class User {
 public:

  User(const User&);
  User& operator=(const User&);
  User() : userId(), domainId(), userName(), firstName(), lastName(), email(), icon(), createdTime(0), updatedTime(0) {
  }

  virtual ~User() throw();
  std::string userId;
  std::string domainId;
  std::string userName;
  std::string firstName;
  std::string lastName;
  std::string email;
  std::string icon;
  int64_t createdTime;
  int64_t updatedTime;

  _User__isset __isset;

  void __set_userId(const std::string& val);

  void __set_domainId(const std::string& val);

  void __set_userName(const std::string& val);

  void __set_firstName(const std::string& val);

  void __set_lastName(const std::string& val);

  void __set_email(const std::string& val);

  void __set_icon(const std::string& val);

  void __set_createdTime(const int64_t val);

  void __set_updatedTime(const int64_t val);

  bool operator == (const User & rhs) const
  {
    if (__isset.userId != rhs.__isset.userId)
      return false;
    else if (__isset.userId && !(userId == rhs.userId))
      return false;
    if (__isset.domainId != rhs.__isset.domainId)
      return false;
    else if (__isset.domainId && !(domainId == rhs.domainId))
      return false;
    if (__isset.userName != rhs.__isset.userName)
      return false;
    else if (__isset.userName && !(userName == rhs.userName))
      return false;
    if (__isset.firstName != rhs.__isset.firstName)
      return false;
    else if (__isset.firstName && !(firstName == rhs.firstName))
      return false;
    if (__isset.lastName != rhs.__isset.lastName)
      return false;
    else if (__isset.lastName && !(lastName == rhs.lastName))
      return false;
    if (__isset.email != rhs.__isset.email)
      return false;
    else if (__isset.email && !(email == rhs.email))
      return false;
    if (__isset.icon != rhs.__isset.icon)
      return false;
    else if (__isset.icon && !(icon == rhs.icon))
      return false;
    if (__isset.createdTime != rhs.__isset.createdTime)
      return false;
    else if (__isset.createdTime && !(createdTime == rhs.createdTime))
      return false;
    if (__isset.updatedTime != rhs.__isset.updatedTime)
      return false;
    else if (__isset.updatedTime && !(updatedTime == rhs.updatedTime))
      return false;
    return true;
  }
  bool operator != (const User &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const User & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(User &a, User &b);

inline std::ostream& operator<<(std::ostream& out, const User& obj)
{
  obj.printTo(out);
  return out;
}

typedef struct _UserGroup__isset {
  _UserGroup__isset() : groupId(false), domainId(false), name(false), description(false), ownerId(false), groupType(false), groupCardinality(false), createdTime(false), updatedTime(false) {}
  bool groupId :1;
  bool domainId :1;
  bool name :1;
  bool description :1;
  bool ownerId :1;
  bool groupType :1;
  bool groupCardinality :1;
  bool createdTime :1;
  bool updatedTime :1;
} _UserGroup__isset;

class UserGroup {
 public:

  UserGroup(const UserGroup&);
  UserGroup& operator=(const UserGroup&);
  UserGroup() : groupId(), domainId(), name(), description(), ownerId(), groupType((GroupType::type)0), groupCardinality((GroupCardinality::type)0), createdTime(0), updatedTime(0) {
  }

  virtual ~UserGroup() throw();
  std::string groupId;
  std::string domainId;
  std::string name;
  std::string description;
  std::string ownerId;
  GroupType::type groupType;
  GroupCardinality::type groupCardinality;
  int64_t createdTime;
  int64_t updatedTime;

  _UserGroup__isset __isset;

  void __set_groupId(const std::string& val);

  void __set_domainId(const std::string& val);

  void __set_name(const std::string& val);

  void __set_description(const std::string& val);

  void __set_ownerId(const std::string& val);

  void __set_groupType(const GroupType::type val);

  void __set_groupCardinality(const GroupCardinality::type val);

  void __set_createdTime(const int64_t val);

  void __set_updatedTime(const int64_t val);

  bool operator == (const UserGroup & rhs) const
  {
    if (__isset.groupId != rhs.__isset.groupId)
      return false;
    else if (__isset.groupId && !(groupId == rhs.groupId))
      return false;
    if (__isset.domainId != rhs.__isset.domainId)
      return false;
    else if (__isset.domainId && !(domainId == rhs.domainId))
      return false;
    if (__isset.name != rhs.__isset.name)
      return false;
    else if (__isset.name && !(name == rhs.name))
      return false;
    if (__isset.description != rhs.__isset.description)
      return false;
    else if (__isset.description && !(description == rhs.description))
      return false;
    if (__isset.ownerId != rhs.__isset.ownerId)
      return false;
    else if (__isset.ownerId && !(ownerId == rhs.ownerId))
      return false;
    if (__isset.groupType != rhs.__isset.groupType)
      return false;
    else if (__isset.groupType && !(groupType == rhs.groupType))
      return false;
    if (__isset.groupCardinality != rhs.__isset.groupCardinality)
      return false;
    else if (__isset.groupCardinality && !(groupCardinality == rhs.groupCardinality))
      return false;
    if (__isset.createdTime != rhs.__isset.createdTime)
      return false;
    else if (__isset.createdTime && !(createdTime == rhs.createdTime))
      return false;
    if (__isset.updatedTime != rhs.__isset.updatedTime)
      return false;
    else if (__isset.updatedTime && !(updatedTime == rhs.updatedTime))
      return false;
    return true;
  }
  bool operator != (const UserGroup &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const UserGroup & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(UserGroup &a, UserGroup &b);

inline std::ostream& operator<<(std::ostream& out, const UserGroup& obj)
{
  obj.printTo(out);
  return out;
}

typedef struct _GroupMembership__isset {
  _GroupMembership__isset() : parentId(false), childId(false), domainId(false), childType(false), createdTime(false), updatedTime(false) {}
  bool parentId :1;
  bool childId :1;
  bool domainId :1;
  bool childType :1;
  bool createdTime :1;
  bool updatedTime :1;
} _GroupMembership__isset;

class GroupMembership {
 public:

  GroupMembership(const GroupMembership&);
  GroupMembership& operator=(const GroupMembership&);
  GroupMembership() : parentId(), childId(), domainId(), childType((GroupChildType::type)0), createdTime(0), updatedTime(0) {
  }

  virtual ~GroupMembership() throw();
  std::string parentId;
  std::string childId;
  std::string domainId;
  GroupChildType::type childType;
  int64_t createdTime;
  int64_t updatedTime;

  _GroupMembership__isset __isset;

  void __set_parentId(const std::string& val);

  void __set_childId(const std::string& val);

  void __set_domainId(const std::string& val);

  void __set_childType(const GroupChildType::type val);

  void __set_createdTime(const int64_t val);

  void __set_updatedTime(const int64_t val);

  bool operator == (const GroupMembership & rhs) const
  {
    if (__isset.parentId != rhs.__isset.parentId)
      return false;
    else if (__isset.parentId && !(parentId == rhs.parentId))
      return false;
    if (__isset.childId != rhs.__isset.childId)
      return false;
    else if (__isset.childId && !(childId == rhs.childId))
      return false;
    if (__isset.domainId != rhs.__isset.domainId)
      return false;
    else if (__isset.domainId && !(domainId == rhs.domainId))
      return false;
    if (__isset.childType != rhs.__isset.childType)
      return false;
    else if (__isset.childType && !(childType == rhs.childType))
      return false;
    if (__isset.createdTime != rhs.__isset.createdTime)
      return false;
    else if (__isset.createdTime && !(createdTime == rhs.createdTime))
      return false;
    if (__isset.updatedTime != rhs.__isset.updatedTime)
      return false;
    else if (__isset.updatedTime && !(updatedTime == rhs.updatedTime))
      return false;
    return true;
  }
  bool operator != (const GroupMembership &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const GroupMembership & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(GroupMembership &a, GroupMembership &b);

inline std::ostream& operator<<(std::ostream& out, const GroupMembership& obj)
{
  obj.printTo(out);
  return out;
}

typedef struct _EntityType__isset {
  _EntityType__isset() : entityTypeId(false), domainId(false), name(false), description(false), createdTime(false), updatedTime(false) {}
  bool entityTypeId :1;
  bool domainId :1;
  bool name :1;
  bool description :1;
  bool createdTime :1;
  bool updatedTime :1;
} _EntityType__isset;

class EntityType {
 public:

  EntityType(const EntityType&);
  EntityType& operator=(const EntityType&);
  EntityType() : entityTypeId(), domainId(), name(), description(), createdTime(0), updatedTime(0) {
  }

  virtual ~EntityType() throw();
  std::string entityTypeId;
  std::string domainId;
  std::string name;
  std::string description;
  int64_t createdTime;
  int64_t updatedTime;

  _EntityType__isset __isset;

  void __set_entityTypeId(const std::string& val);

  void __set_domainId(const std::string& val);

  void __set_name(const std::string& val);

  void __set_description(const std::string& val);

  void __set_createdTime(const int64_t val);

  void __set_updatedTime(const int64_t val);

  bool operator == (const EntityType & rhs) const
  {
    if (__isset.entityTypeId != rhs.__isset.entityTypeId)
      return false;
    else if (__isset.entityTypeId && !(entityTypeId == rhs.entityTypeId))
      return false;
    if (__isset.domainId != rhs.__isset.domainId)
      return false;
    else if (__isset.domainId && !(domainId == rhs.domainId))
      return false;
    if (__isset.name != rhs.__isset.name)
      return false;
    else if (__isset.name && !(name == rhs.name))
      return false;
    if (__isset.description != rhs.__isset.description)
      return false;
    else if (__isset.description && !(description == rhs.description))
      return false;
    if (__isset.createdTime != rhs.__isset.createdTime)
      return false;
    else if (__isset.createdTime && !(createdTime == rhs.createdTime))
      return false;
    if (__isset.updatedTime != rhs.__isset.updatedTime)
      return false;
    else if (__isset.updatedTime && !(updatedTime == rhs.updatedTime))
      return false;
    return true;
  }
  bool operator != (const EntityType &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const EntityType & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(EntityType &a, EntityType &b);

inline std::ostream& operator<<(std::ostream& out, const EntityType& obj)
{
  obj.printTo(out);
  return out;
}

typedef struct _SearchCriteria__isset {
  _SearchCriteria__isset() : searchField(false), value(false), searchCondition(false) {}
  bool searchField :1;
  bool value :1;
  bool searchCondition :1;
} _SearchCriteria__isset;

class SearchCriteria {
 public:

  SearchCriteria(const SearchCriteria&);
  SearchCriteria& operator=(const SearchCriteria&);
  SearchCriteria() : searchField((EntitySearchField::type)0), value(), searchCondition((SearchCondition::type)0) {
  }

  virtual ~SearchCriteria() throw();
  EntitySearchField::type searchField;
  std::string value;
  SearchCondition::type searchCondition;

  _SearchCriteria__isset __isset;

  void __set_searchField(const EntitySearchField::type val);

  void __set_value(const std::string& val);

  void __set_searchCondition(const SearchCondition::type val);

  bool operator == (const SearchCriteria & rhs) const
  {
    if (__isset.searchField != rhs.__isset.searchField)
      return false;
    else if (__isset.searchField && !(searchField == rhs.searchField))
      return false;
    if (__isset.value != rhs.__isset.value)
      return false;
    else if (__isset.value && !(value == rhs.value))
      return false;
    if (__isset.searchCondition != rhs.__isset.searchCondition)
      return false;
    else if (__isset.searchCondition && !(searchCondition == rhs.searchCondition))
      return false;
    return true;
  }
  bool operator != (const SearchCriteria &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const SearchCriteria & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(SearchCriteria &a, SearchCriteria &b);

inline std::ostream& operator<<(std::ostream& out, const SearchCriteria& obj)
{
  obj.printTo(out);
  return out;
}

typedef struct _Entity__isset {
  _Entity__isset() : entityId(false), domainId(false), entityTypeId(false), ownerId(false), parentEntityId(false), name(false), description(false), binaryData(false), fullText(false), sharedCount(true), originalEntityCreationTime(false), createdTime(false), updatedTime(false) {}
  bool entityId :1;
  bool domainId :1;
  bool entityTypeId :1;
  bool ownerId :1;
  bool parentEntityId :1;
  bool name :1;
  bool description :1;
  bool binaryData :1;
  bool fullText :1;
  bool sharedCount :1;
  bool originalEntityCreationTime :1;
  bool createdTime :1;
  bool updatedTime :1;
} _Entity__isset;

class Entity {
 public:

  Entity(const Entity&);
  Entity& operator=(const Entity&);
  Entity() : entityId(), domainId(), entityTypeId(), ownerId(), parentEntityId(), name(), description(), binaryData(), fullText(), sharedCount(0LL), originalEntityCreationTime(0), createdTime(0), updatedTime(0) {
  }

  virtual ~Entity() throw();
  std::string entityId;
  std::string domainId;
  std::string entityTypeId;
  std::string ownerId;
  std::string parentEntityId;
  std::string name;
  std::string description;
  std::string binaryData;
  std::string fullText;
  int64_t sharedCount;
  int64_t originalEntityCreationTime;
  int64_t createdTime;
  int64_t updatedTime;

  _Entity__isset __isset;

  void __set_entityId(const std::string& val);

  void __set_domainId(const std::string& val);

  void __set_entityTypeId(const std::string& val);

  void __set_ownerId(const std::string& val);

  void __set_parentEntityId(const std::string& val);

  void __set_name(const std::string& val);

  void __set_description(const std::string& val);

  void __set_binaryData(const std::string& val);

  void __set_fullText(const std::string& val);

  void __set_sharedCount(const int64_t val);

  void __set_originalEntityCreationTime(const int64_t val);

  void __set_createdTime(const int64_t val);

  void __set_updatedTime(const int64_t val);

  bool operator == (const Entity & rhs) const
  {
    if (__isset.entityId != rhs.__isset.entityId)
      return false;
    else if (__isset.entityId && !(entityId == rhs.entityId))
      return false;
    if (__isset.domainId != rhs.__isset.domainId)
      return false;
    else if (__isset.domainId && !(domainId == rhs.domainId))
      return false;
    if (__isset.entityTypeId != rhs.__isset.entityTypeId)
      return false;
    else if (__isset.entityTypeId && !(entityTypeId == rhs.entityTypeId))
      return false;
    if (__isset.ownerId != rhs.__isset.ownerId)
      return false;
    else if (__isset.ownerId && !(ownerId == rhs.ownerId))
      return false;
    if (__isset.parentEntityId != rhs.__isset.parentEntityId)
      return false;
    else if (__isset.parentEntityId && !(parentEntityId == rhs.parentEntityId))
      return false;
    if (__isset.name != rhs.__isset.name)
      return false;
    else if (__isset.name && !(name == rhs.name))
      return false;
    if (__isset.description != rhs.__isset.description)
      return false;
    else if (__isset.description && !(description == rhs.description))
      return false;
    if (__isset.binaryData != rhs.__isset.binaryData)
      return false;
    else if (__isset.binaryData && !(binaryData == rhs.binaryData))
      return false;
    if (__isset.fullText != rhs.__isset.fullText)
      return false;
    else if (__isset.fullText && !(fullText == rhs.fullText))
      return false;
    if (__isset.sharedCount != rhs.__isset.sharedCount)
      return false;
    else if (__isset.sharedCount && !(sharedCount == rhs.sharedCount))
      return false;
    if (__isset.originalEntityCreationTime != rhs.__isset.originalEntityCreationTime)
      return false;
    else if (__isset.originalEntityCreationTime && !(originalEntityCreationTime == rhs.originalEntityCreationTime))
      return false;
    if (__isset.createdTime != rhs.__isset.createdTime)
      return false;
    else if (__isset.createdTime && !(createdTime == rhs.createdTime))
      return false;
    if (__isset.updatedTime != rhs.__isset.updatedTime)
      return false;
    else if (__isset.updatedTime && !(updatedTime == rhs.updatedTime))
      return false;
    return true;
  }
  bool operator != (const Entity &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const Entity & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(Entity &a, Entity &b);

inline std::ostream& operator<<(std::ostream& out, const Entity& obj)
{
  obj.printTo(out);
  return out;
}

typedef struct _PermissionType__isset {
  _PermissionType__isset() : permissionTypeId(false), domainId(false), name(false), description(false), createdTime(false), updatedTime(false) {}
  bool permissionTypeId :1;
  bool domainId :1;
  bool name :1;
  bool description :1;
  bool createdTime :1;
  bool updatedTime :1;
} _PermissionType__isset;

class PermissionType {
 public:

  PermissionType(const PermissionType&);
  PermissionType& operator=(const PermissionType&);
  PermissionType() : permissionTypeId(), domainId(), name(), description(), createdTime(0), updatedTime(0) {
  }

  virtual ~PermissionType() throw();
  std::string permissionTypeId;
  std::string domainId;
  std::string name;
  std::string description;
  int64_t createdTime;
  int64_t updatedTime;

  _PermissionType__isset __isset;

  void __set_permissionTypeId(const std::string& val);

  void __set_domainId(const std::string& val);

  void __set_name(const std::string& val);

  void __set_description(const std::string& val);

  void __set_createdTime(const int64_t val);

  void __set_updatedTime(const int64_t val);

  bool operator == (const PermissionType & rhs) const
  {
    if (__isset.permissionTypeId != rhs.__isset.permissionTypeId)
      return false;
    else if (__isset.permissionTypeId && !(permissionTypeId == rhs.permissionTypeId))
      return false;
    if (__isset.domainId != rhs.__isset.domainId)
      return false;
    else if (__isset.domainId && !(domainId == rhs.domainId))
      return false;
    if (__isset.name != rhs.__isset.name)
      return false;
    else if (__isset.name && !(name == rhs.name))
      return false;
    if (__isset.description != rhs.__isset.description)
      return false;
    else if (__isset.description && !(description == rhs.description))
      return false;
    if (__isset.createdTime != rhs.__isset.createdTime)
      return false;
    else if (__isset.createdTime && !(createdTime == rhs.createdTime))
      return false;
    if (__isset.updatedTime != rhs.__isset.updatedTime)
      return false;
    else if (__isset.updatedTime && !(updatedTime == rhs.updatedTime))
      return false;
    return true;
  }
  bool operator != (const PermissionType &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const PermissionType & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(PermissionType &a, PermissionType &b);

inline std::ostream& operator<<(std::ostream& out, const PermissionType& obj)
{
  obj.printTo(out);
  return out;
}

typedef struct _Sharing__isset {
  _Sharing__isset() : permissionTypeId(false), entityId(false), groupId(false), sharingType(false), domainId(false), inheritedParentId(false), createdTime(false), updatedTime(false) {}
  bool permissionTypeId :1;
  bool entityId :1;
  bool groupId :1;
  bool sharingType :1;
  bool domainId :1;
  bool inheritedParentId :1;
  bool createdTime :1;
  bool updatedTime :1;
} _Sharing__isset;

class Sharing {
 public:

  Sharing(const Sharing&);
  Sharing& operator=(const Sharing&);
  Sharing() : permissionTypeId(), entityId(), groupId(), sharingType((SharingType::type)0), domainId(), inheritedParentId(), createdTime(0), updatedTime(0) {
  }

  virtual ~Sharing() throw();
  std::string permissionTypeId;
  std::string entityId;
  std::string groupId;
  SharingType::type sharingType;
  std::string domainId;
  std::string inheritedParentId;
  int64_t createdTime;
  int64_t updatedTime;

  _Sharing__isset __isset;

  void __set_permissionTypeId(const std::string& val);

  void __set_entityId(const std::string& val);

  void __set_groupId(const std::string& val);

  void __set_sharingType(const SharingType::type val);

  void __set_domainId(const std::string& val);

  void __set_inheritedParentId(const std::string& val);

  void __set_createdTime(const int64_t val);

  void __set_updatedTime(const int64_t val);

  bool operator == (const Sharing & rhs) const
  {
    if (__isset.permissionTypeId != rhs.__isset.permissionTypeId)
      return false;
    else if (__isset.permissionTypeId && !(permissionTypeId == rhs.permissionTypeId))
      return false;
    if (__isset.entityId != rhs.__isset.entityId)
      return false;
    else if (__isset.entityId && !(entityId == rhs.entityId))
      return false;
    if (__isset.groupId != rhs.__isset.groupId)
      return false;
    else if (__isset.groupId && !(groupId == rhs.groupId))
      return false;
    if (__isset.sharingType != rhs.__isset.sharingType)
      return false;
    else if (__isset.sharingType && !(sharingType == rhs.sharingType))
      return false;
    if (__isset.domainId != rhs.__isset.domainId)
      return false;
    else if (__isset.domainId && !(domainId == rhs.domainId))
      return false;
    if (__isset.inheritedParentId != rhs.__isset.inheritedParentId)
      return false;
    else if (__isset.inheritedParentId && !(inheritedParentId == rhs.inheritedParentId))
      return false;
    if (__isset.createdTime != rhs.__isset.createdTime)
      return false;
    else if (__isset.createdTime && !(createdTime == rhs.createdTime))
      return false;
    if (__isset.updatedTime != rhs.__isset.updatedTime)
      return false;
    else if (__isset.updatedTime && !(updatedTime == rhs.updatedTime))
      return false;
    return true;
  }
  bool operator != (const Sharing &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const Sharing & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(Sharing &a, Sharing &b);

inline std::ostream& operator<<(std::ostream& out, const Sharing& obj)
{
  obj.printTo(out);
  return out;
}


class SharingRegistryException : public ::apache::thrift::TException {
 public:

  SharingRegistryException(const SharingRegistryException&);
  SharingRegistryException& operator=(const SharingRegistryException&);
  SharingRegistryException() : message() {
  }

  virtual ~SharingRegistryException() throw();
  std::string message;

  void __set_message(const std::string& val);

  bool operator == (const SharingRegistryException & rhs) const
  {
    if (!(message == rhs.message))
      return false;
    return true;
  }
  bool operator != (const SharingRegistryException &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const SharingRegistryException & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
  mutable std::string thriftTExceptionMessageHolder_;
  const char* what() const throw();
};

void swap(SharingRegistryException &a, SharingRegistryException &b);

inline std::ostream& operator<<(std::ostream& out, const SharingRegistryException& obj)
{
  obj.printTo(out);
  return out;
}



#endif
