CREATE TABLE aero.avatars (
    av_id SERIAL PRIMARY KEY,
    av_owner BIGINT,
    av_data BYTEA,
    av_type VARCHAR(32),
    FOREIGN KEY (av_owner) REFERENCES aero.users (usr_id)
)