CREATE TABLE avatar_test (
  av_id SERIAL PRIMARY KEY,
  av_owner BIGINT,
  av_data BYTEA NOT NULL,
  av_type VARCHAR(32)
)