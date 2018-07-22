<?php
namespace Airavata\Model\Commons;

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


class ErrorModel {
  static $_TSPEC;

  /**
   * @var string
   */
  public $errorId = "DO_NOT_SET_AT_CLIENTS";
  /**
   * @var int
   */
  public $creationTime = null;
  /**
   * @var string
   */
  public $actualErrorMessage = null;
  /**
   * @var string
   */
  public $userFriendlyMessage = null;
  /**
   * @var bool
   */
  public $transientOrPersistent = false;
  /**
   * @var string[]
   */
  public $rootCauseErrorIdList = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'errorId',
          'type' => TType::STRING,
          ),
        2 => array(
          'var' => 'creationTime',
          'type' => TType::I64,
          ),
        3 => array(
          'var' => 'actualErrorMessage',
          'type' => TType::STRING,
          ),
        4 => array(
          'var' => 'userFriendlyMessage',
          'type' => TType::STRING,
          ),
        5 => array(
          'var' => 'transientOrPersistent',
          'type' => TType::BOOL,
          ),
        6 => array(
          'var' => 'rootCauseErrorIdList',
          'type' => TType::LST,
          'etype' => TType::STRING,
          'elem' => array(
            'type' => TType::STRING,
            ),
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['errorId'])) {
        $this->errorId = $vals['errorId'];
      }
      if (isset($vals['creationTime'])) {
        $this->creationTime = $vals['creationTime'];
      }
      if (isset($vals['actualErrorMessage'])) {
        $this->actualErrorMessage = $vals['actualErrorMessage'];
      }
      if (isset($vals['userFriendlyMessage'])) {
        $this->userFriendlyMessage = $vals['userFriendlyMessage'];
      }
      if (isset($vals['transientOrPersistent'])) {
        $this->transientOrPersistent = $vals['transientOrPersistent'];
      }
      if (isset($vals['rootCauseErrorIdList'])) {
        $this->rootCauseErrorIdList = $vals['rootCauseErrorIdList'];
      }
    }
  }

  public function getName() {
    return 'ErrorModel';
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
            $xfer += $input->readString($this->errorId);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->creationTime);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->actualErrorMessage);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->userFriendlyMessage);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 5:
          if ($ftype == TType::BOOL) {
            $xfer += $input->readBool($this->transientOrPersistent);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 6:
          if ($ftype == TType::LST) {
            $this->rootCauseErrorIdList = array();
            $_size0 = 0;
            $_etype3 = 0;
            $xfer += $input->readListBegin($_etype3, $_size0);
            for ($_i4 = 0; $_i4 < $_size0; ++$_i4)
            {
              $elem5 = null;
              $xfer += $input->readString($elem5);
              $this->rootCauseErrorIdList []= $elem5;
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
    $xfer += $output->writeStructBegin('ErrorModel');
    if ($this->errorId !== null) {
      $xfer += $output->writeFieldBegin('errorId', TType::STRING, 1);
      $xfer += $output->writeString($this->errorId);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->creationTime !== null) {
      $xfer += $output->writeFieldBegin('creationTime', TType::I64, 2);
      $xfer += $output->writeI64($this->creationTime);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->actualErrorMessage !== null) {
      $xfer += $output->writeFieldBegin('actualErrorMessage', TType::STRING, 3);
      $xfer += $output->writeString($this->actualErrorMessage);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->userFriendlyMessage !== null) {
      $xfer += $output->writeFieldBegin('userFriendlyMessage', TType::STRING, 4);
      $xfer += $output->writeString($this->userFriendlyMessage);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->transientOrPersistent !== null) {
      $xfer += $output->writeFieldBegin('transientOrPersistent', TType::BOOL, 5);
      $xfer += $output->writeBool($this->transientOrPersistent);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->rootCauseErrorIdList !== null) {
      if (!is_array($this->rootCauseErrorIdList)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('rootCauseErrorIdList', TType::LST, 6);
      {
        $output->writeListBegin(TType::STRING, count($this->rootCauseErrorIdList));
        {
          foreach ($this->rootCauseErrorIdList as $iter6)
          {
            $xfer += $output->writeString($iter6);
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

/**
 * This data structure can be used to store the validation results
 * captured during validation step and during the launchExperiment
 * operation it can be easilly checked to see the errors occured
 * during the experiment launch operation
 * 
 */
class ValidatorResult {
  static $_TSPEC;

  /**
   * @var bool
   */
  public $result = null;
  /**
   * @var string
   */
  public $errorDetails = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'result',
          'type' => TType::BOOL,
          ),
        2 => array(
          'var' => 'errorDetails',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['result'])) {
        $this->result = $vals['result'];
      }
      if (isset($vals['errorDetails'])) {
        $this->errorDetails = $vals['errorDetails'];
      }
    }
  }

  public function getName() {
    return 'ValidatorResult';
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
          if ($ftype == TType::BOOL) {
            $xfer += $input->readBool($this->result);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->errorDetails);
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
    $xfer += $output->writeStructBegin('ValidatorResult');
    if ($this->result !== null) {
      $xfer += $output->writeFieldBegin('result', TType::BOOL, 1);
      $xfer += $output->writeBool($this->result);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->errorDetails !== null) {
      $xfer += $output->writeFieldBegin('errorDetails', TType::STRING, 2);
      $xfer += $output->writeString($this->errorDetails);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class ValidationResults {
  static $_TSPEC;

  /**
   * @var bool
   */
  public $validationState = null;
  /**
   * @var \Airavata\Model\Commons\ValidatorResult[]
   */
  public $validationResultList = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'validationState',
          'type' => TType::BOOL,
          ),
        2 => array(
          'var' => 'validationResultList',
          'type' => TType::LST,
          'etype' => TType::STRUCT,
          'elem' => array(
            'type' => TType::STRUCT,
            'class' => '\Airavata\Model\Commons\ValidatorResult',
            ),
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['validationState'])) {
        $this->validationState = $vals['validationState'];
      }
      if (isset($vals['validationResultList'])) {
        $this->validationResultList = $vals['validationResultList'];
      }
    }
  }

  public function getName() {
    return 'ValidationResults';
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
          if ($ftype == TType::BOOL) {
            $xfer += $input->readBool($this->validationState);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::LST) {
            $this->validationResultList = array();
            $_size7 = 0;
            $_etype10 = 0;
            $xfer += $input->readListBegin($_etype10, $_size7);
            for ($_i11 = 0; $_i11 < $_size7; ++$_i11)
            {
              $elem12 = null;
              $elem12 = new \Airavata\Model\Commons\ValidatorResult();
              $xfer += $elem12->read($input);
              $this->validationResultList []= $elem12;
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
    $xfer += $output->writeStructBegin('ValidationResults');
    if ($this->validationState !== null) {
      $xfer += $output->writeFieldBegin('validationState', TType::BOOL, 1);
      $xfer += $output->writeBool($this->validationState);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->validationResultList !== null) {
      if (!is_array($this->validationResultList)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('validationResultList', TType::LST, 2);
      {
        $output->writeListBegin(TType::STRUCT, count($this->validationResultList));
        {
          foreach ($this->validationResultList as $iter13)
          {
            $xfer += $iter13->write($output);
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

final class Constant extends \Thrift\Type\TConstant {
  static protected $DEFAULT_ID;

  static protected function init_DEFAULT_ID() {
    return "DO_NOT_SET_AT_CLIENTS";
  }
}


