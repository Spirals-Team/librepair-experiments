/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

create database saga_dubbo;
use saga_dubbo;

CREATE TABLE `testa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service` varchar(128) DEFAULT NULL,
  `vstatus` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `testa` (`id`, `service`, `vstatus`) VALUES ('1','servicea','init');

CREATE TABLE `testb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service` varchar(128) DEFAULT NULL,
  `vstatus` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
INSERT INTO `testb` (`id`, `service`, `vstatus`) VALUES ('2','serviceb','init');

CREATE TABLE `testc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service` varchar(128) DEFAULT NULL,
  `vstatus` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `testc` (`id`, `service`, `vstatus`) VALUES ('3','servicec','init');