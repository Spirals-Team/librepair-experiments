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
import airavata.model.application.io.ttypes
import airavata.model.commons.ttypes

from thrift.transport import TTransport


class WorkflowState(object):
    CREATED = 0
    STARTED = 1
    EXECUTING = 2
    COMPLETED = 3
    FAILED = 4
    CANCELLING = 5
    CANCELED = 6

    _VALUES_TO_NAMES = {
        0: "CREATED",
        1: "STARTED",
        2: "EXECUTING",
        3: "COMPLETED",
        4: "FAILED",
        5: "CANCELLING",
        6: "CANCELED",
    }

    _NAMES_TO_VALUES = {
        "CREATED": 0,
        "STARTED": 1,
        "EXECUTING": 2,
        "COMPLETED": 3,
        "FAILED": 4,
        "CANCELLING": 5,
        "CANCELED": 6,
    }


class ComponentState(object):
    CREATED = 0
    WAITING = 1
    READY = 2
    RUNNING = 3
    COMPLETED = 4
    FAILED = 5
    CANCELED = 6

    _VALUES_TO_NAMES = {
        0: "CREATED",
        1: "WAITING",
        2: "READY",
        3: "RUNNING",
        4: "COMPLETED",
        5: "FAILED",
        6: "CANCELED",
    }

    _NAMES_TO_VALUES = {
        "CREATED": 0,
        "WAITING": 1,
        "READY": 2,
        "RUNNING": 3,
        "COMPLETED": 4,
        "FAILED": 5,
        "CANCELED": 6,
    }


class WorkflowModel(object):
    """
    Attributes:
     - templateId
     - name
     - graph
     - gatewayId
     - createdUser
     - image
     - workflowInputs
     - workflowOutputs
     - creationTime
    """

    thrift_spec = (
        None,  # 0
        (1, TType.STRING, 'templateId', 'UTF8', "DO_NOT_SET_AT_CLIENTS", ),  # 1
        (2, TType.STRING, 'name', 'UTF8', None, ),  # 2
        (3, TType.STRING, 'graph', 'UTF8', None, ),  # 3
        (4, TType.STRING, 'gatewayId', 'UTF8', None, ),  # 4
        (5, TType.STRING, 'createdUser', 'UTF8', None, ),  # 5
        (6, TType.STRING, 'image', 'BINARY', None, ),  # 6
        (7, TType.LIST, 'workflowInputs', (TType.STRUCT, (airavata.model.application.io.ttypes.InputDataObjectType, airavata.model.application.io.ttypes.InputDataObjectType.thrift_spec), False), None, ),  # 7
        (8, TType.LIST, 'workflowOutputs', (TType.STRUCT, (airavata.model.application.io.ttypes.OutputDataObjectType, airavata.model.application.io.ttypes.OutputDataObjectType.thrift_spec), False), None, ),  # 8
        (9, TType.I64, 'creationTime', None, None, ),  # 9
    )

    def __init__(self, templateId=thrift_spec[1][4], name=None, graph=None, gatewayId=None, createdUser=None, image=None, workflowInputs=None, workflowOutputs=None, creationTime=None,):
        self.templateId = templateId
        self.name = name
        self.graph = graph
        self.gatewayId = gatewayId
        self.createdUser = createdUser
        self.image = image
        self.workflowInputs = workflowInputs
        self.workflowOutputs = workflowOutputs
        self.creationTime = creationTime

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
                    self.templateId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.name = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRING:
                    self.graph = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.STRING:
                    self.gatewayId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 5:
                if ftype == TType.STRING:
                    self.createdUser = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 6:
                if ftype == TType.STRING:
                    self.image = iprot.readBinary()
                else:
                    iprot.skip(ftype)
            elif fid == 7:
                if ftype == TType.LIST:
                    self.workflowInputs = []
                    (_etype3, _size0) = iprot.readListBegin()
                    for _i4 in range(_size0):
                        _elem5 = airavata.model.application.io.ttypes.InputDataObjectType()
                        _elem5.read(iprot)
                        self.workflowInputs.append(_elem5)
                    iprot.readListEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 8:
                if ftype == TType.LIST:
                    self.workflowOutputs = []
                    (_etype9, _size6) = iprot.readListBegin()
                    for _i10 in range(_size6):
                        _elem11 = airavata.model.application.io.ttypes.OutputDataObjectType()
                        _elem11.read(iprot)
                        self.workflowOutputs.append(_elem11)
                    iprot.readListEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 9:
                if ftype == TType.I64:
                    self.creationTime = iprot.readI64()
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
        oprot.writeStructBegin('WorkflowModel')
        if self.templateId is not None:
            oprot.writeFieldBegin('templateId', TType.STRING, 1)
            oprot.writeString(self.templateId.encode('utf-8') if sys.version_info[0] == 2 else self.templateId)
            oprot.writeFieldEnd()
        if self.name is not None:
            oprot.writeFieldBegin('name', TType.STRING, 2)
            oprot.writeString(self.name.encode('utf-8') if sys.version_info[0] == 2 else self.name)
            oprot.writeFieldEnd()
        if self.graph is not None:
            oprot.writeFieldBegin('graph', TType.STRING, 3)
            oprot.writeString(self.graph.encode('utf-8') if sys.version_info[0] == 2 else self.graph)
            oprot.writeFieldEnd()
        if self.gatewayId is not None:
            oprot.writeFieldBegin('gatewayId', TType.STRING, 4)
            oprot.writeString(self.gatewayId.encode('utf-8') if sys.version_info[0] == 2 else self.gatewayId)
            oprot.writeFieldEnd()
        if self.createdUser is not None:
            oprot.writeFieldBegin('createdUser', TType.STRING, 5)
            oprot.writeString(self.createdUser.encode('utf-8') if sys.version_info[0] == 2 else self.createdUser)
            oprot.writeFieldEnd()
        if self.image is not None:
            oprot.writeFieldBegin('image', TType.STRING, 6)
            oprot.writeBinary(self.image)
            oprot.writeFieldEnd()
        if self.workflowInputs is not None:
            oprot.writeFieldBegin('workflowInputs', TType.LIST, 7)
            oprot.writeListBegin(TType.STRUCT, len(self.workflowInputs))
            for iter12 in self.workflowInputs:
                iter12.write(oprot)
            oprot.writeListEnd()
            oprot.writeFieldEnd()
        if self.workflowOutputs is not None:
            oprot.writeFieldBegin('workflowOutputs', TType.LIST, 8)
            oprot.writeListBegin(TType.STRUCT, len(self.workflowOutputs))
            for iter13 in self.workflowOutputs:
                iter13.write(oprot)
            oprot.writeListEnd()
            oprot.writeFieldEnd()
        if self.creationTime is not None:
            oprot.writeFieldBegin('creationTime', TType.I64, 9)
            oprot.writeI64(self.creationTime)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.templateId is None:
            raise TProtocolException(message='Required field templateId is unset!')
        if self.name is None:
            raise TProtocolException(message='Required field name is unset!')
        if self.graph is None:
            raise TProtocolException(message='Required field graph is unset!')
        if self.gatewayId is None:
            raise TProtocolException(message='Required field gatewayId is unset!')
        if self.createdUser is None:
            raise TProtocolException(message='Required field createdUser is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class ComponentStatus(object):
    """
    Attributes:
     - state
     - reason
     - timeofStateChange
    """

    thrift_spec = (
        None,  # 0
        (1, TType.I32, 'state', None, 0, ),  # 1
        (2, TType.STRING, 'reason', 'UTF8', None, ),  # 2
        (3, TType.I64, 'timeofStateChange', None, None, ),  # 3
    )

    def __init__(self, state=thrift_spec[1][4], reason=None, timeofStateChange=None,):
        self.state = state
        self.reason = reason
        self.timeofStateChange = timeofStateChange

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
                if ftype == TType.I32:
                    self.state = iprot.readI32()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.reason = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.I64:
                    self.timeofStateChange = iprot.readI64()
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
        oprot.writeStructBegin('ComponentStatus')
        if self.state is not None:
            oprot.writeFieldBegin('state', TType.I32, 1)
            oprot.writeI32(self.state)
            oprot.writeFieldEnd()
        if self.reason is not None:
            oprot.writeFieldBegin('reason', TType.STRING, 2)
            oprot.writeString(self.reason.encode('utf-8') if sys.version_info[0] == 2 else self.reason)
            oprot.writeFieldEnd()
        if self.timeofStateChange is not None:
            oprot.writeFieldBegin('timeofStateChange', TType.I64, 3)
            oprot.writeI64(self.timeofStateChange)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.state is None:
            raise TProtocolException(message='Required field state is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class WorkflowStatus(object):
    """
    Attributes:
     - state
     - timeOfStateChange
     - reason
    """

    thrift_spec = (
        None,  # 0
        (1, TType.I32, 'state', None, None, ),  # 1
        (2, TType.I64, 'timeOfStateChange', None, None, ),  # 2
        (3, TType.STRING, 'reason', 'UTF8', None, ),  # 3
    )

    def __init__(self, state=None, timeOfStateChange=None, reason=None,):
        self.state = state
        self.timeOfStateChange = timeOfStateChange
        self.reason = reason

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
                if ftype == TType.I32:
                    self.state = iprot.readI32()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.I64:
                    self.timeOfStateChange = iprot.readI64()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRING:
                    self.reason = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
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
        oprot.writeStructBegin('WorkflowStatus')
        if self.state is not None:
            oprot.writeFieldBegin('state', TType.I32, 1)
            oprot.writeI32(self.state)
            oprot.writeFieldEnd()
        if self.timeOfStateChange is not None:
            oprot.writeFieldBegin('timeOfStateChange', TType.I64, 2)
            oprot.writeI64(self.timeOfStateChange)
            oprot.writeFieldEnd()
        if self.reason is not None:
            oprot.writeFieldBegin('reason', TType.STRING, 3)
            oprot.writeString(self.reason.encode('utf-8') if sys.version_info[0] == 2 else self.reason)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.state is None:
            raise TProtocolException(message='Required field state is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class EdgeModel(object):
    """
    Attributes:
     - edgeId
     - name
     - status
     - description
    """

    thrift_spec = (
        None,  # 0
        (1, TType.STRING, 'edgeId', 'UTF8', "DO_NOT_SET_AT_CLIENTS", ),  # 1
        (2, TType.STRING, 'name', 'UTF8', None, ),  # 2
        (3, TType.STRUCT, 'status', (ComponentStatus, ComponentStatus.thrift_spec), None, ),  # 3
        (4, TType.STRING, 'description', 'UTF8', None, ),  # 4
    )

    def __init__(self, edgeId=thrift_spec[1][4], name=None, status=None, description=None,):
        self.edgeId = edgeId
        self.name = name
        self.status = status
        self.description = description

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
                    self.edgeId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.name = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRUCT:
                    self.status = ComponentStatus()
                    self.status.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.STRING:
                    self.description = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
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
        oprot.writeStructBegin('EdgeModel')
        if self.edgeId is not None:
            oprot.writeFieldBegin('edgeId', TType.STRING, 1)
            oprot.writeString(self.edgeId.encode('utf-8') if sys.version_info[0] == 2 else self.edgeId)
            oprot.writeFieldEnd()
        if self.name is not None:
            oprot.writeFieldBegin('name', TType.STRING, 2)
            oprot.writeString(self.name.encode('utf-8') if sys.version_info[0] == 2 else self.name)
            oprot.writeFieldEnd()
        if self.status is not None:
            oprot.writeFieldBegin('status', TType.STRUCT, 3)
            self.status.write(oprot)
            oprot.writeFieldEnd()
        if self.description is not None:
            oprot.writeFieldBegin('description', TType.STRING, 4)
            oprot.writeString(self.description.encode('utf-8') if sys.version_info[0] == 2 else self.description)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.edgeId is None:
            raise TProtocolException(message='Required field edgeId is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class PortModel(object):
    """
    Attributes:
     - portId
     - name
     - status
     - value
     - description
    """

    thrift_spec = (
        None,  # 0
        (1, TType.STRING, 'portId', 'UTF8', "DO_NOT_SET_AT_CLIENTS", ),  # 1
        (2, TType.STRING, 'name', 'UTF8', None, ),  # 2
        (3, TType.STRUCT, 'status', (ComponentStatus, ComponentStatus.thrift_spec), None, ),  # 3
        (4, TType.STRING, 'value', 'UTF8', None, ),  # 4
        (5, TType.STRING, 'description', 'UTF8', None, ),  # 5
    )

    def __init__(self, portId=thrift_spec[1][4], name=None, status=None, value=None, description=None,):
        self.portId = portId
        self.name = name
        self.status = status
        self.value = value
        self.description = description

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
                    self.portId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.name = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRUCT:
                    self.status = ComponentStatus()
                    self.status.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.STRING:
                    self.value = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 5:
                if ftype == TType.STRING:
                    self.description = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
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
        oprot.writeStructBegin('PortModel')
        if self.portId is not None:
            oprot.writeFieldBegin('portId', TType.STRING, 1)
            oprot.writeString(self.portId.encode('utf-8') if sys.version_info[0] == 2 else self.portId)
            oprot.writeFieldEnd()
        if self.name is not None:
            oprot.writeFieldBegin('name', TType.STRING, 2)
            oprot.writeString(self.name.encode('utf-8') if sys.version_info[0] == 2 else self.name)
            oprot.writeFieldEnd()
        if self.status is not None:
            oprot.writeFieldBegin('status', TType.STRUCT, 3)
            self.status.write(oprot)
            oprot.writeFieldEnd()
        if self.value is not None:
            oprot.writeFieldBegin('value', TType.STRING, 4)
            oprot.writeString(self.value.encode('utf-8') if sys.version_info[0] == 2 else self.value)
            oprot.writeFieldEnd()
        if self.description is not None:
            oprot.writeFieldBegin('description', TType.STRING, 5)
            oprot.writeString(self.description.encode('utf-8') if sys.version_info[0] == 2 else self.description)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.portId is None:
            raise TProtocolException(message='Required field portId is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class NodeModel(object):
    """
    Attributes:
     - nodeId
     - name
     - applicationId
     - applicationName
     - status
     - description
    """

    thrift_spec = (
        None,  # 0
        (1, TType.STRING, 'nodeId', 'UTF8', "DO_NOT_SET_AT_CLIENTS", ),  # 1
        (2, TType.STRING, 'name', 'UTF8', None, ),  # 2
        (3, TType.STRING, 'applicationId', 'UTF8', None, ),  # 3
        (4, TType.STRING, 'applicationName', 'UTF8', None, ),  # 4
        (5, TType.STRUCT, 'status', (ComponentStatus, ComponentStatus.thrift_spec), None, ),  # 5
        (6, TType.STRING, 'description', 'UTF8', None, ),  # 6
    )

    def __init__(self, nodeId=thrift_spec[1][4], name=None, applicationId=None, applicationName=None, status=None, description=None,):
        self.nodeId = nodeId
        self.name = name
        self.applicationId = applicationId
        self.applicationName = applicationName
        self.status = status
        self.description = description

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
                    self.nodeId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.name = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRING:
                    self.applicationId = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.STRING:
                    self.applicationName = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 5:
                if ftype == TType.STRUCT:
                    self.status = ComponentStatus()
                    self.status.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 6:
                if ftype == TType.STRING:
                    self.description = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
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
        oprot.writeStructBegin('NodeModel')
        if self.nodeId is not None:
            oprot.writeFieldBegin('nodeId', TType.STRING, 1)
            oprot.writeString(self.nodeId.encode('utf-8') if sys.version_info[0] == 2 else self.nodeId)
            oprot.writeFieldEnd()
        if self.name is not None:
            oprot.writeFieldBegin('name', TType.STRING, 2)
            oprot.writeString(self.name.encode('utf-8') if sys.version_info[0] == 2 else self.name)
            oprot.writeFieldEnd()
        if self.applicationId is not None:
            oprot.writeFieldBegin('applicationId', TType.STRING, 3)
            oprot.writeString(self.applicationId.encode('utf-8') if sys.version_info[0] == 2 else self.applicationId)
            oprot.writeFieldEnd()
        if self.applicationName is not None:
            oprot.writeFieldBegin('applicationName', TType.STRING, 4)
            oprot.writeString(self.applicationName.encode('utf-8') if sys.version_info[0] == 2 else self.applicationName)
            oprot.writeFieldEnd()
        if self.status is not None:
            oprot.writeFieldBegin('status', TType.STRUCT, 5)
            self.status.write(oprot)
            oprot.writeFieldEnd()
        if self.description is not None:
            oprot.writeFieldBegin('description', TType.STRING, 6)
            oprot.writeString(self.description.encode('utf-8') if sys.version_info[0] == 2 else self.description)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        if self.nodeId is None:
            raise TProtocolException(message='Required field nodeId is unset!')
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)
