<?php
namespace Airavata\Model\Status;

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


final class ExperimentState {
  const CREATED = 0;
  const VALIDATED = 1;
  const SCHEDULED = 2;
  const LAUNCHED = 3;
  const EXECUTING = 4;
  const CANCELING = 5;
  const CANCELED = 6;
  const COMPLETED = 7;
  const FAILED = 8;
  static public $__names = array(
    0 => 'CREATED',
    1 => 'VALIDATED',
    2 => 'SCHEDULED',
    3 => 'LAUNCHED',
    4 => 'EXECUTING',
    5 => 'CANCELING',
    6 => 'CANCELED',
    7 => 'COMPLETED',
    8 => 'FAILED',
  );
}

final class TaskState {
  const CREATED = 0;
  const EXECUTING = 1;
  const COMPLETED = 2;
  const FAILED = 3;
  const CANCELED = 4;
  static public $__names = array(
    0 => 'CREATED',
    1 => 'EXECUTING',
    2 => 'COMPLETED',
    3 => 'FAILED',
    4 => 'CANCELED',
  );
}

final class ProcessState {
  const CREATED = 0;
  const VALIDATED = 1;
  const STARTED = 2;
  const PRE_PROCESSING = 3;
  const CONFIGURING_WORKSPACE = 4;
  const INPUT_DATA_STAGING = 5;
  const EXECUTING = 6;
  const MONITORING = 7;
  const OUTPUT_DATA_STAGING = 8;
  const POST_PROCESSING = 9;
  const COMPLETED = 10;
  const FAILED = 11;
  const CANCELLING = 12;
  const CANCELED = 13;
  static public $__names = array(
    0 => 'CREATED',
    1 => 'VALIDATED',
    2 => 'STARTED',
    3 => 'PRE_PROCESSING',
    4 => 'CONFIGURING_WORKSPACE',
    5 => 'INPUT_DATA_STAGING',
    6 => 'EXECUTING',
    7 => 'MONITORING',
    8 => 'OUTPUT_DATA_STAGING',
    9 => 'POST_PROCESSING',
    10 => 'COMPLETED',
    11 => 'FAILED',
    12 => 'CANCELLING',
    13 => 'CANCELED',
  );
}

final class JobState {
  const SUBMITTED = 0;
  const QUEUED = 1;
  const ACTIVE = 2;
  const COMPLETE = 3;
  const CANCELED = 4;
  const FAILED = 5;
  const SUSPENDED = 6;
  const UNKNOWN = 7;
  static public $__names = array(
    0 => 'SUBMITTED',
    1 => 'QUEUED',
    2 => 'ACTIVE',
    3 => 'COMPLETE',
    4 => 'CANCELED',
    5 => 'FAILED',
    6 => 'SUSPENDED',
    7 => 'UNKNOWN',
  );
}

/**
 * Status: A generic status object.
 * 
 * state:
 *   State .
 * 
 * timeOfStateChange:
 *   time the status was last updated.
 * 
 * reason:
 *   User friendly reason on how the state is inferred.
 * 
 */
class ExperimentStatus {
  static $_TSPEC;

  /**
   * @var int
   */
  public $state = null;
  /**
   * @var int
   */
  public $timeOfStateChange = null;
  /**
   * @var string
   */
  public $reason = null;
  /**
   * @var string
   */
  public $statusId = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'state',
          'type' => TType::I32,
          ),
        2 => array(
          'var' => 'timeOfStateChange',
          'type' => TType::I64,
          ),
        3 => array(
          'var' => 'reason',
          'type' => TType::STRING,
          ),
        4 => array(
          'var' => 'statusId',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['state'])) {
        $this->state = $vals['state'];
      }
      if (isset($vals['timeOfStateChange'])) {
        $this->timeOfStateChange = $vals['timeOfStateChange'];
      }
      if (isset($vals['reason'])) {
        $this->reason = $vals['reason'];
      }
      if (isset($vals['statusId'])) {
        $this->statusId = $vals['statusId'];
      }
    }
  }

  public function getName() {
    return 'ExperimentStatus';
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
            $xfer += $input->readI32($this->state);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->timeOfStateChange);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->reason);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->statusId);
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
    $xfer += $output->writeStructBegin('ExperimentStatus');
    if ($this->state !== null) {
      $xfer += $output->writeFieldBegin('state', TType::I32, 1);
      $xfer += $output->writeI32($this->state);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->timeOfStateChange !== null) {
      $xfer += $output->writeFieldBegin('timeOfStateChange', TType::I64, 2);
      $xfer += $output->writeI64($this->timeOfStateChange);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->reason !== null) {
      $xfer += $output->writeFieldBegin('reason', TType::STRING, 3);
      $xfer += $output->writeString($this->reason);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->statusId !== null) {
      $xfer += $output->writeFieldBegin('statusId', TType::STRING, 4);
      $xfer += $output->writeString($this->statusId);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class ProcessStatus {
  static $_TSPEC;

  /**
   * @var int
   */
  public $state = null;
  /**
   * @var int
   */
  public $timeOfStateChange = null;
  /**
   * @var string
   */
  public $reason = null;
  /**
   * @var string
   */
  public $statusId = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'state',
          'type' => TType::I32,
          ),
        2 => array(
          'var' => 'timeOfStateChange',
          'type' => TType::I64,
          ),
        3 => array(
          'var' => 'reason',
          'type' => TType::STRING,
          ),
        4 => array(
          'var' => 'statusId',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['state'])) {
        $this->state = $vals['state'];
      }
      if (isset($vals['timeOfStateChange'])) {
        $this->timeOfStateChange = $vals['timeOfStateChange'];
      }
      if (isset($vals['reason'])) {
        $this->reason = $vals['reason'];
      }
      if (isset($vals['statusId'])) {
        $this->statusId = $vals['statusId'];
      }
    }
  }

  public function getName() {
    return 'ProcessStatus';
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
            $xfer += $input->readI32($this->state);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->timeOfStateChange);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->reason);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->statusId);
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
    $xfer += $output->writeStructBegin('ProcessStatus');
    if ($this->state !== null) {
      $xfer += $output->writeFieldBegin('state', TType::I32, 1);
      $xfer += $output->writeI32($this->state);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->timeOfStateChange !== null) {
      $xfer += $output->writeFieldBegin('timeOfStateChange', TType::I64, 2);
      $xfer += $output->writeI64($this->timeOfStateChange);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->reason !== null) {
      $xfer += $output->writeFieldBegin('reason', TType::STRING, 3);
      $xfer += $output->writeString($this->reason);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->statusId !== null) {
      $xfer += $output->writeFieldBegin('statusId', TType::STRING, 4);
      $xfer += $output->writeString($this->statusId);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class TaskStatus {
  static $_TSPEC;

  /**
   * @var int
   */
  public $state = null;
  /**
   * @var int
   */
  public $timeOfStateChange = null;
  /**
   * @var string
   */
  public $reason = null;
  /**
   * @var string
   */
  public $statusId = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'state',
          'type' => TType::I32,
          ),
        2 => array(
          'var' => 'timeOfStateChange',
          'type' => TType::I64,
          ),
        3 => array(
          'var' => 'reason',
          'type' => TType::STRING,
          ),
        4 => array(
          'var' => 'statusId',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['state'])) {
        $this->state = $vals['state'];
      }
      if (isset($vals['timeOfStateChange'])) {
        $this->timeOfStateChange = $vals['timeOfStateChange'];
      }
      if (isset($vals['reason'])) {
        $this->reason = $vals['reason'];
      }
      if (isset($vals['statusId'])) {
        $this->statusId = $vals['statusId'];
      }
    }
  }

  public function getName() {
    return 'TaskStatus';
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
            $xfer += $input->readI32($this->state);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->timeOfStateChange);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->reason);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->statusId);
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
    $xfer += $output->writeStructBegin('TaskStatus');
    if ($this->state !== null) {
      $xfer += $output->writeFieldBegin('state', TType::I32, 1);
      $xfer += $output->writeI32($this->state);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->timeOfStateChange !== null) {
      $xfer += $output->writeFieldBegin('timeOfStateChange', TType::I64, 2);
      $xfer += $output->writeI64($this->timeOfStateChange);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->reason !== null) {
      $xfer += $output->writeFieldBegin('reason', TType::STRING, 3);
      $xfer += $output->writeString($this->reason);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->statusId !== null) {
      $xfer += $output->writeFieldBegin('statusId', TType::STRING, 4);
      $xfer += $output->writeString($this->statusId);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class JobStatus {
  static $_TSPEC;

  /**
   * @var int
   */
  public $jobState = null;
  /**
   * @var int
   */
  public $timeOfStateChange = null;
  /**
   * @var string
   */
  public $reason = null;
  /**
   * @var string
   */
  public $statusId = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'jobState',
          'type' => TType::I32,
          ),
        2 => array(
          'var' => 'timeOfStateChange',
          'type' => TType::I64,
          ),
        3 => array(
          'var' => 'reason',
          'type' => TType::STRING,
          ),
        4 => array(
          'var' => 'statusId',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['jobState'])) {
        $this->jobState = $vals['jobState'];
      }
      if (isset($vals['timeOfStateChange'])) {
        $this->timeOfStateChange = $vals['timeOfStateChange'];
      }
      if (isset($vals['reason'])) {
        $this->reason = $vals['reason'];
      }
      if (isset($vals['statusId'])) {
        $this->statusId = $vals['statusId'];
      }
    }
  }

  public function getName() {
    return 'JobStatus';
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
            $xfer += $input->readI32($this->jobState);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->timeOfStateChange);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->reason);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->statusId);
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
    $xfer += $output->writeStructBegin('JobStatus');
    if ($this->jobState !== null) {
      $xfer += $output->writeFieldBegin('jobState', TType::I32, 1);
      $xfer += $output->writeI32($this->jobState);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->timeOfStateChange !== null) {
      $xfer += $output->writeFieldBegin('timeOfStateChange', TType::I64, 2);
      $xfer += $output->writeI64($this->timeOfStateChange);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->reason !== null) {
      $xfer += $output->writeFieldBegin('reason', TType::STRING, 3);
      $xfer += $output->writeString($this->reason);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->statusId !== null) {
      $xfer += $output->writeFieldBegin('statusId', TType::STRING, 4);
      $xfer += $output->writeString($this->statusId);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class QueueStatusModel {
  static $_TSPEC;

  /**
   * @var string
   */
  public $hostName = null;
  /**
   * @var string
   */
  public $queueName = null;
  /**
   * @var bool
   */
  public $queueUp = null;
  /**
   * @var int
   */
  public $runningJobs = null;
  /**
   * @var int
   */
  public $queuedJobs = null;
  /**
   * @var int
   */
  public $time = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'hostName',
          'type' => TType::STRING,
          ),
        2 => array(
          'var' => 'queueName',
          'type' => TType::STRING,
          ),
        3 => array(
          'var' => 'queueUp',
          'type' => TType::BOOL,
          ),
        4 => array(
          'var' => 'runningJobs',
          'type' => TType::I32,
          ),
        5 => array(
          'var' => 'queuedJobs',
          'type' => TType::I32,
          ),
        6 => array(
          'var' => 'time',
          'type' => TType::I64,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['hostName'])) {
        $this->hostName = $vals['hostName'];
      }
      if (isset($vals['queueName'])) {
        $this->queueName = $vals['queueName'];
      }
      if (isset($vals['queueUp'])) {
        $this->queueUp = $vals['queueUp'];
      }
      if (isset($vals['runningJobs'])) {
        $this->runningJobs = $vals['runningJobs'];
      }
      if (isset($vals['queuedJobs'])) {
        $this->queuedJobs = $vals['queuedJobs'];
      }
      if (isset($vals['time'])) {
        $this->time = $vals['time'];
      }
    }
  }

  public function getName() {
    return 'QueueStatusModel';
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
            $xfer += $input->readString($this->hostName);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->queueName);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::BOOL) {
            $xfer += $input->readBool($this->queueUp);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->runningJobs);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 5:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->queuedJobs);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 6:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->time);
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
    $xfer += $output->writeStructBegin('QueueStatusModel');
    if ($this->hostName !== null) {
      $xfer += $output->writeFieldBegin('hostName', TType::STRING, 1);
      $xfer += $output->writeString($this->hostName);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->queueName !== null) {
      $xfer += $output->writeFieldBegin('queueName', TType::STRING, 2);
      $xfer += $output->writeString($this->queueName);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->queueUp !== null) {
      $xfer += $output->writeFieldBegin('queueUp', TType::BOOL, 3);
      $xfer += $output->writeBool($this->queueUp);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->runningJobs !== null) {
      $xfer += $output->writeFieldBegin('runningJobs', TType::I32, 4);
      $xfer += $output->writeI32($this->runningJobs);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->queuedJobs !== null) {
      $xfer += $output->writeFieldBegin('queuedJobs', TType::I32, 5);
      $xfer += $output->writeI32($this->queuedJobs);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->time !== null) {
      $xfer += $output->writeFieldBegin('time', TType::I64, 6);
      $xfer += $output->writeI64($this->time);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}


