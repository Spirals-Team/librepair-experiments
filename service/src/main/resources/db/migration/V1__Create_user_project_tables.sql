CREATE TABLE aero.users (
  userId SERIAL PRIMARY KEY,
  userName VARCHAR(64) NOT NULL UNIQUE,
  userPassword VARCHAR(64) NOT NULL,
  userEmail VARCHAR(128) NOT NULL UNIQUE,
  userLevel SMALLINT DEFAULT 0
);
CREATE TABLE aero.projects (
  projectId SERIAL PRIMARY KEY,
  projectName VARCHAR(64) NOT NULL UNIQUE,
  projectOwner BIGINT,
  projectType VARCHAR(32),
  projectDescription TEXT
);