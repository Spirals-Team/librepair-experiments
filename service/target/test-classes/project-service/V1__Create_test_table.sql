CREATE TABLE project_test (
  projectId SERIAL PRIMARY KEY,
  projectName VARCHAR(64) NOT NULL UNIQUE,
  projectOwner BIGINT,
  projectType VARCHAR(32),
  projectDescription TEXT
)