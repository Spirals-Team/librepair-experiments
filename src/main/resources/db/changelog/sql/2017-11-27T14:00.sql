
-- Rename user table
ALTER TABLE security.user RENAME to users;

ALTER TABLE security.users RENAME user_id to id;

DROP SEQUENCE security.user_user_id_seq CASCADE;

CREATE SEQUENCE security.users_id_seq;
ALTER TABLE security.users ALTER COLUMN id SET NOT NULL;
ALTER TABLE security.users ALTER COLUMN id SET DEFAULT nextval('security.users_id_seq');
ALTER SEQUENCE security.users_id_seq OWNED BY security.users.id;

-- Rename security.users_roles table

ALTER TABLE security.user_x_role RENAME to users_roles;

ALTER TABLE security.users_roles RENAME user_x_role_id to id;

DROP SEQUENCE security.user_x_role_user_x_role_id_seq CASCADE;

CREATE SEQUENCE security.users_roles_id_seq;
ALTER TABLE security.users_roles ALTER COLUMN id SET NOT NULL;
ALTER TABLE security.users_roles ALTER COLUMN id SET DEFAULT nextval('security.users_roles_id_seq');
ALTER SEQUENCE security.users_roles_id_seq OWNED BY security.users_roles.id;



-- Insert default users and roles
UPDATE security.users SET id = nextval('security.users_id_seq') WHERE username = 'admin';

INSERT INTO security.users (username, pass, active)
    VALUES ('app_admin', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', true);

INSERT INTO security.users (username, pass, active)
  VALUES ('hub_admin', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', true);

INSERT INTO security.users (username, pass, active)
  VALUES ('user', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', true);



UPDATE security.users_roles SET role = 'ROLE_ROOT', id = nextval('security.users_roles_id_seq') where role = 'ADMIN';

INSERT INTO security.users_roles (user_id, role)
  VALUES (
    (SELECT id FROM security.users WHERE username = 'app_admin'),
    'ROLE_APP_ADMIN');
INSERT INTO security.users_roles (user_id, role)
  VALUES (
  (SELECT id FROM security.users WHERE username = 'hub_admin'),
  'ROLE_HUB_ADMIN');
INSERT INTO security.users_roles (user_id, role)
  VALUES (
    (SELECT id FROM security.users WHERE username = 'user'),
    'ROLE_USER');
