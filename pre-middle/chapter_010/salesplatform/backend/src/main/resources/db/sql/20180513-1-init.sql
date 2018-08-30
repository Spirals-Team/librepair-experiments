-- create tables
CREATE TABLE IF NOT EXISTS body (
    id          SERIAL PRIMARY KEY,
    type        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS fuel (
    id          SERIAL PRIMARY KEY,
    type        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS transmission (
    id          SERIAL PRIMARY KEY,
    type        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS category (
    id          SERIAL PRIMARY KEY,
    type        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS manufacture (
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS car_model (
    id             SERIAL PRIMARY KEY,
    name           TEXT    NOT NULL UNIQUE,
    manufacture_id INTEGER NOT NULL REFERENCES manufacture (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS cars (
    id              SERIAL PRIMARY KEY,
    body_id         INTEGER          NOT NULL REFERENCES body (id) ON DELETE SET NULL,
    category_id     INTEGER          NOT NULL REFERENCES category (id) ON DELETE SET NULL,
    fuel_id         INTEGER          NOT NULL REFERENCES fuel (id) ON DELETE SET NULL,
    model_id        INTEGER          NOT NULL REFERENCES car_model (id) ON DELETE SET NULL,
    transmission_id INTEGER          NOT NULL REFERENCES transmission (id) ON DELETE SET NULL,
    engine_size     DOUBLE PRECISION NOT NULL,
    mileage         INTEGER          NOT NULL,
    year            INTEGER          NOT NULL
);

CREATE TABLE IF NOT EXISTS role (
    id   SERIAL PRIMARY KEY,
    type CHARACTER VARYING(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id      SERIAL PRIMARY KEY,
    email   CHARACTER VARYING(100) UNIQUE NOT NULL,
    passw   CHARACTER VARYING(100)        NOT NULL,
    role_id INTEGER REFERENCES role (id) ON DELETE SET NULL,
    phone   TEXT
);

CREATE TABLE IF NOT EXISTS posts (
    id           SERIAL PRIMARY KEY,
    content      TEXT,
    price        DOUBLE PRECISION NOT NULL,
    is_active    BOOLEAN          NOT NULL,
    car_id       INTEGER REFERENCES cars (id) ON DELETE SET NULL,
    user_id      INTEGER REFERENCES users (id) ON DELETE SET NULL,
    publish_date TIMESTAMP        NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS pictures (
    id      SERIAL PRIMARY KEY,
    data    BYTEA,
    post_id INTEGER REFERENCES posts (id) ON DELETE SET NULL
);
