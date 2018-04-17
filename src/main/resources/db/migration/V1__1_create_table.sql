CREATE TABLE scheduled_job
(
  id bigint NOT NULL,
  path character varying(512) NOT NULL,
  schedule character varying(255) NOT NULL,
  status character varying(512) NOT NULL,
  enabled integer NOT NULL
)