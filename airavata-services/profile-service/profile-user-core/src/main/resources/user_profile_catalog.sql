CREATE TABLE IF NOT EXISTS USER_PROFILE (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    USER_ID VARCHAR (255),
    GATEWAY_ID VARCHAR (255),
    USER_MODEL_VERSION VARCHAR (255),
    USER_NAME VARCHAR (255),
    ORCID_ID VARCHAR (255),
    COUNTRY VARCHAR (255),
    HOME_ORGANIZATION VARCHAR (255),
    ORIGINATION_AFFILIATION VARCHAR (255),
    CREATION_TIME BIGINT,
    LAST_ACCESS_TIME BIGINT,
    VALID_UNTIL BIGINT,
    STATE VARCHAR (255),
    COMMENTS TEXT,
    GPG_KEY VARCHAR (8192),
    TIME_ZONE VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID)
);

CREATE TABLE IF NOT EXISTS USER_PROFILE_EMAIL (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    EMAIL VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID, EMAIL),
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES USER_PROFILE(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS USER_PROFILE_PHONE (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    PHONE VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID, PHONE ),
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES USER_PROFILE(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS USER_PROFILE_NATIONALITY (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    NATIONALITY VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID, NATIONALITY ),
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES USER_PROFILE(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS USER_PROFILE_LABELED_URI (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    LABELED_URI VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID, LABELED_URI ),
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES USER_PROFILE(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS NSF_DEMOGRAPHIC (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    GENDER VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID),
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES USER_PROFILE(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS NSF_DEMOGRAPHIC_ETHNICITY (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    ETHNICITY VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID, ETHNICITY ),
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES NSF_DEMOGRAPHIC(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS NSF_DEMOGRAPHIC_RACE (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    RACE VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID, RACE ),
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES NSF_DEMOGRAPHIC(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS NSF_DEMOGRAPHIC_DISABILITY (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    DISABILITY VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID, DISABILITY ),
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES NSF_DEMOGRAPHIC(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS CUSTOMIZED_DASHBOARD (
    AIRAVATA_INTERNAL_USER_ID VARCHAR (255),
    ENABLED_EXPERIMENT_ID VARCHAR (255),
    ENABLED_NAME VARCHAR (255),
    ENABLED_DESCRIPTION VARCHAR (255),
    ENABLED_PROJECT VARCHAR (255),
    ENABLED_OWNER VARCHAR (255),
    ENABLED_APPLICATION VARCHAR (255),
    ENABLED_COMPUTE_RESOURCE VARCHAR (255),
    ENABLED_JOB_NAME VARCHAR (255),
    ENABLED_JOB_ID VARCHAR (255),
    ENABLED_JOB_STATUS VARCHAR (255),
    ENABLED_JOB_CREATION_TIME VARCHAR (255),
    ENABLED_NOTIFICATIONS_TO VARCHAR (255),
    ENABLED_WORKING_DIR VARCHAR (255),
    ENABLED_JOB_DESCRIPTION VARCHAR (255),
    ENABLED_CREATION_TIME VARCHAR (255),
    ENABLED_LAST_MODIFIED_TIME VARCHAR (255),
    ENABLED_WALL_TIME VARCHAR (255),
    ENABLED_CPU_COUNT VARCHAR (255),
    ENABLED_NODE_COUNT VARCHAR (255),
    ENABLED_QUEUE VARCHAR (255),
    ENABLED_INPUTS VARCHAR (255),
    ENABLED_OUTPUTS VARCHAR (255),
    ENABLED_STORAGE_DIR VARCHAR (255),
    ENABLED_ERRORS VARCHAR (255),
    PRIMARY KEY (AIRAVATA_INTERNAL_USER_ID)
    FOREIGN KEY (AIRAVATA_INTERNAL_USER_ID) REFERENCES USER_PROFILE(AIRAVATA_INTERNAL_USER_ID) ON DELETE CASCADE
);

CREATE TABLE CONFIGURATION
(
        CONFIG_KEY VARCHAR(255),
        CONFIG_VAL VARCHAR(255),
        PRIMARY KEY(CONFIG_KEY, CONFIG_VAL)
);

INSERT INTO CONFIGURATION (CONFIG_KEY, CONFIG_VAL) VALUES('user_profile_catalog_version', '0.17');