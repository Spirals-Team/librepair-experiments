DELETE FROM users;
DELETE FROM contexts;
DELETE FROM portfolios;
DELETE FROM tasks;
DELETE FROM users_roles;
DELETE FROM external_contexts_internal_contexts;
DELETE FROM tasks_contexts;
DELETE FROM tasks_pointed_completion_states;
DELETE FROM external_tasks_internal_tasks;
DELETE FROM tasks_blocked_by_the_task_tasks_blocking_the_task;
DELETE FROM tasks_with_relatives_unlocked_by_the_task_tasks_unlocking_the_task_relatives;

INSERT INTO users (email, display_name, password) VALUES
  ('initializedBySqlScriptUser1@email.com', 'initializedBySqlScriptUser1DisplayName',
   'initializedBySqlScriptUser1Password'),
  ('initializedBySqlScriptUser2@email.com', 'initializedBySqlScriptUser2DisplayName',
   'initializedBySqlScriptUser2Password'),
  ('initializedBySqlScriptAdmin3@email.com', 'initializedBySqlScriptAdmin3DisplayName',
   'initializedBySqlScriptAdmin3Password'),
  ('initializedBySqlScriptUserAdmin4@email.com', 'initializedBySqlScriptUserAdmin4DisplayName',
   'initializedBySqlScriptUserAdmin4Password');

INSERT INTO users_roles (user_id, ROLE)
VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_USER'),
  (3, 'ROLE_ADMIN'),
  (4, 'ROLE_USER'),
  (4, 'ROLE_ADMIN');

INSERT INTO contexts (user_id, display_name, description) VALUES
  (1, 'initializedBySqlScriptUser1Context1DisplayName',
   'initializedBySqlScriptUser1Context1Description'),
  (1, 'initializedBySqlScriptUser1Context2DisplayName',
   'initializedBySqlScriptUser1Context2Description'),
  (2, 'initializedBySqlScriptUser2Context3DisplayName',
   'initializedBySqlScriptUser2Context3Description'),
  (2,
   'initializedBySqlScriptUser2Context4WithExternalContext5And6WithInternalContext7And8DisplayName',
   'initializedBySqlScriptUser2Context4Description'),
  (2, 'initializedBySqlScriptUser2Context5WithInternalContext4DisplayName',
   'initializedBySqlScriptUser2Context5Description'),
  (2, 'initializedBySqlScriptUser2Context6WithInternalContext4DisplayName',
   'initializedBySqlScriptUser2Context6Description'),
  (2, 'initializedBySqlScriptUser2Context7WithExternalContext4DisplayName',
   'initializedBySqlScriptUser2Context7Description'),
  (2, 'initializedBySqlScriptUser2Context8WithExternalContext4DisplayName',
   'initializedBySqlScriptUser2Context8Description');

INSERT INTO external_contexts_internal_contexts (external_context_id, internal_context_id)
VALUES
  (4, 7),
  (4, 8),
  (5, 4),
  (6, 4);

INSERT INTO portfolios (user_id, display_name, description) VALUES
  (1, 'initializedBySqlScriptUser1Portfolio1DisplayName',
   'initializedBySqlScriptUser1Portfolio1Description'),
  (1, 'initializedBySqlScriptUser1Portfolio2DisplayName',
   'initializedBySqlScriptUser1Portfolio2Description'),
  (2, 'initializedBySqlScriptUser2Portfolio3DisplayName',
   'initializedBySqlScriptUser2Portfolio3Description'),
  (2, 'initializedBySqlScriptUser2Portfolio4DisplayName',
   'initializedBySqlScriptUser2Portfolio4Description');

INSERT INTO tasks (display_name, user_id, description, planned_start_task_timestamp, planned_stop_task_timestamp, internal_execution_state, self_completion_state)
VALUES ();

INSERT INTO tasks_contexts (task_id, context_id)
VALUES ();

INSERT INTO external_tasks_internal_tasks (external_task_id, internal_task_id)
VALUES ();

INSERT INTO tasks_blocked_by_the_task_tasks_blocking_the_task (task_blocked_by_the_task_id, task_blocking_the_task_id)
VALUES();

INSERT INTO tasks_pointed_completion_states (task_id, pointed_completion_state)
VALUES();

INSERT INTO tasks_with_relatives_unlocked_by_the_task_tasks_unlocking_the_task_relatives (
  task_with_relatives_unlocked_by_the_task_id, task_unlocking_the_task_relatives_id)
VALUES();