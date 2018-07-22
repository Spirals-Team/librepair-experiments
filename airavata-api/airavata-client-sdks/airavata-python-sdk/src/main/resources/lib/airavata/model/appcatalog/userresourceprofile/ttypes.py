#
# Autogenerated by Thrift Compiler (0.10.0)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TFrozenDict, TException, TApplicationException
from thrift.protocol.TProtocol import TProtocolException
import sys
import airavata.model.appcatalog.computeresource.ttypes
import airavata.model.data.movement.ttypes
import airavata.model.user.ttypes

from thrift.transport import TTransport


class UserComputeResourcePreference(object):
    """
    User specific preferences for a Computer Resource

    computeResourceId:
      Corelate the preference to a compute resource.


    loginUserName:
      If turned true, Airavata will override the preferences of better alternatives exist.


    preferredBatchQueue:
     Gateways can choose a defualt batch queue based on average job dimention, reservations or other metrics.

    scratchLocation:
     Path to the local scratch space on a HPC cluster. Typically used to create working directory for job execution.

    allocationProjectNumber:
     Typically used on HPC machines to charge computing usage to a account number. For instance, on XSEDE once an
       allocation is approved, an allocation number is assigned. Before passing this number with job submittions, the
       account to be used has to be added to the allocation.

    resourceSpecificCredentialStoreToken:
     Resource specific credential store token. If this token is specified, then it is superceeded by the gateway's
      default credential store.

    validated:
     If true the the configuration has been validated in the sense that the username and credential can be used to
     login to the remote host and the scratchLocation is a valid location that the user has permission to write to.
     Should be treated as read-only and only mutated by Airavata middleware.


    Attributes:
     - computeResourceId
     - loginUserName
     - preferredBatchQueue
     - scratchLocation
     - allocationProjectNumber
     - resourceSpecificCredentialStoreToken
     - qualityOfService
     - reservation
     - reservationStartTime
     - reservationEndTime
     - validated
    """

    thrift_spec = (
        None,  # 0
        (1, TType.STRING, 'computeResourceId', 'UTF8', None, ),  # 1
        (2, TType.STRING, 'loginUserName', 'UTF8', None, ),  # 2
        (3, TType.STRING, 'preferredBatchQueue', 'UTF8', None, ),  # 3
        (4, TType.STRING, 'scratchLocation', 'UTF8', None, ),  # 4
        (5, TType.STRING, 'allocationProjectNumber', 'UTF8', None, ),  # 5
        (6, TType.STRING, 'resourceSpecificCredentialStoreToken', 'UTF8', None, ),  # 6
        (7, TType.STRING, 'qualityOfService', 'UTF8', None, ),  # 7
        (8, TType.STRING, 'reservation', 'UTF8', None, ),  # 8
        (9, TType.I64, 'reservationStartTime', None, None, ),  # 9
        (10, TType.I64, 'reservationEndTime', None, None, ),  # 10
        (11, TType.BOOL, 'validated', None, False, ),  # 11
    )

    def __init__(self, computeResourceId=None, loginUserName=None, preferredBatchQueue=None, scratchLocation=None, allocationProjectNumber=None, resourceSpecificCredentialStoreToken=None, qualityOfService=None, reservation=None, reservationStartTime=None, reservationEndTime=None, validated=thrift_spec[11][4],):
        self.computeResourceId = computeResourceId
        self.loginUserName = loginUserName
        self.preferredBatchQueue = preferredBatchQueue
        self.scratchLocation = scratchLocation
        self.allocationProjectNumber = allocationProjectNumber
        self.resourceSpecificCredentialStoreToken = resourceSpecificCredentialStoreToken
        self.qualityOfService = qualityOfService
        self.reservation = reservation
        self.reservationStartTime = reservationStartTime
        self.reservationEndTime = reservationEndTime
        self.validated = validated

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, (self.__class__, self.thrift_spec))
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRING:
                    self.computeResourceId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.loginUserName = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRING:
                    self.preferredBatchQueue = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.STRING:
                    self.scratchLocation = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 5:
                if ftype == TType.STRING:
                    self.allocationProjectNumber = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 6:
                if ftype == TType.STRING:
                    self.resourceSpecificCredentialStoreToken = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 7:
                if ftype == TType.STRING:
                    self.qualityOfService = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 8:
                if ftype == TType.STRING:
                    self.reservation = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 9:
                if ftype == TType.I64:
                    self.reservationStartTime = iprot.readI64()
                else:
                    iprot.skip(ftype)
            elif fid == 10:
                if ftype == TType.I64:
                    self.reservationEndTime = iprot.readI64()
                else:
                    iprot.skip(ftype)
            elif fid == 11:
                if ftype == TType.BOOL:
                    self.validated = iprot.readBool()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, (self.__class__, self.thrift_spec)))
            return
        oprot.writeStructBegin('UserComputeResourcePreference')
        if self.computeResourceId is not None:
            oprot.writeFieldBegin('computeResourceId', TType.STRING, 1)
            oprot.writeString(self.computeResourceId.encode('utf-8') if sys.version_info[0] == 2 else self.computeResourceId)
            oprot.writeFieldEnd()
        if self.loginUserName is not None:
            oprot.writeFieldBegin('loginUserName', TType.STRING, 2)
            oprot.writeString(self.loginUserName.encode('utf-8') if sys.version_info[0] == 2 else self.loginUserName)
            oprot.writeFieldEnd()
        if self.preferredBatchQueue is not None:
            oprot.writeFieldBegin('preferredBatchQueue', TType.STRING, 3)
            oprot.writeString(self.preferredBatchQueue.encode('utf-8') if sys.version_info[0] == 2 else self.preferredBatchQueue)
            oprot.writeFieldEnd()
        if self.scratchLocation is not None:
            oprot.writeFieldBegin('scratchLocation', TType.STRING, 4)
            oprot.writeString(self.scratchLocation.encode('utf-8') if sys.version_info[0] == 2 else self.scratchLocation)
            oprot.writeFieldEnd()
        if self.allocationProjectNumber is not None:
            oprot.writeFieldBegin('allocationProjectNumber', TType.STRING, 5)
            oprot.writeString(self.allocationProjectNumber.encode('utf-8') if sys.version_info[0] == 2 else self.allocationProjectNumber)
            oprot.writeFieldEnd()
        if self.resourceSpecificCredentialStoreToken is not None:
            oprot.writeFieldBegin('resourceSpecificCredentialStoreToken', TType.STRING, 6)
            oprot.writeString(self.resourceSpecificCredentialStoreToken.encode('utf-8') if sys.version_info[0] == 2 else self.resourceSpecificCredentialStoreToken)
            oprot.writeFieldEnd()
        if self.qualityOfService is not None:
            oprot.writeFieldBegin('qualityOfService', TType.STRING, 7)
            oprot.writeString(self.qualityOfService.encode('utf-8') if sys.version_info[0] == 2 else self.qualityOfService)
            oprot.writeFieldEnd()
        if self.reservation is not None:
            oprot.writeFieldBegin('reservation', TType.STRING, 8)
            oprot.writeString(self.reservation.encode('utf-8') if sys.version_info[0] == 2 else self.reservation)
            oprot.writeFieldEnd()
        if self.reservationStartTime is not None:
            oprot.writeFieldBegin('reservationStartTime', TType.I64, 9)
            oprot.writeI64(self.reservationStartTime)
            oprot.writeFieldEnd()
        if self.reservationEndTime is not None:
            oprot.writeFieldBegin('reservationEndTime', TType.I64, 10)
            oprot.writeI64(self.reservationEndTime)
            oprot.writeFieldEnd()
        if self.validated is not None:
            oprot.writeFieldBegin('validated', TType.BOOL, 11)
            oprot.writeBool(self.validated)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.computeResourceId is None:
            raise TProtocolException(message='Required field computeResourceId is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class UserStoragePreference(object):
    """
    Attributes:
     - storageResourceId
     - loginUserName
     - fileSystemRootLocation
     - resourceSpecificCredentialStoreToken
    """

    thrift_spec = (
        None,  # 0
        (1, TType.STRING, 'storageResourceId', 'UTF8', None, ),  # 1
        (2, TType.STRING, 'loginUserName', 'UTF8', None, ),  # 2
        (3, TType.STRING, 'fileSystemRootLocation', 'UTF8', None, ),  # 3
        (4, TType.STRING, 'resourceSpecificCredentialStoreToken', 'UTF8', None, ),  # 4
    )

    def __init__(self, storageResourceId=None, loginUserName=None, fileSystemRootLocation=None, resourceSpecificCredentialStoreToken=None,):
        self.storageResourceId = storageResourceId
        self.loginUserName = loginUserName
        self.fileSystemRootLocation = fileSystemRootLocation
        self.resourceSpecificCredentialStoreToken = resourceSpecificCredentialStoreToken

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, (self.__class__, self.thrift_spec))
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRING:
                    self.storageResourceId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.loginUserName = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRING:
                    self.fileSystemRootLocation = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.STRING:
                    self.resourceSpecificCredentialStoreToken = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, (self.__class__, self.thrift_spec)))
            return
        oprot.writeStructBegin('UserStoragePreference')
        if self.storageResourceId is not None:
            oprot.writeFieldBegin('storageResourceId', TType.STRING, 1)
            oprot.writeString(self.storageResourceId.encode('utf-8') if sys.version_info[0] == 2 else self.storageResourceId)
            oprot.writeFieldEnd()
        if self.loginUserName is not None:
            oprot.writeFieldBegin('loginUserName', TType.STRING, 2)
            oprot.writeString(self.loginUserName.encode('utf-8') if sys.version_info[0] == 2 else self.loginUserName)
            oprot.writeFieldEnd()
        if self.fileSystemRootLocation is not None:
            oprot.writeFieldBegin('fileSystemRootLocation', TType.STRING, 3)
            oprot.writeString(self.fileSystemRootLocation.encode('utf-8') if sys.version_info[0] == 2 else self.fileSystemRootLocation)
            oprot.writeFieldEnd()
        if self.resourceSpecificCredentialStoreToken is not None:
            oprot.writeFieldBegin('resourceSpecificCredentialStoreToken', TType.STRING, 4)
            oprot.writeString(self.resourceSpecificCredentialStoreToken.encode('utf-8') if sys.version_info[0] == 2 else self.resourceSpecificCredentialStoreToken)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.storageResourceId is None:
            raise TProtocolException(message='Required field storageResourceId is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class UserResourceProfile(object):
    """
    User Resource Profile

    userId:
    Unique identifier used to link user to corresponding user data model

    gatewayID:
     Unique identifier for the gateway assigned by Airavata. Corelate this to Airavata Admin API Gateway Registration.

    credentialStoreToken:
     Gateway's defualt credential store token.

    computeResourcePreferences:
     List of resource preferences for each of the registered compute resources.

     identityServerTenant:

     identityServerPwdCredToken:


    Attributes:
     - userId
     - gatewayID
     - credentialStoreToken
     - userComputeResourcePreferences
     - userStoragePreferences
     - identityServerTenant
     - identityServerPwdCredToken
    """

    thrift_spec = (
        None,  # 0
        (1, TType.STRING, 'userId', 'UTF8', None, ),  # 1
        (2, TType.STRING, 'gatewayID', 'UTF8', None, ),  # 2
        (3, TType.STRING, 'credentialStoreToken', 'UTF8', None, ),  # 3
        (4, TType.LIST, 'userComputeResourcePreferences', (TType.STRUCT, (UserComputeResourcePreference, UserComputeResourcePreference.thrift_spec), False), None, ),  # 4
        (5, TType.LIST, 'userStoragePreferences', (TType.STRUCT, (UserStoragePreference, UserStoragePreference.thrift_spec), False), None, ),  # 5
        (6, TType.STRING, 'identityServerTenant', 'UTF8', None, ),  # 6
        (7, TType.STRING, 'identityServerPwdCredToken', 'UTF8', None, ),  # 7
    )

    def __init__(self, userId=None, gatewayID=None, credentialStoreToken=None, userComputeResourcePreferences=None, userStoragePreferences=None, identityServerTenant=None, identityServerPwdCredToken=None,):
        self.userId = userId
        self.gatewayID = gatewayID
        self.credentialStoreToken = credentialStoreToken
        self.userComputeResourcePreferences = userComputeResourcePreferences
        self.userStoragePreferences = userStoragePreferences
        self.identityServerTenant = identityServerTenant
        self.identityServerPwdCredToken = identityServerPwdCredToken

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, (self.__class__, self.thrift_spec))
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRING:
                    self.userId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.gatewayID = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRING:
                    self.credentialStoreToken = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.LIST:
                    self.userComputeResourcePreferences = []
                    (_etype3, _size0) = iprot.readListBegin()
                    for _i4 in range(_size0):
                        _elem5 = UserComputeResourcePreference()
                        _elem5.read(iprot)
                        self.userComputeResourcePreferences.append(_elem5)
                    iprot.readListEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 5:
                if ftype == TType.LIST:
                    self.userStoragePreferences = []
                    (_etype9, _size6) = iprot.readListBegin()
                    for _i10 in range(_size6):
                        _elem11 = UserStoragePreference()
                        _elem11.read(iprot)
                        self.userStoragePreferences.append(_elem11)
                    iprot.readListEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 6:
                if ftype == TType.STRING:
                    self.identityServerTenant = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 7:
                if ftype == TType.STRING:
                    self.identityServerPwdCredToken = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, (self.__class__, self.thrift_spec)))
            return
        oprot.writeStructBegin('UserResourceProfile')
        if self.userId is not None:
            oprot.writeFieldBegin('userId', TType.STRING, 1)
            oprot.writeString(self.userId.encode('utf-8') if sys.version_info[0] == 2 else self.userId)
            oprot.writeFieldEnd()
        if self.gatewayID is not None:
            oprot.writeFieldBegin('gatewayID', TType.STRING, 2)
            oprot.writeString(self.gatewayID.encode('utf-8') if sys.version_info[0] == 2 else self.gatewayID)
            oprot.writeFieldEnd()
        if self.credentialStoreToken is not None:
            oprot.writeFieldBegin('credentialStoreToken', TType.STRING, 3)
            oprot.writeString(self.credentialStoreToken.encode('utf-8') if sys.version_info[0] == 2 else self.credentialStoreToken)
            oprot.writeFieldEnd()
        if self.userComputeResourcePreferences is not None:
            oprot.writeFieldBegin('userComputeResourcePreferences', TType.LIST, 4)
            oprot.writeListBegin(TType.STRUCT, len(self.userComputeResourcePreferences))
            for iter12 in self.userComputeResourcePreferences:
                iter12.write(oprot)
            oprot.writeListEnd()
            oprot.writeFieldEnd()
        if self.userStoragePreferences is not None:
            oprot.writeFieldBegin('userStoragePreferences', TType.LIST, 5)
            oprot.writeListBegin(TType.STRUCT, len(self.userStoragePreferences))
            for iter13 in self.userStoragePreferences:
                iter13.write(oprot)
            oprot.writeListEnd()
            oprot.writeFieldEnd()
        if self.identityServerTenant is not None:
            oprot.writeFieldBegin('identityServerTenant', TType.STRING, 6)
            oprot.writeString(self.identityServerTenant.encode('utf-8') if sys.version_info[0] == 2 else self.identityServerTenant)
            oprot.writeFieldEnd()
        if self.identityServerPwdCredToken is not None:
            oprot.writeFieldBegin('identityServerPwdCredToken', TType.STRING, 7)
            oprot.writeString(self.identityServerPwdCredToken.encode('utf-8') if sys.version_info[0] == 2 else self.identityServerPwdCredToken)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.userId is None:
            raise TProtocolException(message='Required field userId is unset!')
        if self.gatewayID is None:
            raise TProtocolException(message='Required field gatewayID is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)
