<?php
namespace Airavata\Model\Task;

/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
use Thrift\Base\TBase;
use Thrift\Type\TType;
use Thrift\Type\TMessageType;
use Thrift\Exception\TException;
use Thrift\Exception\TProtocolException;
use Thrift\Protocol\TProtocol;
use Thrift\Protocol\TBinaryProtocolAccelerated;
use Thrift\Exception\TApplicationException;


/**
 * TaskTypes: An enumerated list of TaskTypes. Task being generic, the task type will provide the concrete interpretation.
 * 
 */
final class TaskTypes {
  const ENV_SETUP = 0;
  const DATA_STAGING = 1;
  const JOB_SUBMISSION = 2;
  const ENV_CLEANUP = 3;
  const MONITORING = 4;
  const OUTPUT_FETCHING = 5;
  static public $__names = array(
    0 => 'ENV_SETUP',
    1 => 'DATA_STAGING',
    2 => 'JOB_SUBMISSION',
    3 => 'ENV_CLEANUP',
    4 => 'MONITORING',
    5 => 'OUTPUT_FETCHING',
  );
}

/**
 * DataStagingTaskModel: A structure holding the data staging task details.
 * 
 * Source and Destination locations includes standard representation of protocol, host, port and path
 *   A friendly description of the task, usally used to communicate information to users.
 * 
 */
final class DataStageType {
  const INPUT = 0;
  const OUPUT = 1;
  const ARCHIVE_OUTPUT = 2;
  static public $__names = array(
    0 => 'INPUT',
    1 => 'OUPUT',
    2 => 'ARCHIVE_OUTPUT',
  );
}

/**
 * TaskModel: A structure holding the generic task details.
 * 
 * taskDetail:
 *   A friendly description of the task, usally used to communicate information to users.
 * 
 * subTaskModel:
 *   A generic byte object for the Task developer to store internal serialized data into registry catalogs.
 */
class TaskModel {
  static $_TSPEC;

  /**
   * @var string
   */
  public $taskId = "DO_NOT_SET_AT_CLIENTS";
  /**
   * @var int
   */
  public $taskType = null;
  /**
   * @var string
   */
  public $parentProcessId = null;
  /**
   * @var int
   */
  public $creationTime = null;
  /**
   * @var int
   */
  public $lastUpdateTime = null;
  /**
   * @var \Airavata\Model\Status\TaskStatus[]
   */
  public $taskStatuses = null;
  /**
   * @var string
   */
  public $taskDetail = null;
  /**
   * @var string
   */
  public $subTaskModel = null;
  /**
   * @var \Airavata\Model\Commons\ErrorModel[]
   */
  public $taskErrors = null;
  /**
   * @var \Airavata\Model\Job\JobModel[]
   */
  public $jobs = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'taskId',
          'type' => TType::STRING,
          ),
        2 => array(
          'var' => 'taskType',
          'type' => TType::I32,
          ),
        3 => array(
          'var' => 'parentProcessId',
          'type' => TType::STRING,
          ),
        4 => array(
          'var' => 'creationTime',
          'type' => TType::I64,
          ),
        5 => array(
          'var' => 'lastUpdateTime',
          'type' => TType::I64,
          ),
        6 => array(
          'var' => 'taskStatuses',
          'type' => TType::LST,
          'etype' => TType::STRUCT,
          'elem' => array(
            'type' => TType::STRUCT,
            'class' => '\Airavata\Model\Status\TaskStatus',
            ),
          ),
        7 => array(
          'var' => 'taskDetail',
          'type' => TType::STRING,
          ),
        8 => array(
          'var' => 'subTaskModel',
          'type' => TType::STRING,
          ),
        9 => array(
          'var' => 'taskErrors',
          'type' => TType::LST,
          'etype' => TType::STRUCT,
          'elem' => array(
            'type' => TType::STRUCT,
            'class' => '\Airavata\Model\Commons\ErrorModel',
            ),
          ),
        10 => array(
          'var' => 'jobs',
          'type' => TType::LST,
          'etype' => TType::STRUCT,
          'elem' => array(
            'type' => TType::STRUCT,
            'class' => '\Airavata\Model\Job\JobModel',
            ),
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['taskId'])) {
        $this->taskId = $vals['taskId'];
      }
      if (isset($vals['taskType'])) {
        $this->taskType = $vals['taskType'];
      }
      if (isset($vals['parentProcessId'])) {
        $this->parentProcessId = $vals['parentProcessId'];
      }
      if (isset($vals['creationTime'])) {
        $this->creationTime = $vals['creationTime'];
      }
      if (isset($vals['lastUpdateTime'])) {
        $this->lastUpdateTime = $vals['lastUpdateTime'];
      }
      if (isset($vals['taskStatuses'])) {
        $this->taskStatuses = $vals['taskStatuses'];
      }
      if (isset($vals['taskDetail'])) {
        $this->taskDetail = $vals['taskDetail'];
      }
      if (isset($vals['subTaskModel'])) {
        $this->subTaskModel = $vals['subTaskModel'];
      }
      if (isset($vals['taskErrors'])) {
        $this->taskErrors = $vals['taskErrors'];
      }
      if (isset($vals['jobs'])) {
        $this->jobs = $vals['jobs'];
      }
    }
  }

  public function getName() {
    return 'TaskModel';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->taskId);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->taskType);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->parentProcessId);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->creationTime);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 5:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->lastUpdateTime);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 6:
          if ($ftype == TType::LST) {
            $this->taskStatuses = array();
            $_size0 = 0;
            $_etype3 = 0;
            $xfer += $input->readListBegin($_etype3, $_size0);
            for ($_i4 = 0; $_i4 < $_size0; ++$_i4)
            {
              $elem5 = null;
              $elem5 = new \Airavata\Model\Status\TaskStatus();
              $xfer += $elem5->read($input);
              $this->taskStatuses []= $elem5;
            }
            $xfer += $input->readListEnd();
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 7:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->taskDetail);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 8:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->subTaskModel);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 9:
          if ($ftype == TType::LST) {
            $this->taskErrors = array();
            $_size6 = 0;
            $_etype9 = 0;
            $xfer += $input->readListBegin($_etype9, $_size6);
            for ($_i10 = 0; $_i10 < $_size6; ++$_i10)
            {
              $elem11 = null;
              $elem11 = new \Airavata\Model\Commons\ErrorModel();
              $xfer += $elem11->read($input);
              $this->taskErrors []= $elem11;
            }
            $xfer += $input->readListEnd();
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 10:
          if ($ftype == TType::LST) {
            $this->jobs = array();
            $_size12 = 0;
            $_etype15 = 0;
            $xfer += $input->readListBegin($_etype15, $_size12);
            for ($_i16 = 0; $_i16 < $_size12; ++$_i16)
            {
              $elem17 = null;
              $elem17 = new \Airavata\Model\Job\JobModel();
              $xfer += $elem17->read($input);
              $this->jobs []= $elem17;
            }
            $xfer += $input->readListEnd();
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('TaskModel');
    if ($this->taskId !== null) {
      $xfer += $output->writeFieldBegin('taskId', TType::STRING, 1);
      $xfer += $output->writeString($this->taskId);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->taskType !== null) {
      $xfer += $output->writeFieldBegin('taskType', TType::I32, 2);
      $xfer += $output->writeI32($this->taskType);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->parentProcessId !== null) {
      $xfer += $output->writeFieldBegin('parentProcessId', TType::STRING, 3);
      $xfer += $output->writeString($this->parentProcessId);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->creationTime !== null) {
      $xfer += $output->writeFieldBegin('creationTime', TType::I64, 4);
      $xfer += $output->writeI64($this->creationTime);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->lastUpdateTime !== null) {
      $xfer += $output->writeFieldBegin('lastUpdateTime', TType::I64, 5);
      $xfer += $output->writeI64($this->lastUpdateTime);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->taskStatuses !== null) {
      if (!is_array($this->taskStatuses)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('taskStatuses', TType::LST, 6);
      {
        $output->writeListBegin(TType::STRUCT, count($this->taskStatuses));
        {
          foreach ($this->taskStatuses as $iter18)
          {
            $xfer += $iter18->write($output);
          }
        }
        $output->writeListEnd();
      }
      $xfer += $output->writeFieldEnd();
    }
    if ($this->taskDetail !== null) {
      $xfer += $output->writeFieldBegin('taskDetail', TType::STRING, 7);
      $xfer += $output->writeString($this->taskDetail);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->subTaskModel !== null) {
      $xfer += $output->writeFieldBegin('subTaskModel', TType::STRING, 8);
      $xfer += $output->writeString($this->subTaskModel);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->taskErrors !== null) {
      if (!is_array($this->taskErrors)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('taskErrors', TType::LST, 9);
      {
        $output->writeListBegin(TType::STRUCT, count($this->taskErrors));
        {
          foreach ($this->taskErrors as $iter19)
          {
            $xfer += $iter19->write($output);
          }
        }
        $output->writeListEnd();
      }
      $xfer += $output->writeFieldEnd();
    }
    if ($this->jobs !== null) {
      if (!is_array($this->jobs)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('jobs', TType::LST, 10);
      {
        $output->writeListBegin(TType::STRUCT, count($this->jobs));
        {
          foreach ($this->jobs as $iter20)
          {
            $xfer += $iter20->write($output);
          }
        }
        $output->writeListEnd();
      }
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class DataStagingTaskModel {
  static $_TSPEC;

  /**
   * @var string
   */
  public $source = null;
  /**
   * @var string
   */
  public $destination = null;
  /**
   * @var int
   */
  public $type = null;
  /**
   * @var int
   */
  public $transferStartTime = null;
  /**
   * @var int
   */
  public $transferEndTime = null;
  /**
   * @var string
   */
  public $transferRate = null;
  /**
   * @var \Airavata\Model\Application\Io\InputDataObjectType
   */
  public $processInput = null;
  /**
   * @var \Airavata\Model\Application\Io\OutputDataObjectType
   */
  public $processOutput = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'source',
          'type' => TType::STRING,
          ),
        2 => array(
          'var' => 'destination',
          'type' => TType::STRING,
          ),
        3 => array(
          'var' => 'type',
          'type' => TType::I32,
          ),
        4 => array(
          'var' => 'transferStartTime',
          'type' => TType::I64,
          ),
        5 => array(
          'var' => 'transferEndTime',
          'type' => TType::I64,
          ),
        6 => array(
          'var' => 'transferRate',
          'type' => TType::STRING,
          ),
        7 => array(
          'var' => 'processInput',
          'type' => TType::STRUCT,
          'class' => '\Airavata\Model\Application\Io\InputDataObjectType',
          ),
        8 => array(
          'var' => 'processOutput',
          'type' => TType::STRUCT,
          'class' => '\Airavata\Model\Application\Io\OutputDataObjectType',
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['source'])) {
        $this->source = $vals['source'];
      }
      if (isset($vals['destination'])) {
        $this->destination = $vals['destination'];
      }
      if (isset($vals['type'])) {
        $this->type = $vals['type'];
      }
      if (isset($vals['transferStartTime'])) {
        $this->transferStartTime = $vals['transferStartTime'];
      }
      if (isset($vals['transferEndTime'])) {
        $this->transferEndTime = $vals['transferEndTime'];
      }
      if (isset($vals['transferRate'])) {
        $this->transferRate = $vals['transferRate'];
      }
      if (isset($vals['processInput'])) {
        $this->processInput = $vals['processInput'];
      }
      if (isset($vals['processOutput'])) {
        $this->processOutput = $vals['processOutput'];
      }
    }
  }

  public function getName() {
    return 'DataStagingTaskModel';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->source);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->destination);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->type);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->transferStartTime);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 5:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->transferEndTime);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 6:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->transferRate);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 7:
          if ($ftype == TType::STRUCT) {
            $this->processInput = new \Airavata\Model\Application\Io\InputDataObjectType();
            $xfer += $this->processInput->read($input);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 8:
          if ($ftype == TType::STRUCT) {
            $this->processOutput = new \Airavata\Model\Application\Io\OutputDataObjectType();
            $xfer += $this->processOutput->read($input);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('DataStagingTaskModel');
    if ($this->source !== null) {
      $xfer += $output->writeFieldBegin('source', TType::STRING, 1);
      $xfer += $output->writeString($this->source);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->destination !== null) {
      $xfer += $output->writeFieldBegin('destination', TType::STRING, 2);
      $xfer += $output->writeString($this->destination);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->type !== null) {
      $xfer += $output->writeFieldBegin('type', TType::I32, 3);
      $xfer += $output->writeI32($this->type);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->transferStartTime !== null) {
      $xfer += $output->writeFieldBegin('transferStartTime', TType::I64, 4);
      $xfer += $output->writeI64($this->transferStartTime);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->transferEndTime !== null) {
      $xfer += $output->writeFieldBegin('transferEndTime', TType::I64, 5);
      $xfer += $output->writeI64($this->transferEndTime);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->transferRate !== null) {
      $xfer += $output->writeFieldBegin('transferRate', TType::STRING, 6);
      $xfer += $output->writeString($this->transferRate);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->processInput !== null) {
      if (!is_object($this->processInput)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('processInput', TType::STRUCT, 7);
      $xfer += $this->processInput->write($output);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->processOutput !== null) {
      if (!is_object($this->processOutput)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('processOutput', TType::STRUCT, 8);
      $xfer += $this->processOutput->write($output);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

/**
 * EnvironmentSetupTaskModel: A structure holding the environment creation task details
 * 
 */
class EnvironmentSetupTaskModel {
  static $_TSPEC;

  /**
   * @var string
   */
  public $location = null;
  /**
   * @var int
   */
  public $protocol = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'location',
          'type' => TType::STRING,
          ),
        2 => array(
          'var' => 'protocol',
          'type' => TType::I32,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['location'])) {
        $this->location = $vals['location'];
      }
      if (isset($vals['protocol'])) {
        $this->protocol = $vals['protocol'];
      }
    }
  }

  public function getName() {
    return 'EnvironmentSetupTaskModel';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->location);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->protocol);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('EnvironmentSetupTaskModel');
    if ($this->location !== null) {
      $xfer += $output->writeFieldBegin('location', TType::STRING, 1);
      $xfer += $output->writeString($this->location);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->protocol !== null) {
      $xfer += $output->writeFieldBegin('protocol', TType::I32, 2);
      $xfer += $output->writeI32($this->protocol);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class JobSubmissionTaskModel {
  static $_TSPEC;

  /**
   * @var int
   */
  public $jobSubmissionProtocol = null;
  /**
   * @var int
   */
  public $monitorMode = null;
  /**
   * @var int
   */
  public $wallTime = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'jobSubmissionProtocol',
          'type' => TType::I32,
          ),
        2 => array(
          'var' => 'monitorMode',
          'type' => TType::I32,
          ),
        3 => array(
          'var' => 'wallTime',
          'type' => TType::I32,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['jobSubmissionProtocol'])) {
        $this->jobSubmissionProtocol = $vals['jobSubmissionProtocol'];
      }
      if (isset($vals['monitorMode'])) {
        $this->monitorMode = $vals['monitorMode'];
      }
      if (isset($vals['wallTime'])) {
        $this->wallTime = $vals['wallTime'];
      }
    }
  }

  public function getName() {
    return 'JobSubmissionTaskModel';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->jobSubmissionProtocol);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->monitorMode);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->wallTime);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('JobSubmissionTaskModel');
    if ($this->jobSubmissionProtocol !== null) {
      $xfer += $output->writeFieldBegin('jobSubmissionProtocol', TType::I32, 1);
      $xfer += $output->writeI32($this->jobSubmissionProtocol);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->monitorMode !== null) {
      $xfer += $output->writeFieldBegin('monitorMode', TType::I32, 2);
      $xfer += $output->writeI32($this->monitorMode);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->wallTime !== null) {
      $xfer += $output->writeFieldBegin('wallTime', TType::I32, 3);
      $xfer += $output->writeI32($this->wallTime);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class MonitorTaskModel {
  static $_TSPEC;

  /**
   * @var int
   */
  public $monitorMode = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'monitorMode',
          'type' => TType::I32,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['monitorMode'])) {
        $this->monitorMode = $vals['monitorMode'];
      }
    }
  }

  public function getName() {
    return 'MonitorTaskModel';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->monitorMode);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('MonitorTaskModel');
    if ($this->monitorMode !== null) {
      $xfer += $output->writeFieldBegin('monitorMode', TType::I32, 1);
      $xfer += $output->writeI32($this->monitorMode);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}


