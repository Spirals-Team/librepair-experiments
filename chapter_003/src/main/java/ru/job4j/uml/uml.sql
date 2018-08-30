-- Create db
CREATE DATABASE request_db;

-- Create tables
CREATE TABLE role (
    id          SERIAL PRIMARY KEY,
    role        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE rules (
    id          SERIAL PRIMARY KEY,
    rule        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE users (
    id           SERIAL PRIMARY KEY,
    login        CHARACTER VARYING(100) UNIQUE NOT NULL,
    password     CHARACTER VARYING(100)        NOT NULL,
    user_role_id INTEGER                       NOT NULL REFERENCES role (id),
    create_date  TIMESTAMP                     NOT NULL DEFAULT now()
);

CREATE TABLE role_rules (
    role_id INTEGER REFERENCES role (id),
    rule_id INTEGER REFERENCES rules (id)
);

CREATE TABLE category (
    id          SERIAL PRIMARY KEY,
    category    CHARACTER VARYING(100),
    description TEXT
);

CREATE TABLE state (
    id          SERIAL PRIMARY KEY,
    state       CHARACTER VARYING(100),
    description TEXT
);

CREATE TABLE items (
    id          SERIAL PRIMARY KEY,
    description TEXT,
    user_id     INTEGER REFERENCES users (id),
    category_id INTEGER REFERENCES category (id),
    state_id    INTEGER REFERENCES state (id),
    create_date TIMESTAMP NOT NULL DEFAULT now(),
    update_date TIMESTAMP
);

CREATE TABLE commens (
    id          SERIAL PRIMARY KEY,
    comment     TEXT,
    item_id     INTEGER REFERENCES items (id),
    create_date TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE attachs (
    id          SERIAL PRIMARY KEY,
    attachment  BYTEA,
    item_id     INTEGER REFERENCES items (id),
    create_date TIMESTAMP NOT NULL DEFAULT now()
);

-- Populate db
INSERT INTO role (role, description) VALUES ('admin', 'role admin');
INSERT INTO role (role, description) VALUES ('user', 'role user');
INSERT INTO role (role, description) VALUES ('guest', 'role guest');

INSERT INTO rules (rule, description) VALUES ('create', 'create item');
INSERT INTO rules (rule, description) VALUES ('edit', 'edit item');
INSERT INTO rules (rule, description) VALUES ('view', 'view item');
INSERT INTO rules (rule, description) VALUES ('delete', 'delete item');

INSERT INTO users (login, password, user_role_id)
VALUES ('kent', '44>Sx*vW', 1);
INSERT INTO users (login, password, user_role_id)
VALUES ('rose', 'L@/Nc6#v', 2);
INSERT INTO users (login, password, user_role_id)
VALUES ('anette', '3k3(KjxD', 3);

INSERT INTO role_rules (role_id, rule_id) VALUES (1, 1);
INSERT INTO role_rules (role_id, rule_id) VALUES (1, 2);
INSERT INTO role_rules (role_id, rule_id) VALUES (1, 3);
INSERT INTO role_rules (role_id, rule_id) VALUES (1, 4);
INSERT INTO role_rules (role_id, rule_id) VALUES (2, 1);
INSERT INTO role_rules (role_id, rule_id) VALUES (2, 2);
INSERT INTO role_rules (role_id, rule_id) VALUES (2, 3);
INSERT INTO role_rules (role_id, rule_id) VALUES (3, 3);

INSERT INTO category (category, description) VALUES ('transport', 'car, train');
INSERT INTO category (category, description) VALUES ('food', 'meat, icecream');
INSERT INTO category (category, description) VALUES ('other', 'etc.');

INSERT INTO state (state, description) VALUES ('new', 'newly created item');
INSERT INTO state (state, description) VALUES ('assign', 'implementation');
INSERT INTO state (state, description) VALUES ('reject', 'rejected');

INSERT INTO items (description, user_id, category_id, state_id)
VALUES ('first request', 2, 1, 1);
INSERT INTO items (description, user_id, category_id, state_id)
VALUES ('second request', 2, 1, 3);

INSERT INTO commens (comment, item_id) VALUES ('do it', 1);
INSERT INTO commens (comment, item_id) VALUES ('stop', 2);

INSERT INTO attachs (attachment, item_id) VALUES ('attach1', 1);