<?php
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * @license http://www.apache.org/licenses/LICENSE-2.0 Apache V2
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

/**
 * Bundle all thrift and Airavata stubs into a include file. This is simple but not so elegant way.
 *  Contributions welcome to improve writing PHP Client Samples.
 *
 */
include 'getAiravataClient.php';
global $airavataclient;
global $transport;

use Airavata\API\Error\AiravataClientException;
use Airavata\API\Error\AiravataSystemException;
use Airavata\API\Error\InvalidRequestException;
use Thrift\Exception\TTransportException;

use Airavata\Model\AppCatalog\AppDeployment\ApplicationModule;


if (count($argv) < 2) {
    exit("Please provide an Application Module ID."."\n". "Usage: php getApplicationModule.php <app_module_ID>". "\n"."\n");
}

$appModuleId = $argv[1];

$applicationModule = get_appModule($appModuleId);

var_dump($applicationModule);

$transport->close();

/**
 * Get the appModule with the given ID
 * @param $appModuleId
 * @return null
 */
function get_appModule($appModuleId)
{
    global $airavataclient;

    try {
        return $airavataclient->getApplicationModule($appModuleId);
    } catch (InvalidRequestException $ire) {
        echo 'Invalid Request Exception!'."\n" . $ire->getMessage();
    } catch (AiravataClientException $ace) {
        echo 'Airavata Client Exception!'."\n" . $ace->getMessage();
    } catch (AiravataSystemException $ase) {
        echo 'Airavata System Exception!'."\n" . $ase->getMessage();
    } catch (TTransportException $tte) {
        echo 'TTransport Exception!'."\n" . $tte->getMessage();
    } catch (\Exception $e) {
        echo 'Exception!'."\n". $e->getMessage();
    }

}

?>

