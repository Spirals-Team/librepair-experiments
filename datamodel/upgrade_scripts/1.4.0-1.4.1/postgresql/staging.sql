--
-- upgrade STAGING datamodel van 1.4.0 naar 1.4.1 (PostgreSQL)
--
create index idx_bericht_soort on bericht (soort);
create index idx_bericht_status on bericht (status);

CREATE TABLE brmo_metadata (
        naam CHARACTER VARYING(255) NOT NULL,
        waarde CHARACTER VARYING(255),
        CONSTRAINT brmo_metadata_pk PRIMARY KEY (naam)
);
COMMENT ON TABLE brmo_metadata IS 'BRMO metadata en versie gegevens';

-- brmo versienummer
INSERT INTO brmo_metadata (naam, waarde) VALUES ('brmoversie','1.4.1');
