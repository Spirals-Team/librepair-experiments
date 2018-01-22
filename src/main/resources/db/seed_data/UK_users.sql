-- Users
INSERT INTO security.users (username, pass, active)
    VALUES ('Transmit Enterprise', '$2a$10$JHRZw0sScIpREfz.BKU75Oaa0kwhJM78FSSCvNUFK9KPhkFT6nQMa', true);

INSERT INTO security.users (username, pass, active)
  VALUES ('Local Organization', '$2a$10$JHRZw0sScIpREfz.BKU75Oaa0kwhJM78FSSCvNUFK9KPhkFT6nQMa', true);

INSERT INTO security.users (username, pass, active)
  VALUES ('Enumerator', '$2a$10$JHRZw0sScIpREfz.BKU75Oaa0kwhJM78FSSCvNUFK9KPhkFT6nQMa', true);

-- Users roles
INSERT INTO security.users_roles (user_id, role)
  VALUES (
    (SELECT id FROM security.users WHERE username = 'Transmit Enterprise'),
    'ROLE_HUB_ADMIN');
INSERT INTO security.users_roles (user_id, role)
  VALUES (
  (SELECT id FROM security.users WHERE username = 'Local Organization'),
  'ROLE_APP_ADMIN');
INSERT INTO security.users_roles (user_id, role)
  VALUES (
  (SELECT id FROM security.users WHERE username = 'Enumerator'),
  'ROLE_SURVEY_USER');

-- Application
INSERT INTO ps_network.applications (name, code, description, is_active, country, city, information, is_hub, is_partner)
VALUES (
  'Transmit Enterprise',
  'fptransmitapp',
  'Transmit Enterprise Application',
  true,
  (SELECT id from system.countries where numeric_code = '826'),
  NULL,
  NULL,
  true,
  false);

-- Organization
INSERT INTO ps_network.organizations (name, code, description, is_active, country, information, application_id)
  VALUES ('Local Organization',
  'fptransmitlocalorg',
  'Local Organization',
  true,
  NULL,
  NULL,
  (SELECT id FROM ps_network.applications WHERE code = 'fptransmitapp'));


-- Users applications
INSERT INTO ps_network.users_applications(user_id, application_id, organization_id)
	VALUES (
	  (SELECT id from security.users WHERE username = 'Transmit Enterprise'),
	  (SELECT id from ps_network.applications WHERE code = 'fptransmitapp'),
	  NULL);

INSERT INTO ps_network.users_applications(user_id, application_id, organization_id)
	VALUES (
	  (SELECT id from security.users WHERE username = 'Local Organization'),
	  (SELECT id from ps_network.applications WHERE code = 'fptransmitapp'),
	  (SELECT id from ps_network.organizations WHERE code = 'fptransmitlocalorg'));

INSERT INTO ps_network.users_applications(user_id, application_id, organization_id)
	VALUES (
	  (SELECT id from security.users WHERE username = 'Enumerator'),
	  (SELECT id from ps_network.applications WHERE code = 'fptransmitapp'),
	  (SELECT id from ps_network.organizations WHERE code = 'fptransmitlocalorg'));


-- Table for terms and conditions
CREATE TABLE security.TERMCONDPOL (
	  id  bigint PRIMARY KEY,
	  html  text,
	  version  character varying (30),
	  year integer,
    created_date date,
    type_cod character varying (10)
);

CREATE SEQUENCE security.TERMCONDPOL_id_seq;
