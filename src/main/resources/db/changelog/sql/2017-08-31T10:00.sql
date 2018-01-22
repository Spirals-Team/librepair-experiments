ALTER TABLE survey.task_tracking DROP COLUMN task_tracking_ancestor;

ALTER TABLE survey.task_tracking
    ADD COLUMN created_date timestamp without time zone;
