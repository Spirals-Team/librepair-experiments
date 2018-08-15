--
--    Copyright 2010-2016 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

-- // create program orderable table
-- Migration SQL that makes the change goes here.

CREATE TABLE core.program_orderable (
    id character varying NOT NULL,
    program_id character varying NOT NULL,
    orderable_id character varying NOT NULL,
    doses_per_patient integer,
    active boolean,
    full_supply boolean,
    server_version bigint,
    date_deleted bigint,
    PRIMARY KEY (id)
) WITH (
    OIDS=FALSE
) TABLESPACE core_space;

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE core.program_orderable;
