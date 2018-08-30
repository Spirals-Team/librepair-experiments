CREATE TABLE company (
    id   INTEGER NOT NULL,
    name CHARACTER VARYING,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person (
    id         INTEGER NOT NULL,
    name       CHARACTER VARYING,
    company_id INTEGER,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

-- 1) Retrieve in a single query:
-- - names of all persons that are NOT in the company with id = 5
-- - company name for each person
SELECT
    c.name,
    p.name
FROM company c
    JOIN person p ON (c.id = p.company_id AND c.id <> 5);

-- 2) Select the name of the company
-- with the maximum number of persons + number of persons in this company
WITH company_person AS (
    SELECT
        c.name              AS name,
        count(p.company_id) AS employees
    FROM company c
        JOIN person p ON c.id = p.company_id
    GROUP BY c.name)
SELECT
    cp.name,
    cp.employees
FROM company_person cp
WHERE cp.employees = (SELECT max(cp.employees)
                      FROM company_person cp);
