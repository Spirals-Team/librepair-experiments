# Introduction
Liquibase is a framework written in Java used to manage and apply your sql files. Their website sports the tagline “source control for your database”, which might be a bit misleading. Liquibase is not a version control system like Git or Subversion. In fact it is meant to be used in tandem with a version control system. When you use Liquibase you will have a project, just like any old Java project, that contains your sql files. When you run this project Liquibase will install your changes to the database. You can also embed Liquibase (and your sql files) into an existing project, allowing your application to manage its own database. Liquibase is meant to bring the management and deployment of your sql files into the familiar developer realms of IDE’s, version control, and continuous integration.

# Commands
## Create a database
The initial change log files for the database are already created under the name `db-changelog-master.xml` and `db-changelog-evolution.xml` in the `src/main/resources/db/changelog/` directory. So its not neccesary to generate this files again. All our database changes will take place in the `db-changelog-evolution.xml`.

If you are interested in know how to generate a change log file you can follow this [link](http://www.liquibase.org/documentation/command_line.html).

## Update and existing database
Changes are made using "change sets". Each changeSet tag must be uniquely identified by the combination of the “id” and "author" tag. If the combination of "id" and "author" its not unique, Liquibase will skip our changeset unless we explicitly tell him not to. 

### Liquibase Formatted SQL

An easy way to add  a change set is to write the changes to the database as we normally do, using SQL, and adding a couple extra lines to make it a Liquibase-formatted SQL file. For example:

```
--liquibase formatted sql
 
CREATE TABLE SampleTable
(
  id uuid NOT NULL,
  someNumber INTEGER,
  CONSTRAINT sampletable_pkey PRIMARY KEY (id)
);
ALTER TABLE SampleTable
  OWNER TO sample;
 
--rollback DROP TABLE SampleTable;
```

The lines starting with "-" is what makes the file a Liquibase-formatted SQL-file. Only a few additional lines are needed.

```
--liquibase formatted sql
```

The above statement simply introduces the file as a Liquibase-formatted SQL-file.

One really nice feature is the possibility to define rollback statements. If used properly it is possible to return to an earlier state of the database this way. It should be noted that a Liquibase-formatted SQL-file can contain several SQL-statements and thus several rollback statements.


Now we add the changeset in the `db-changelog-evolution.xml`. For example:
```
<!--
This script create the first schemas and tables for the project.
__>
<changeSet id="2017-08-22T12:09" author="nvaldez">
     <sqlFile path="sql/2017-08-22T12:09.sql" relativeChangelogFile="true"/>
</changeSet>
```
It's important that add a short description for each changeset, explaining what the script will actually do. That way we can keep a record of the evolution of the database.

We will use the following convention for the changeset id-author combination:

``
id="<yyyy-mm-ddThh:mm>" author="myUserName"
``
The ID is based on the [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601).

All the `.sql` files must be in `src/main/resources/db/changelog/sql/` and the name of the file should be the same as the "id".

### Using Liquibase changeset format
Another way to introduce a change set is using Liquibase syntax. For example
```
<changeSet id="1" author="bob">
     <comment>A sample change log</comment>
     <createTable/>
</changeSet>

<changeSet id="2" author="bob" runAlways="true">
     <alterTable/>
</changeSet>
```

Where "id" and "author" must follow the same conventions previously discussed.

`runAlways="true"` executes the change set on every run, even if it has been run before.

To learn more about how to write change sets using the Liquibase format you can visit [this link](http://www.liquibase.org/documentation/changeset.html)







