-- DROP TABLE purchase;
-- DROP SEQUENCE global_seq;

CREATE TABLE purchase (
  id           BIGINT           NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 0, INCREMENT BY 1),
--   id           BIGINT DEFAULT nextval('global_seq'),
  money        DOUBLE PRECISION NOT NULL,
  created_date TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  category     VARCHAR(50)      NOT NULL,
  comment      VARCHAR(50),
  CONSTRAINT primary_key_purchase PRIMARY KEY (id)
);
-- CREATE SEQUENCE global_seq
--   AS BIGINT
--   START WITH 0;