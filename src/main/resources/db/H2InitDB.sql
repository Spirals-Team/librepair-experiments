DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS contexts;
DROP TABLE IF EXISTS portfolios;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS external_contexts_internal_contexts;
DROP TABLE IF EXISTS tasks_contexts;
DROP TABLE IF EXISTS tasks_pointed_completion_states;
DROP TABLE IF EXISTS external_tasks_internal_tasks;
DROP TABLE IF EXISTS tasks_blocked_by_the_task_tasks_blocking_the_task;
DROP TABLE IF EXISTS tasks_with_relatives_unlocked_by_the_task_tasks_unlocking_the_task_relatives;

CREATE TABLE users
(
  id                INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
  email             VARCHAR UNIQUE                     NOT NULL,
  display_name      VARCHAR UNIQUE                     NOT NULL,
  password          VARCHAR                            NOT NULL,
  registration_time TIMESTAMP DEFAULT now()            NOT NULL,
);

CREATE TABLE contexts
(
  id           INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
  user_id      INTEGER                            NOT NULL,
  display_name VARCHAR                            NOT NULL,
  description  VARCHAR,

  CONSTRAINT user_id_context_display_name UNIQUE (user_id, display_name),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE portfolios
(
  id           INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
  user_id      INTEGER                            NOT NULL,
  display_name VARCHAR                            NOT NULL,
  description  VARCHAR,

  CONSTRAINT user_id_portfolio_display_name UNIQUE (user_id, display_name),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tasks
(
  id                           INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
  user_id                      INTEGER                            NOT NULL,
  display_name                 VARCHAR                            NOT NULL,
  description                  VARCHAR,
  planned_start_task_timestamp TIMESTAMP                          NOT NULL,
  planned_stop_task_timestamp  TIMESTAMP                          NOT NULL,
  actual_start_task_timestamp  TIMESTAMP,
  actual_stop_task_timestamp   TIMESTAMP,
  portfolio_id                 INTEGER,
  task_metric_description      VARCHAR,
  task_metric_importance       DOUBLE,
  task_metric_urgency          DOUBLE,
  task_metric_stability        DOUBLE,
  internal_execution_state     VARCHAR,
  self_completion_state        VARCHAR,

  CONSTRAINT user_id_task_display_name UNIQUE (user_id, display_name),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (portfolio_id) REFERENCES portfolios (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE users_roles (
  user_id INTEGER NOT NULL,
  role    VARCHAR NOT NULL,
  CONSTRAINT user_id_role UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE external_contexts_internal_contexts (
  external_context_id INTEGER NOT NULL,
  internal_context_id VARCHAR NOT NULL,
  CONSTRAINT external_context_id_internal_context_id UNIQUE (
    external_context_id,
    internal_context_id),
  FOREIGN KEY (external_context_id) REFERENCES contexts (id),
  FOREIGN KEY (internal_context_id) REFERENCES contexts (id),
);

CREATE TABLE tasks_contexts (
  task_id    INTEGER NOT NULL,
  context_id INTEGER NOT NULL,
  CONSTRAINT task_id_context_id UNIQUE (task_id, context_id),
  FOREIGN KEY (task_id) REFERENCES tasks (id),
  FOREIGN KEY (context_id) REFERENCES contexts (id),
);

CREATE TABLE tasks_pointed_completion_states (
  task_id                  INTEGER NOT NULL,
  pointed_completion_state VARCHAR NOT NULL,
  CONSTRAINT task_id_pointed_completion_state UNIQUE (task_id, pointed_completion_state),
  FOREIGN KEY (task_id) REFERENCES tasks (id),
);

CREATE TABLE external_tasks_internal_tasks (
  external_task_id INTEGER NOT NULL,
  internal_task_id VARCHAR NOT NULL,
  CONSTRAINT external_task_id_internal_task_id UNIQUE (external_task_id, internal_task_id),
  FOREIGN KEY (external_task_id) REFERENCES tasks (id),
  FOREIGN KEY (internal_task_id) REFERENCES tasks (id),
);

CREATE TABLE tasks_blocked_by_the_task_tasks_blocking_the_task (
  task_blocked_by_the_task_id INTEGER NOT NULL,
  task_blocking_the_task_id   VARCHAR NOT NULL,
  CONSTRAINT task_blocked_by_the_task_id_task_blocking_the_task_id UNIQUE (
    task_blocked_by_the_task_id,
    task_blocking_the_task_id),
  FOREIGN KEY (task_blocked_by_the_task_id) REFERENCES tasks (id),
  FOREIGN KEY (task_blocking_the_task_id) REFERENCES tasks (id)
);

CREATE TABLE tasks_with_relatives_unlocked_by_the_task_tasks_unlocking_the_task_relatives (
  task_with_relatives_unlocked_by_the_task_id INTEGER NOT NULL,
  task_unlocking_the_task_relatives_id        VARCHAR NOT NULL,
  CONSTRAINT task_with_relatives_unlocked_by_the_task_id_task_unlocking_the_task_relatives_id UNIQUE (
    task_with_relatives_unlocked_by_the_task_id,
    task_unlocking_the_task_relatives_id),
  FOREIGN KEY (task_with_relatives_unlocked_by_the_task_id) REFERENCES tasks (id),
  FOREIGN KEY (task_unlocking_the_task_relatives_id) REFERENCES tasks (id)
);