ALTER TABLE survey.task
    RENAME TO intervention;

ALTER TABLE survey.intervention
    RENAME task_id TO intervention_id;