ALTER TABLE survey.task_tracking
    RENAME TO intervention_tracking;

ALTER TABLE survey.intervention_tracking
    RENAME task_tracking_id TO intervention_tracking_id;

ALTER TABLE survey.intervention_tracking
    RENAME task_id TO intervention_id;