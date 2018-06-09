

ALTER TABLE coinbase.charge ADD COLUMN redirect_url VARCHAR;

ALTER TABLE coinbase.charge_timeline ADD COLUMN network VARCHAR;
ALTER TABLE coinbase.charge_timeline ADD COLUMN transaction_id VARCHAR;
