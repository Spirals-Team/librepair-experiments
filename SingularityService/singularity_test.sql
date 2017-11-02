CREATE TABLE requestHistory (
  requestId VARCHAR(100) NOT NULL,
  createdAt TIMESTAMP NOT NULL DEFAULT '1971-01-01 00:00:01',
  requestState VARCHAR(25) NOT NULL,
  user VARCHAR(100) NULL,
  message VARCHAR(280) NULL,
  request BLOB NOT NULL,
  PRIMARY KEY (requestId, createdAt)
);

CREATE TABLE deployHistory (
  requestId VARCHAR(100) NOT NULL,
  deployId VARCHAR(100) NOT NULL,
  createdAt TIMESTAMP NOT NULL DEFAULT '1971-01-01 00:00:01',
  user VARCHAR(100) NULL,
  message VARCHAR(280) NULL,
  deployStateAt TIMESTAMP NOT NULL DEFAULT '1971-01-01 00:00:01',
  deployState VARCHAR(25) NOT NULL,
  bytes BLOB NOT NULL,
  PRIMARY KEY (requestId, deployId),
);

CREATE TABLE taskHistory (
  taskId VARCHAR(200) PRIMARY KEY,
  deployId VARCHAR(100) NULL,
  requestId VARCHAR(100) NOT NULL,
  updatedAt TIMESTAMP NOT NULL DEFAULT '1971-01-01 00:00:01',
  startedAt TIMESTAMP NULL,
  host VARCHAR(100) NULL,
  lastTaskStatus VARCHAR(25) NULL,
  runId VARCHAR(100) NULL,
  bytes BLOB NOT NULL,
  purged BOOLEAN NOT NULL DEFAULT false,
);
