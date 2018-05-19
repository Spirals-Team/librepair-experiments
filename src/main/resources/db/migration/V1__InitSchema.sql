CREATE TABLE appuser (
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(80),
    password      VARCHAR(80),
    updated_at    TIMESTAMP,
    created_at    TIMESTAMP
);

CREATE TABLE todo (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT,
    description   TEXT,
    completed_at  TIMESTAMP,
    updated_at    TIMESTAMP,
    created_at    TIMESTAMP
);

ALTER TABLE todo
    ADD CONSTRAINT fk_user_on_todo
    FOREIGN KEY (user_id)
    REFERENCES appuser (id);
