CREATE TABLE transmission (
  id   SERIAL PRIMARY KEY,
  type VARCHAR(20)
);

CREATE TABLE engine (
  id   SERIAL PRIMARY KEY,
  type VARCHAR(20)
);

CREATE TABLE chassis (
  id   SERIAL PRIMARY KEY,
  type VARCHAR(20)
);

CREATE TABLE car (
  id              SERIAL PRIMARY KEY,
  name            VARCHAR(20),
  engine_id       INTEGER REFERENCES engine (id),
  transmission_id INTEGER REFERENCES transmission (id),
  chassis_id      INTEGER REFERENCES chassis (id)
);
