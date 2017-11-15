CREATE TABLE STREAM_DEFINITIONS  (
	DEFINITION_NAME VARCHAR(255) NOT NULL PRIMARY KEY,
	DEFINITION VARCHAR2(4000) DEFAULT NULL
);
CREATE TABLE STREAM_DEPLOYMENTS  (
	STREAM_NAME VARCHAR(255) NOT NULL PRIMARY KEY,
	DEPLOYER_NAME VARCHAR(255) NOT NULL,
	PACKAGE_NAME VARCHAR(255),
	RELEASE_NAME VARCHAR(255),
	REPO_NAME VARCHAR(255)
);
