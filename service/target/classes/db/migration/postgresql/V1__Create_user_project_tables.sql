CREATE TABLE aero.users (
  usr_id SERIAL PRIMARY KEY,
  usr_name VARCHAR(64) NOT NULL UNIQUE,
  usr_password VARCHAR(64) NOT NULL,
  usr_email VARCHAR(128) NOT NULL UNIQUE,
  usr_level SMALLINT DEFAULT 0
);
CREATE TABLE aero.projects (
  prj_id SERIAL PRIMARY KEY,
  prj_name VARCHAR(64) NOT NULL UNIQUE,
  prj_owner BIGINT,
  prj_type VARCHAR(32),
  prj_description TEXT
);