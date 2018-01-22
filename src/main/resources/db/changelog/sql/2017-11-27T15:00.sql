ALTER TABLE ps_network.user_x_application RENAME to users_applications;

ALTER TABLE ps_network.users_applications RENAME user_x_application_id to id;


DROP SEQUENCE ps_network.user_x_application_user_x_application_id_seq CASCADE;

CREATE SEQUENCE ps_network.users_applications_id_seq;
ALTER TABLE ps_network.users_applications ALTER COLUMN id SET NOT NULL;
ALTER TABLE ps_network.users_applications ALTER COLUMN id SET DEFAULT nextval('ps_network.users_applications_id_seq');
ALTER SEQUENCE ps_network.users_applications_id_seq OWNED BY ps_network.users_applications.id;


INSERT INTO ps_network.users_applications(user_id, application_id, organization_id)
	VALUES (
	  (SELECT id from security.users WHERE username = 'user'),
	  (SELECT id from ps_network.applications WHERE code = 'fpapp'),
	  (SELECT id from ps_network.organizations WHERE code = 'fporg'));

INSERT INTO ps_network.users_applications(user_id, application_id, organization_id)
	VALUES (
	  (SELECT id from security.users WHERE username = 'app_admin'),
	  (SELECT id from ps_network.applications WHERE code = 'fpapp'),
	  (SELECT id from ps_network.organizations WHERE code = 'fporg'));


INSERT INTO ps_network.users_applications(user_id, application_id, organization_id)
	VALUES (
	  (SELECT id from security.users WHERE username = 'hub_admin'),
	  (SELECT id from ps_network.applications WHERE code = 'fpapp'),
	  null);