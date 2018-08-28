CREATE TABLE roles (
  id   SERIAL PRIMARY KEY,
  role VARCHAR(20)
);

CREATE TABLE rules (
  id          SERIAL PRIMARY KEY,
  description VARCHAR(30)
);

CREATE TABLE roles_rules (
  id       SERIAL PRIMARY KEY,
  role_id  INTEGER REFERENCES roles (id),
  rules_id INTEGER REFERENCES rules (id)
);

CREATE TABLE users (
  id       SERIAL PRIMARY KEY,
  login    VARCHAR(20),
  password VARCHAR(20),
  role_id  INTEGER REFERENCES roles (id)
);

CREATE TABLE category (
  id       SERIAL PRIMARY KEY,
  category VARCHAR(30)
);

CREATE TABLE state (
  id     SERIAL PRIMARY KEY,
  status VARCHAR(20)
);

CREATE TABLE items (
  id          SERIAL PRIMARY KEY,
  name        VARCHAR(30),
  state_id    INTEGER REFERENCES state (id),
  user_id     INTEGER REFERENCES users (id),
  category_id INTEGER REFERENCES category (id)
);

CREATE TABLE attached (
  id      SERIAL PRIMARY KEY,
  file    TEXT,
  item_id INTEGER REFERENCES items (id)
);

CREATE TABLE comments (
  id      SERIAL PRIMARY KEY,
  comment TEXT,
  item_id INTEGER REFERENCES items (id)
);