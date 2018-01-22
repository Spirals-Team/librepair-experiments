ALTER TABLE ps_network.organization RENAME to organizations;

ALTER TABLE ps_network.organizations RENAME organization_id to id;


DROP SEQUENCE ps_network.organization_organization_id_seq CASCADE;

CREATE SEQUENCE ps_network.organizations_id_seq;
ALTER TABLE ps_network.organizations ALTER COLUMN id SET NOT NULL;
ALTER TABLE ps_network.organizations ALTER COLUMN id SET DEFAULT nextval('ps_network.organizations_id_seq');
ALTER SEQUENCE ps_network.organizations_id_seq OWNED BY ps_network.organizations.id;
