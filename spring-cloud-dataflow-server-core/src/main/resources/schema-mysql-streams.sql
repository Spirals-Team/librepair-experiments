CREATE TABLE STREAM_DEFINITIONS  (
	DEFINITION_NAME VARCHAR(255) NOT NULL PRIMARY KEY,
	DEFINITION TEXT DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE STREAM_DEPLOYMENTS  (
	STREAM_NAME VARCHAR(255) NOT NULL PRIMARY KEY,
	DEPLOYER_NAME VARCHAR(255) NOT NULL,
	PACKAGE_NAME VARCHAR(255),
	RELEASE_NAME VARCHAR(255),
	REPO_NAME VARCHAR(255)
) ENGINE=InnoDB;
