CREATE TABLE "ORGANIZATIONS" (
  "UUID" VARCHAR(40) NOT NULL PRIMARY KEY,
  "KEE" VARCHAR(32) NOT NULL,
  "NAME" VARCHAR(64) NOT NULL,
  "DESCRIPTION" VARCHAR(256),
  "URL" VARCHAR(256),
  "AVATAR_URL" VARCHAR(256),
  "GUARDED" BOOLEAN NOT NULL,
  "USER_ID" INTEGER,
  "DEFAULT_PERM_TEMPLATE_PROJECT" VARCHAR(40),
  "DEFAULT_PERM_TEMPLATE_VIEW" VARCHAR(40),
  "CREATED_AT" BIGINT NOT NULL,
  "UPDATED_AT" BIGINT NOT NULL
);
CREATE UNIQUE INDEX "PK_ORGANIZATIONS" ON "ORGANIZATIONS" ("UUID");
CREATE UNIQUE INDEX "ORGANIZATION_KEY" ON "ORGANIZATIONS" ("KEE");

CREATE TABLE "INTERNAL_PROPERTIES" (
  "KEE" VARCHAR(50) NOT NULL PRIMARY KEY,
  "IS_EMPTY" BOOLEAN NOT NULL,
  "TEXT_VALUE" VARCHAR(4000),
  "CLOB_VALUE" CLOB,
  "CREATED_AT" BIGINT
);
CREATE UNIQUE INDEX "UNIQ_INTERNAL_PROPERTIES" ON "INTERNAL_PROPERTIES" ("KEE");

CREATE TABLE "LOADED_TEMPLATES" (
  "ID" INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
  "KEE" VARCHAR(200),
  "TEMPLATE_TYPE" VARCHAR(64) NOT NULL
);
CREATE INDEX "IX_LOADED_TEMPLATES_TYPE" ON "LOADED_TEMPLATES" ("TEMPLATE_TYPE");
