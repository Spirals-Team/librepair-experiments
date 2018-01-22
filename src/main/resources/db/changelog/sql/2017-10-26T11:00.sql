CREATE TABLE data_collect.surveys
(
    id bigserial,
    description character varying(255),
    survey_definition jsonb,
    title character varying(255),
    CONSTRAINT surveys_pkey PRIMARY KEY (id)
);

CREATE TABLE data_collect.snapshots_indicators
(
    id bigserial,
    additional_properties jsonb,
    alimentation character varying(255),
    autonomy_decisions character varying(255) ,
    awareness_of_needs character varying(255),
    documentation character varying(255),
    drinking_water_access character varying(255),
    electricity_access character varying(255),
    garbage_disposal character varying(255),
    income character varying(255),
    influence_in_public_sector character varying(255),
    information_access character varying(255),
    middle_education character varying(255),
    nearby_health_post character varying(255) ,
    phone character varying(255) ,
    proper_kitchen character varying(255),
    read_and_write character varying(255),
    refrigerator character varying(255),
    safe_bathroom character varying(255),
    safe_house character varying(255),
    security character varying(255),
    self_esteem character varying(255),
    separate_bed character varying(255),
    separate_bedrooms character varying(255),
    social_capital character varying(255),
    CONSTRAINT snapshots_indicators_pkey PRIMARY KEY (id)
);


CREATE TABLE data_collect.snapshots_economics
(
    id bigserial,
    activity_main character varying(255),
    activity_secondary character varying(255),
    additional_properties jsonb,
    area_of_residence character varying(255),
    benefit_income integer,
    created_at timestamp without time zone,
    currency character varying(255),
    education_client_level character varying(255) ,
    education_level_attained character varying(255) ,
    education_person_most_studied character varying(255) ,
    employment_status_primary character varying(255) ,
    employment_status_secondary character varying(255) ,
    family_id bigint,
    household_monthly_income integer,
    household_monthly_outgoing integer,
    housing_situation character varying(255) ,
    net_suplus integer,
    other_income integer,
    pension_income integer,
    salary_income integer,
    savings_income integer,
    snapshot_indicator_id bigint,
    survey_definition_id bigint,
    CONSTRAINT snapshots_economics_pkey PRIMARY KEY (id),
    CONSTRAINT fk_survey_definition_id FOREIGN KEY (survey_definition_id)
        REFERENCES data_collect.surveys (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_snapshots_indicators_id FOREIGN KEY (snapshot_indicator_id)
        REFERENCES data_collect.snapshots_indicators (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE data_collect.snapshots_properties_attributes
(
    property_system_name character varying(255) NOT NULL,
    lang character varying(255),
    title character varying(255),
    property_schema_name character varying(255),
    stoplight_group character varying(255),
    stoplight_type character varying(255),
    CONSTRAINT snapshots_properties_attributes_pkey PRIMARY KEY (property_system_name)
);