CREATE TABLE system.activity (
	  activity_id  bigserial PRIMARY KEY,
	  user_id  bigint,
	  activity_type character varying (20),
	  description character varying (200),
	  organization_id bigint,
	  application_id bigint,
	  create_at TIMESTAMP
);

ALTER TABLE system.activity ADD CONSTRAINT fk_activity_user_user_id FOREIGN KEY (user_id) REFERENCES security.user (user_id),
    ADD CONSTRAINT fk_activity_organization_organization_id FOREIGN KEY (organization_id) REFERENCES ps_network.organization (organization_id),
    ADD CONSTRAINT fk_activity_application_application_id FOREIGN KEY (application_id) REFERENCES ps_network.application (application_id);

