/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

CREATE TABLE WORKFLOW
(
        TEMPLATE_ID VARCHAR (255) NOT NULL,
        WORKFLOW_NAME VARCHAR (255) NOT NULL,
        CREATED_USER VARCHAR (255),
        GATEWAY_ID VARCHAR (255),
        GRAPH LONGTEXT,
        IMAGE BLOB,
        CREATION_TIME timestamp DEFAULT NOW(),
        UPDATE_TIME TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
        PRIMARY KEY (TEMPLATE_ID)
);

CREATE TABLE WORKFLOW_INPUT
(
         TEMPLATE_ID VARCHAR(255),
         INPUT_KEY VARCHAR(255),
         INPUT_VALUE VARCHAR(255),
         DATA_TYPE VARCHAR(255),
         METADATA VARCHAR(255),
         APP_ARGUMENT VARCHAR(255),
         STANDARD_INPUT SMALLINT,
         USER_FRIENDLY_DESC VARCHAR(255),
         INPUT_ORDER INTEGER,
         IS_REQUIRED SMALLINT,
         REQUIRED_TO_COMMANDLINE SMALLINT,
         DATA_STAGED SMALLINT,
         PRIMARY KEY(TEMPLATE_ID,INPUT_KEY),
         FOREIGN KEY (TEMPLATE_ID) REFERENCES WORKFLOW(TEMPLATE_ID) ON DELETE CASCADE
);

CREATE TABLE WORKFLOW_OUTPUT
(
         TEMPLATE_ID VARCHAR(255),
         OUTPUT_KEY VARCHAR(255),
         OUTPUT_VALUE LONGTEXT,
         DATA_TYPE VARCHAR(255),
         IS_REQUIRED SMALLINT,
         REQUIRED_TO_COMMANDLINE SMALLINT,
         DATA_MOVEMENT SMALLINT,
         DATA_NAME_LOCATION VARCHAR(255),
         SEARCH_QUERY VARCHAR(255),
         APP_ARGUMENT VARCHAR(255),
         OUTPUT_STREAMING SMALLINT,
         PRIMARY KEY(TEMPLATE_ID,OUTPUT_KEY),
         FOREIGN KEY (TEMPLATE_ID) REFERENCES WORKFLOW(TEMPLATE_ID) ON DELETE CASCADE
);

CREATE TABLE COMPONENT_STATUS
(
        STATUS_ID VARCHAR (255) NOT NULL,
        TEMPLATE_ID VARCHAR (255) NOT NULL,
        STATE VARCHAR(255),
        REASON VARCHAR(255),
        UPDATE_TIME TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
        PRIMARY KEY (STATUS_ID),
        FOREIGN KEY (TEMPLATE_ID) REFERENCES WORKFLOW(TEMPLATE_ID) ON DELETE CASCADE
);

CREATE TABLE WORKFLOW_STATUS
(
        STATUS_ID VARCHAR (255) NOT NULL,
        TEMPLATE_ID VARCHAR (255) NOT NULL,
        STATE VARCHAR(255),
        REASON VARCHAR(255),
        UPDATE_TIME TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
        PRIMARY KEY (STATUS_ID, TEMPLATE_ID),
        FOREIGN KEY (TEMPLATE_ID) REFERENCES WORKFLOW(TEMPLATE_ID) ON DELETE CASCADE
);

CREATE TABLE EDGE
(
        EDGE_ID VARCHAR (255) NOT NULL,
        TEMPLATE_ID VARCHAR (255) NOT NULL,
        NAME VARCHAR (255),
        COMPONENT_STATUS_ID VARCHAR(255),
        DESCRIPTION VARCHAR(500),
        CREATED_TIME TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
        PRIMARY KEY (EDGE_ID, TEMPLATE_ID),
        FOREIGN KEY (TEMPLATE_ID) REFERENCES WORKFLOW(TEMPLATE_ID) ON DELETE CASCADE
);

CREATE TABLE PORT
(
        PORT_ID VARCHAR (255) NOT NULL,
        TEMPLATE_ID VARCHAR (255) NOT NULL,
        NAME VARCHAR (255),
        COMPONENT_STATUS_ID VARCHAR(255),
        DESCRIPTION VARCHAR(500),
        CREATED_TIME TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
        PRIMARY KEY (PORT_ID, TEMPLATE_ID),
        FOREIGN KEY (TEMPLATE_ID) REFERENCES WORKFLOW(TEMPLATE_ID) ON DELETE CASCADE
);

CREATE TABLE NODE
(
        NODE_ID VARCHAR (255) NOT NULL,
        TEMPLATE_ID VARCHAR (255) NOT NULL,
        NAME VARCHAR (255),
        APPLICATION_ID VARCHAR (255),
        APPLICATION_NAME VARCHAR (255),
        COMPONENT_STATUS_ID VARCHAR(255),
        DESCRIPTION VARCHAR(500),
        CREATED_TIME TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
        PRIMARY KEY (NODE_ID, TEMPLATE_ID),
        FOREIGN KEY (TEMPLATE_ID) REFERENCES WORKFLOW(TEMPLATE_ID) ON DELETE CASCADE
);