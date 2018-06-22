<?php
namespace Airavata\Model\Tenant;

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


final class TenantApprovalStatus {
  const REQUESTED = 0;
  const APPROVED = 1;
  const ACTIVE = 2;
  const DEACTIVATED = 3;
  const CANCELLED = 4;
  const DENIED = 5;
  const CREATED = 6;
  const DEPLOYED = 7;
  static public $__names = array(
    0 => 'REQUESTED',
    1 => 'APPROVED',
    2 => 'ACTIVE',
    3 => 'DEACTIVATED',
    4 => 'CANCELLED',
    5 => 'DENIED',
    6 => 'CREATED',
    7 => 'DEPLOYED',
  );
}

class TenantPreferences {
  static $_TSPEC;

  /**
   * @var string
   */
  public $tenantAdminFirstName = null;
  /**
   * @var string
   */
  public $tenantAdminLastName = null;
  /**
   * @var string
   */
  public $tenantAdminEmail = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        10 => array(
          'var' => 'tenantAdminFirstName',
          'type' => TType::STRING,
          ),
        11 => array(
          'var' => 'tenantAdminLastName',
          'type' => TType::STRING,
          ),
        12 => array(
          'var' => 'tenantAdminEmail',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['tenantAdminFirstName'])) {
        $this->tenantAdminFirstName = $vals['tenantAdminFirstName'];
      }
      if (isset($vals['tenantAdminLastName'])) {
        $this->tenantAdminLastName = $vals['tenantAdminLastName'];
      }
      if (isset($vals['tenantAdminEmail'])) {
        $this->tenantAdminEmail = $vals['tenantAdminEmail'];
      }
    }
  }

  public function getName() {
    return 'TenantPreferences';
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
        case 10:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->tenantAdminFirstName);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 11:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->tenantAdminLastName);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 12:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->tenantAdminEmail);
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
    $xfer += $output->writeStructBegin('TenantPreferences');
    if ($this->tenantAdminFirstName !== null) {
      $xfer += $output->writeFieldBegin('tenantAdminFirstName', TType::STRING, 10);
      $xfer += $output->writeString($this->tenantAdminFirstName);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->tenantAdminLastName !== null) {
      $xfer += $output->writeFieldBegin('tenantAdminLastName', TType::STRING, 11);
      $xfer += $output->writeString($this->tenantAdminLastName);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->tenantAdminEmail !== null) {
      $xfer += $output->writeFieldBegin('tenantAdminEmail', TType::STRING, 12);
      $xfer += $output->writeString($this->tenantAdminEmail);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class TenantConfig {
  static $_TSPEC;

  /**
   * @var string
   */
  public $oauthClientId = null;
  /**
   * @var string
   */
  public $oauthClientSecret = null;
  /**
   * @var string
   */
  public $identityServerUserName = null;
  /**
   * @var string
   */
  public $identityServerPasswordToken = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        16 => array(
          'var' => 'oauthClientId',
          'type' => TType::STRING,
          ),
        17 => array(
          'var' => 'oauthClientSecret',
          'type' => TType::STRING,
          ),
        13 => array(
          'var' => 'identityServerUserName',
          'type' => TType::STRING,
          ),
        14 => array(
          'var' => 'identityServerPasswordToken',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['oauthClientId'])) {
        $this->oauthClientId = $vals['oauthClientId'];
      }
      if (isset($vals['oauthClientSecret'])) {
        $this->oauthClientSecret = $vals['oauthClientSecret'];
      }
      if (isset($vals['identityServerUserName'])) {
        $this->identityServerUserName = $vals['identityServerUserName'];
      }
      if (isset($vals['identityServerPasswordToken'])) {
        $this->identityServerPasswordToken = $vals['identityServerPasswordToken'];
      }
    }
  }

  public function getName() {
    return 'TenantConfig';
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
        case 16:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->oauthClientId);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 17:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->oauthClientSecret);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 13:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->identityServerUserName);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 14:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->identityServerPasswordToken);
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
    $xfer += $output->writeStructBegin('TenantConfig');
    if ($this->identityServerUserName !== null) {
      $xfer += $output->writeFieldBegin('identityServerUserName', TType::STRING, 13);
      $xfer += $output->writeString($this->identityServerUserName);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->identityServerPasswordToken !== null) {
      $xfer += $output->writeFieldBegin('identityServerPasswordToken', TType::STRING, 14);
      $xfer += $output->writeString($this->identityServerPasswordToken);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->oauthClientId !== null) {
      $xfer += $output->writeFieldBegin('oauthClientId', TType::STRING, 16);
      $xfer += $output->writeString($this->oauthClientId);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->oauthClientSecret !== null) {
      $xfer += $output->writeFieldBegin('oauthClientSecret', TType::STRING, 17);
      $xfer += $output->writeString($this->oauthClientSecret);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class Tenant {
  static $_TSPEC;

  /**
   * @var string
   */
  public $tenantId = null;
  /**
   * @var int
   */
  public $tenantApprovalStatus = null;
  /**
   * @var string
   */
  public $tenantName = null;
  /**
   * @var string
   */
  public $domain = null;
  /**
   * @var string
   */
  public $emailAddress = null;
  /**
   * @var string
   */
  public $tenantAcronym = null;
  /**
   * @var string
   */
  public $tenantURL = null;
  /**
   * @var string
   */
  public $tenantPublicAbstract = null;
  /**
   * @var string
   */
  public $reviewProposalDescription = null;
  /**
   * @var string
   */
  public $declinedReason = null;
  /**
   * @var int
   */
  public $requestCreationTime = null;
  /**
   * @var string
   */
  public $requesterUsername = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'tenantId',
          'type' => TType::STRING,
          ),
        2 => array(
          'var' => 'tenantApprovalStatus',
          'type' => TType::I32,
          ),
        3 => array(
          'var' => 'tenantName',
          'type' => TType::STRING,
          ),
        4 => array(
          'var' => 'domain',
          'type' => TType::STRING,
          ),
        5 => array(
          'var' => 'emailAddress',
          'type' => TType::STRING,
          ),
        6 => array(
          'var' => 'tenantAcronym',
          'type' => TType::STRING,
          ),
        7 => array(
          'var' => 'tenantURL',
          'type' => TType::STRING,
          ),
        8 => array(
          'var' => 'tenantPublicAbstract',
          'type' => TType::STRING,
          ),
        9 => array(
          'var' => 'reviewProposalDescription',
          'type' => TType::STRING,
          ),
        15 => array(
          'var' => 'declinedReason',
          'type' => TType::STRING,
          ),
        18 => array(
          'var' => 'requestCreationTime',
          'type' => TType::I64,
          ),
        19 => array(
          'var' => 'requesterUsername',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['tenantId'])) {
        $this->tenantId = $vals['tenantId'];
      }
      if (isset($vals['tenantApprovalStatus'])) {
        $this->tenantApprovalStatus = $vals['tenantApprovalStatus'];
      }
      if (isset($vals['tenantName'])) {
        $this->tenantName = $vals['tenantName'];
      }
      if (isset($vals['domain'])) {
        $this->domain = $vals['domain'];
      }
      if (isset($vals['emailAddress'])) {
        $this->emailAddress = $vals['emailAddress'];
      }
      if (isset($vals['tenantAcronym'])) {
        $this->tenantAcronym = $vals['tenantAcronym'];
      }
      if (isset($vals['tenantURL'])) {
        $this->tenantURL = $vals['tenantURL'];
      }
      if (isset($vals['tenantPublicAbstract'])) {
        $this->tenantPublicAbstract = $vals['tenantPublicAbstract'];
      }
      if (isset($vals['reviewProposalDescription'])) {
        $this->reviewProposalDescription = $vals['reviewProposalDescription'];
      }
      if (isset($vals['declinedReason'])) {
        $this->declinedReason = $vals['declinedReason'];
      }
      if (isset($vals['requestCreationTime'])) {
        $this->requestCreationTime = $vals['requestCreationTime'];
      }
      if (isset($vals['requesterUsername'])) {
        $this->requesterUsername = $vals['requesterUsername'];
      }
    }
  }

  public function getName() {
    return 'Tenant';
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
            $xfer += $input->readString($this->tenantId);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->tenantApprovalStatus);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->tenantName);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->domain);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 5:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->emailAddress);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 6:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->tenantAcronym);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 7:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->tenantURL);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 8:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->tenantPublicAbstract);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 9:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->reviewProposalDescription);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 15:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->declinedReason);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 18:
          if ($ftype == TType::I64) {
            $xfer += $input->readI64($this->requestCreationTime);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 19:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->requesterUsername);
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
    $xfer += $output->writeStructBegin('Tenant');
    if ($this->tenantId !== null) {
      $xfer += $output->writeFieldBegin('tenantId', TType::STRING, 1);
      $xfer += $output->writeString($this->tenantId);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->tenantApprovalStatus !== null) {
      $xfer += $output->writeFieldBegin('tenantApprovalStatus', TType::I32, 2);
      $xfer += $output->writeI32($this->tenantApprovalStatus);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->tenantName !== null) {
      $xfer += $output->writeFieldBegin('tenantName', TType::STRING, 3);
      $xfer += $output->writeString($this->tenantName);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->domain !== null) {
      $xfer += $output->writeFieldBegin('domain', TType::STRING, 4);
      $xfer += $output->writeString($this->domain);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->emailAddress !== null) {
      $xfer += $output->writeFieldBegin('emailAddress', TType::STRING, 5);
      $xfer += $output->writeString($this->emailAddress);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->tenantAcronym !== null) {
      $xfer += $output->writeFieldBegin('tenantAcronym', TType::STRING, 6);
      $xfer += $output->writeString($this->tenantAcronym);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->tenantURL !== null) {
      $xfer += $output->writeFieldBegin('tenantURL', TType::STRING, 7);
      $xfer += $output->writeString($this->tenantURL);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->tenantPublicAbstract !== null) {
      $xfer += $output->writeFieldBegin('tenantPublicAbstract', TType::STRING, 8);
      $xfer += $output->writeString($this->tenantPublicAbstract);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->reviewProposalDescription !== null) {
      $xfer += $output->writeFieldBegin('reviewProposalDescription', TType::STRING, 9);
      $xfer += $output->writeString($this->reviewProposalDescription);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->declinedReason !== null) {
      $xfer += $output->writeFieldBegin('declinedReason', TType::STRING, 15);
      $xfer += $output->writeString($this->declinedReason);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->requestCreationTime !== null) {
      $xfer += $output->writeFieldBegin('requestCreationTime', TType::I64, 18);
      $xfer += $output->writeI64($this->requestCreationTime);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->requesterUsername !== null) {
      $xfer += $output->writeFieldBegin('requesterUsername', TType::STRING, 19);
      $xfer += $output->writeString($this->requesterUsername);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}


