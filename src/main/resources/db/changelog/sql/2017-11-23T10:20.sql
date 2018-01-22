--This script create column for store family ubication

ALTER TABLE data_collect.snapshots_economics
    ADD COLUMN family_ubication character varying (200);
