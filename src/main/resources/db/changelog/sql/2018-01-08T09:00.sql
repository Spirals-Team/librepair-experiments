--Create table to store terms and conditions, and privacy politics
CREATE TABLE security.TERMCONDPOL (
	id bigint PRIMARY KEY,
	html text NOT NULL,
	version character varying (30) NOT NULL,
	year integer NOT NULL,
	created_date date NOT NULL,
	type_cod character varying (10) NOT NULL
);

CREATE SEQUENCE security.TERMCONDPOL_id_seq;

--Add snapshot relationships
--Relationship snapshot with user
ALTER TABLE data_collect.snapshots_economics ADD COLUMN user_id bigint;

ALTER TABLE data_collect.snapshots_economics ADD CONSTRAINT fk_users_snapshots_economics FOREIGN KEY (user_id)
        REFERENCES security.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

--Relationships snapshot with temscondpol
ALTER TABLE data_collect.snapshots_economics ADD COLUMN term_cond_id bigint;

ALTER TABLE data_collect.snapshots_economics ADD CONSTRAINT fk_termcondpol_term_snapshots_economics FOREIGN KEY (term_cond_id)
        REFERENCES security.termcondpol (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

ALTER TABLE data_collect.snapshots_economics ADD COLUMN priv_pol_id bigint;

ALTER TABLE data_collect.snapshots_economics ADD CONSTRAINT fk_termcondpol_pol_snapshots_economics FOREIGN KEY (priv_pol_id)
        REFERENCES security.termcondpol (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
