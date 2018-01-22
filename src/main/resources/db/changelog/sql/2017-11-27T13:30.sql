

ALTER TABLE ps_network.application RENAME to applications;

ALTER TABLE ps_network.applications RENAME application_id to id;


DROP SEQUENCE ps_network.application_application_id_seq CASCADE;

CREATE SEQUENCE ps_network.applications_id_seq;
ALTER TABLE ps_network.applications ALTER COLUMN id SET NOT NULL;
ALTER TABLE ps_network.applications ALTER COLUMN id SET DEFAULT nextval('ps_network.applications_id_seq');
ALTER SEQUENCE ps_network.applications_id_seq OWNED BY ps_network.applications.id;

ALTER TABLE ps_network.applications RENAME is_organization TO is_partner;

ALTER TABLE ps_network.organizations ALTER COLUMN code TYPE VARCHAR(255);

INSERT INTO ps_network.applications (name, code, description, is_active, country, city, information, is_hub, is_partner)
VALUES (
  'Fundación Paraguaya',
  'fpapp',
  'Aplicación de la fundación',
  true,
  (SELECT id from system.countries where country = 'Paraguay'),
  (SELECT id from system.cities where city = 'Asunción'),
  NULL,
  true,
  false);

INSERT INTO ps_network.organizations (name, code, description, is_active, country, information, application_id)
  VALUES ('Fundación Paraguaya',
  'fporg',
  'Organización para uso de la fundación',
  true,
  (SELECT id FROM system.countries where country = 'Paraguay'),
  NULL,
  (SELECT id FROM ps_network.applications WHERE code = 'fpapp'));
