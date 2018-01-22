-- This script create the first schemas and tables for the project. --

-- Create Schema --
CREATE SCHEMA security;
CREATE SCHEMA system;
CREATE SCHEMA ps_network;
CREATE SCHEMA ps_families;
CREATE SCHEMA survey;
CREATE SCHEMA data_collect;

-- Schema: security --
CREATE TABLE security.user (
	  user_id  bigserial PRIMARY KEY,
	  username  character varying (30),
	  pass  character varying (30),
	  active boolean
);

CREATE TABLE security.user_x_role (
	  user_x_role_id bigserial PRIMARY KEY,
	  user_id bigint,
	  role character varying (50)
);


-- Schema: system --
CREATE TABLE system.parameter (
	  parameter_id bigserial PRIMARY KEY,
	  key_parameter  integer,
	  description character varying (100),
	  type_parameter character varying (50)
);

CREATE TABLE system.country (
	  country_id bigserial PRIMARY KEY,
	  country  character varying (20)
);

CREATE TABLE system.city (
	  city_id bigserial PRIMARY KEY,
	  city character varying (20)
);

-- Schema: ps_network --
CREATE TABLE ps_network.application (
	  application_id bigserial PRIMARY KEY,
	  name  character varying (50),
	  code character varying (50),
	  description character varying,
	  is_active boolean,
	  country bigint,
	  city bigint,
	  information character varying (200),
	  is_hub boolean,
	  is_direct boolean
);

CREATE TABLE ps_network.organization (
	  organization_id bigserial PRIMARY KEY,
	  name character varying (50),
	  code integer,
	  description character varying (200),
	  is_active boolean,
	  country bigint,
	  information character varying (200),
	  application_id integer
);

CREATE TABLE ps_network.user_x_application (
	  user_x_application_id bigserial PRIMARY KEY,
	  user_id bigint,
	  application_id bigint,
	  organization_id bigint
);

--Schema: ps_families --
CREATE TABLE ps_families.family (
	  family_id bigserial PRIMARY KEY,
	  name character varying (50),
	  country bigint,
	  city bigint,
	  location_type character varying (30),
	  location_position_gps character varying (50),
	  person_reference_id bigint,
	  application_id bigint,
	  organization_id bigint
);

CREATE TABLE ps_families.family_socialeconomical_attribute (
	  family_socialeconomical_attribute_id bigserial PRIMARY KEY,
	  family_id bigint,
	  income double precision,
	  study_level character varying (30),
	  family_date date
);

CREATE TABLE ps_families.family_socialeconomical_attribute_active (
	  family_socialeconomical_attribute_active_id bigserial PRIMARY KEY,
	  application_id bigint,
	  column_name character varying (50),
	  is_active boolean
);

CREATE TABLE ps_families.person (
	  person_id bigserial PRIMARY KEY,
	  name character varying (50),
	  lastname character varying (50),
	  identification_type character varying (50),
	  identification_number character varying (50),
	  person_role character varying (50),
	  gender char,
	  activity_primary character varying (100),
	  activity_secundary character varying (100),
	  phone_number character varying (50),
	  country bigint,
	  city bigint,
	  family_id bigint
);

-- Schema: survey --
CREATE TABLE survey.survey_template (
	  survey_template_id bigserial PRIMARY KEY,
	  name character varying (50),
	  created_by_user_id bigint,
	  date_created date,
	  survey_indicador_template_id bigint,
	  survey_socioeconomics_template_id bigint,
	  comments character varying (200)
);

CREATE TABLE survey.survey_indicador_template (
	  survey_indicador_template_id bigserial PRIMARY KEY,
	  name character varying (50),
	  configuration character varying (100),
	  comments character varying (200)
);

CREATE TABLE survey.survey_socioeconomics_template (
	  survey_indicador_template_id bigserial PRIMARY KEY,
	  name character varying (50),
	  configuracion character varying (100),
	  comments character varying (200)
);

CREATE TABLE survey.task (
	  task_id bigserial PRIMARY KEY,
	  family_id bigint,
	  status character varying (100),
	  description character varying (200)
);

CREATE TABLE survey.task_tracking(
	  task_tracking_id bigserial PRIMARY KEY,
	  task_id integer,
	  task_tracking_ancestor integer,
	  comment character varying (100),
	  is_status_changed boolean,
	  status_new character varying (100)
);

-- Schema: data_collect --
CREATE TABLE data_collect.survey_data_collect_base (
	  survey_data_collect_base_id bigserial PRIMARY KEY,
	  survey_template_id bigint,
	  survey_taken_by_user_id bigint,
	  survey_taken_date date,
	  interviewee_person_id bigint,
	  family_id bigint
);



-- FK --
ALTER TABLE security.user_x_role ADD CONSTRAINT fk_user_x_role_user_user_id FOREIGN KEY (user_id) REFERENCES security.user (user_id);

ALTER TABLE ps_network.application ADD CONSTRAINT fk_application_country_country_id FOREIGN KEY (country) REFERENCES system.country (country_id),
ADD CONSTRAINT fk_application_city_city_id FOREIGN KEY (city) REFERENCES system.city (city_id);

ALTER TABLE ps_network.organization ADD CONSTRAINT fk_organization_country_country_id FOREIGN KEY (country) REFERENCES system.city (city_id),
ADD CONSTRAINT fk_organization_application_application_id FOREIGN KEY (application_id) REFERENCES ps_network.application (application_id);

ALTER TABLE ps_network.user_x_application ADD CONSTRAINT fk_user_x_application_user_user_id FOREIGN KEY (user_id) REFERENCES security.user (user_id),
ADD CONSTRAINT fk_user_x_application_application_application_id FOREIGN KEY (application_id) REFERENCES ps_network.application (application_id),
ADD CONSTRAINT fk_user_x_application_organization_organization_id FOREIGN KEY (organization_id) REFERENCES ps_network.organization (organization_id);

ALTER TABLE ps_families.family ADD CONSTRAINT fk_family_country_country_id FOREIGN KEY (country) REFERENCES system.country (country_id),
ADD CONSTRAINT fk_family_person_person_id FOREIGN KEY (person_reference_id) REFERENCES ps_families.person (person_id),
ADD CONSTRAINT fk_famly_application_application_id FOREIGN KEY (application_id) REFERENCES ps_network.application (application_id),
ADD CONSTRAINT fk_famly_organization_organization_id FOREIGN KEY (organization_id) REFERENCES ps_network.organization (organization_id);

ALTER TABLE ps_families.family_socialeconomical_attribute ADD CONSTRAINT fk_family_socialeconomical_attribute_family_family_id FOREIGN KEY (family_id) REFERENCES ps_families.family (family_id);

ALTER TABLE ps_families.family_socialeconomical_attribute_active ADD CONSTRAINT fk_family_attribute_active_application_application_id FOREIGN KEY (application_id) REFERENCES ps_network.application (application_id);


ALTER TABLE ps_families.person ADD CONSTRAINT fk_person_country_country_id FOREIGN KEY (country) REFERENCES system.country (country_id),
ADD CONSTRAINT fk_person_city_city_id FOREIGN KEY (city) REFERENCES system.city (city_id),
ADD CONSTRAINT fk_person_family_family_id FOREIGN KEY (family_id) REFERENCES ps_families.family (family_id);

ALTER TABLE survey.survey_template ADD CONSTRAINT fk_survey_template_user_user_id FOREIGN KEY (created_by_user_id) REFERENCES security.user (user_id);

ALTER TABLE survey.task ADD CONSTRAINT fk_task_family_family_id FOREIGN KEY (family_id) REFERENCES ps_families.family (family_id);

ALTER TABLE survey.task_tracking ADD CONSTRAINT fk_task_tracking_task_task_id FOREIGN KEY (task_id) REFERENCES survey.task (task_id);

ALTER TABLE data_collect.survey_data_collect_base ADD CONSTRAINT fk_survey_data_collect_base_survey_template_survey_template_id FOREIGN KEY (survey_template_id) REFERENCES survey.survey_template (survey_template_id),
ADD CONSTRAINT fk_survey_data_collect_base_user_user_id FOREIGN KEY (survey_taken_by_user_id) REFERENCES security.user (user_id),
ADD CONSTRAINT fk_survey_data_collect_base_person_person_id FOREIGN KEY (interviewee_person_id) REFERENCES ps_families.person (person_id),
ADD CONSTRAINT fk_survey_data_collect_base_family_family_id FOREIGN KEY (family_id) REFERENCES ps_families.family (family_id);
