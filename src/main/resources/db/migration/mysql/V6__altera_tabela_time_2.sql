ALTER TABLE time ADD ativo BOOLEAN NOT NULL;

UPDATE time SET ativo = 1 WHERE id = 1;
UPDATE time SET ativo = 1  WHERE id = 2;
UPDATE time SET ativo = 1  WHERE id = 3;