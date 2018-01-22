
--Change integer to decimal 
ALTER TABLE data_collect.snapshots_economics ALTER COLUMN benefit_income type DECIMAL(15,2);
ALTER TABLE data_collect.snapshots_economics ALTER COLUMN household_monthly_income type DECIMAL(15,2);
ALTER TABLE data_collect.snapshots_economics ALTER COLUMN household_monthly_outgoing type DECIMAL(15,2);
ALTER TABLE data_collect.snapshots_economics ALTER COLUMN net_suplus type DECIMAL(15,2);
ALTER TABLE data_collect.snapshots_economics ALTER COLUMN other_income type DECIMAL(15,2);
ALTER TABLE data_collect.snapshots_economics ALTER COLUMN pension_income type DECIMAL(15,2);
ALTER TABLE data_collect.snapshots_economics ALTER COLUMN salary_income type DECIMAL(15,2);
ALTER TABLE data_collect.snapshots_economics ALTER COLUMN savings_income type DECIMAL(15,2);


