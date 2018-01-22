CREATE TABLE data_collect.snapshot_indicator_priorities (

	  snapshot_indicator_priorities_id  bigserial,
	  snapshot_indicator bigint,
	  indicator character varying(255),
	  reason character varying(500),
	  action character varying(500),
      estimated_date timestamp without time zone,
		
	  CONSTRAINT snapshot_indicator_priorities_pkey PRIMARY KEY (snapshot_indicator_priorities_id),
      CONSTRAINT fk_snapshots_indicators_snapshot_indicator_priorities FOREIGN KEY (snapshot_indicator)
        REFERENCES data_collect.snapshots_indicators (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

