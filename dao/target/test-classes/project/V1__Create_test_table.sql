CREATE TABLE project_test (
  prj_id SERIAL PRIMARY KEY,
  prj_name VARCHAR(64) NOT NULL UNIQUE,
  prj_owner BIGINT,
  prj_type VARCHAR(32),
  prj_description TEXT
)