-- noinspection SqlNoDataSourceInspectionForFile


DROP TABLE IF EXISTS coinbase.charge_payment CASCADE;

DROP TABLE IF EXISTS coinbase.charge_addresses CASCADE;
DROP TABLE IF EXISTS coinbase.charge_metadata CASCADE;
DROP TABLE IF EXISTS coinbase.charge_pricing CASCADE;
DROP TABLE IF EXISTS coinbase.charge_timeline CASCADE;
DROP TABLE IF EXISTS coinbase.charge CASCADE;

DROP TABLE IF EXISTS coinbase.payment_values CASCADE;
DROP TABLE IF EXISTS coinbase.payment CASCADE;

-- flyway creates the schema
-- DROP SCHEMA IF EXISTS coinbase CASCADE;
-- CREATE SCHEMA coinbase;


CREATE TABLE coinbase.charge (
    code          VARCHAR         PRIMARY KEY
  , confirmed_at  TIMESTAMP
  , created_at    TIMESTAMP
  , inserted_at   TIMESTAMP       NOT NULL    DEFAULT NOW()
  , description   VARCHAR
  , expires_at    TIMESTAMP
  , hosted_url    VARCHAR
  , logo_url      VARCHAR
  , name          VARCHAR
  , pricing_type  VARCHAR
  );


CREATE TABLE coinbase.charge_addresses (
    charge_code   VARCHAR         NOT NULL    REFERENCES coinbase.charge(code)
  , network       VARCHAR         NOT NULL
  , address       VARCHAR
  , PRIMARY KEY (charge_code, network)
  );


CREATE TABLE coinbase.charge_metadata (
    charge_code   VARCHAR         NOT NULL    REFERENCES coinbase.charge(code)
  , key           VARCHAR         NOT NULL
  , value         VARCHAR
  , PRIMARY KEY (charge_code, key)
  );


CREATE TABLE coinbase.charge_pricing (
    charge_code   VARCHAR         NOT NULL    REFERENCES coinbase.charge(code)
  , type          VARCHAR         NOT NULL
  , amount        numeric(24, 6)
  , currency      VARCHAR     
  , PRIMARY KEY (charge_code, type)
  );

CREATE TABLE coinbase.charge_timeline (
    charge_code   VARCHAR         NOT NULL    REFERENCES coinbase.charge(code)
  , time          TIMESTAMP       NOT NULL
  , status        VARCHAR         NOT NULL
  , context       VARCHAR     
  );

CREATE INDEX IF NOT EXISTS FK_CHARGE_TIMELINE_IDX ON coinbase.charge_timeline (charge_code);


CREATE TABLE coinbase.payment (
    network       VARCHAR         NOT NULL
  , transaction_id VARCHAR        NOT NULL
  , confirmations_accumulated BIGINT NOT NULL
  , confirmations_required    BIGINT NOT NULL
  , hash          VARCHAR     
  , height        BIGINT          NOT NULL
  , status        VARCHAR     
  , PRIMARY KEY (network, transaction_id)
  );

CREATE TABLE coinbase.payment_values (
    network         VARCHAR       NOT NULL
  , transaction_id  VARCHAR       NOT NULL
  , type            VARCHAR       NOT NULL
  , amount          DECIMAL(24, 6)
  , currency        VARCHAR
  , PRIMARY KEY (network, transaction_id, type)
--  , FOREIGN KEY (network, transaction_id) REFERENCES coinbase.payment(network, transaction_id)
  );

CREATE TABLE coinbase.charge_payment (
    charge_code     VARCHAR       NOT NULL    REFERENCES coinbase.charge(code)
  , network         VARCHAR       NOT NULL
  , transaction_id  VARCHAR       NOT NULL
  , PRIMARY KEY (charge_code, network, transaction_id)
--  , FOREIGN KEY (network, transaction_id) REFERENCES coinbase.payment(network, transaction_id)

  , CONSTRAINT unq_payment      UNIQUE (network, transaction_id)
  );

