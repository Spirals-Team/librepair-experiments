CREATE DATABASE  IF NOT EXISTS OSM_CALENDAR;

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.PERSON (
    person_id BIGINT NOT NULL,
    emailaddress TEXT CHARACTER SET UTF8,
    firstname TEXT CHARACTER SET UTF8,
    lastname TEXT CHARACTER SET UTF8,
    PRIMARY KEY (person_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.SCHOOLSUBJECT (
    schoolsubject_id BIGINT NOT NULL,
    schoolsubject_name TEXT CHARACTER SET UTF8,
    subjecttype BIGINT NOT NULL,
    PRIMARY KEY (schoolsubject_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.TEACHER (
    teacher_id BIGINT NOT NULL,
    person_id BIGINT NOT NULL,
    PRIMARY KEY (teacher_id),
    FOREIGN KEY (person_id)
        REFERENCES OSM_CALENDAR.PERSON (person_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.TEACHERSUBJECTRELATION (
    teachersubjectrelation_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    schoolsubject_id BIGINT NOT NULL,
    PRIMARY KEY (teachersubjectrelation_id),
    FOREIGN KEY (teacher_id)
        REFERENCES OSM_CALENDAR.TEACHER (teacher_id),
    FOREIGN KEY (schoolsubject_id)
        REFERENCES OSM_CALENDAR.SCHOOLSUBJECT (schoolsubject_id)
);


CREATE TABLE IF NOT EXISTS OSM_CALENDAR.TASK (
    task_id BIGINT NOT NULL,
    name TEXT CHARACTER SET UTF8,
    description TEXT CHARACTER SET UTF8,
    deadline DATETIME NOT NULL,
    started BOOLEAN NOT NULL,
    reminder DATETIME NOT NULL,
    taskgiver BIGINT NOT NULL,
    taskexecutor BIGINT,
    PRIMARY KEY (task_id),
    FOREIGN KEY (taskgiver)
        REFERENCES OSM_CALENDAR.PERSON (person_id),
    FOREIGN KEY (taskexecutor)
        REFERENCES OSM_CALENDAR.PERSON (person_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.PROJECT (
    project_id BIGINT NOT NULL,
    name TEXT CHARACTER SET UTF8,
    description TEXT CHARACTER SET UTF8,
    responsible BIGINT NOT NULL,
    project_start DATETIME NOT NULL,
    project_end DATETIME NOT NULL,
    primary key(project_id),
    FOREIGN KEY (responsible)
        REFERENCES OSM_CALENDAR.PERSON (person_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.PROJECTTASK (
    projecttask_id BIGINT NOT NULL,
    project BIGINT NOT NULL,
    task BIGINT NOT NULL,
    PRIMARY KEY (projecttask_id),
    foreign key (project) references OSM_CALENDAR.PROJECT(project_id),
    foreign key (task) references OSM_CALENDAR.TASK(task_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.PROJECTTASKSELFRELATION (
    projecttaskselfrelation_id BIGINT NOT NULL,
    projecttask BIGINT NOT NULL,
    predecessor BIGINT NOT NULL,
    PRIMARY KEY (projecttaskselfrelation_id),
    FOREIGN KEY (projecttask)
        REFERENCES OSM_CALENDAR.PROJECTTASK (projecttask_id),
    FOREIGN KEY (predecessor)
        REFERENCES OSM_CALENDAR.PROJECTTASK (projecttask_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.APPOINTMENT (
    appointment_id BIGINT NOT NULL,
    appointment_start DATETIME NOT NULL,
    appointment_end DATETIME NOT NULL,
    appointment_duration BIGINT NOT NULL,
    appointment_subject TEXT CHARACTER SET UTF8,
    description TEXT CHARACTER SET UTF8,
    location TEXT CHARACTER SET UTF8,
    appointment_owner BIGINT NOT NULL,
    PRIMARY KEY (appointment_id),
    FOREIGN KEY (appointment_owner)
        REFERENCES OSM_CALENDAR.PERSON (person_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.MEETING (
    meeting_id BIGINT NOT NULL,
    meeting_start DATETIME NOT NULL,
    meeting_end DATETIME NOT NULL,
    duration BIGINT NOT NULL,
    meeting_subject TEXT CHARACTER SET UTF8,
    location TEXT CHARACTER SET UTF8,
    description TEXT CHARACTER SET UTF8,
    organiser BIGINT NOT NULL,
    PRIMARY KEY (meeting_id),
    FOREIGN KEY (organiser)
        REFERENCES OSM_CALENDAR.PERSON (person_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.MEETING_INVITEDPERSON_RELATION (
    meeting_invitedperson_relation_id BIGINT NOT NULL,
    meeting_id BIGINT NOT NULL,
    person_id BIGINT NOT NULL,
    PRIMARY KEY (meeting_invitedperson_relation_id),
    FOREIGN KEY (person_id)
        REFERENCES OSM_CALENDAR.PERSON (person_id),
    FOREIGN KEY (meeting_id)
        REFERENCES OSM_CALENDAR.MEETING (meeting_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.schoolclass (
    schoolclass_id BIGINT NOT NULL,
    schoolclassname TEXT CHARACTER SET UTF8,
    schoolclassteacher BIGINT NOT NULL,
    PRIMARY KEY (schoolclass_id),
    FOREIGN KEY (schoolclassteacher)
        REFERENCES OSM_CALENDAR.TEACHER (teacher_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.TIMETABLE (
    timetable_id BIGINT NOT NULL,
    timetable_name TEXT CHARACTER SET UTF8,
    validfrom DATETIME NOT NULL,
    validto DATETIME NOT NULL,
    PRIMARY KEY (timetable_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.TIMETABLEENTRY (
    timetableentry_id BIGINT NOT NULL,
    schoolsubject bigint not null,
    schoolclass BIGINT NOT NULL,
    timetableentrysubject TEXT CHARACTER SET UTF8,
    teacher BIGINT NOT NULL,
    room TEXT CHARACTER SET UTF8,
    weekday INT NOT NULL,
    schoolhour INT NOT NULL,
    PRIMARY KEY (timetableentry_id),
    FOREIGN KEY (schoolclass)
        REFERENCES OSM_CALENDAR.SCHOOLCLASS (schoolclass_id),
    FOREIGN KEY (teacher)
        REFERENCES OSM_CALENDAR.TEACHER (teacher_id),
	foreign key (schoolsubject) references OSM_CALENDAR.SCHOOLSUBJECT (schoolsubject_id)
);


CREATE TABLE IF NOT EXISTS OSM_CALENDAR.CLASSTIMETABLE (
    classtimetable_id BIGINT NOT NULL,
    schoolclass_id BIGINT NOT NULL,
    PRIMARY KEY (classtimetable_id),
    FOREIGN KEY (schoolclass_id)
        REFERENCES OSM_CALENDAR.SCHOOLCLASS (schoolclass_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.TEACHERTIMETABLE (
    teachertimetable_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    PRIMARY KEY (teachertimetable_id),
    FOREIGN KEY (teacher_id)
        REFERENCES OSM_CALENDAR.TEACHER (teacher_id)
);

CREATE TABLE IF NOT EXISTS OSM_CALENDAR.TIMETABLERELATION (
    timetablerelation_id BIGINT NOT NULL,
    timetableentry_id BIGINT NOT NULL,
    timetable_id BIGINT NOT NULL,
    teachertimetable_id BIGINT NOT NULL,
    classtimetable_id BIGINT NOT NULL,
    PRIMARY KEY (timetablerelation_id),
    FOREIGN KEY (timetableentry_id)
        REFERENCES OSM_CALENDAR.TIMETABLEENTRY (timetableentry_id),
    FOREIGN KEY (timetable_id)
        REFERENCES OSM_CALENDAR.TIMETABLE (timetable_id),
    FOREIGN KEY (teachertimetable_id)
        REFERENCES OSM_CALENDAR.TEACHERTIMETABLE (teachertimetable_id),
    FOREIGN KEY (classtimetable_id)
        REFERENCES OSM_CALENDAR.CLASSTIMETABLE (classtimetable_id)
);

create table IF not exists OSM_CALENDAR.TODO (
	todo_id BIGINT NOT NULL,
	name  TEXT CHARACTER SET UTF8,
	duedate DATETIME,
	description TEXT CHARACTER SET UTF8,
	reminder DATETIME,
	todo_owner BIGINT NOT NULL,
    primary key(todo_id),
    FOREIGN KEY (todo_owner)
        REFERENCES OSM_CALENDAR.PERSON (person_id)
);


create table if not exists OSM_CALENDAR.STATE (
    state_id  BIGINT NOT NULL,
    name TEXT CHARACTER SET UTF8,
    isoCode TEXT CHARACTER SET UTF8,
    isoCodeShort TEXT CHARACTER SET UTF8
     primary key (state_id)
);

create table if not exists OSM_CALENDAR.publicholiday(
    publicholiday_id  BIGINT NOT NULL,
    day DATE NOT NULL,
    name TEXT CHARACTER SET UTF8,
    state_id BIGINT NOT NULL,
    primary key (publicholiday_id),
    FOREIGN KEY (state_id)
        REFERENCES OSM_CALENDAR.STATE (state_id)
);

create table if not exists OSM_CALENDAR.vacation(
    vacation_id  BIGINT NOT NULL,
    vacation_start DATE NOT NULL,
    vacation_end DATE NOT NULL,
    vacation_description TEXT CHARACTER SET UTF8,
    vacation_state  BIGINT NOT NULL,
    slug TEXT CHARACTER SET UTF8,
    primary key (vacation_id),
    FOREIGN KEY (vacation_state)
          REFERENCES OSM_CALENDAR.STATE (state_id)
);

create table if not exists OSM_CALENDAR.lendobject(
    lendobject_id BIGINT NOT NULL,
    name TEXT CHARACTER SET UTF8,
    owner BIGINT NOT NULL,
    lendbywhom BIGINT NOT NULL,
    whenlend DATE NOT NULL,
    duetoreturn DATE NOT NULL,
    returned BOOLEAN NOT NULL,
    primary key (lendobject_id),
    FOREIGN KEY (owner)
          REFERENCES OSM_CALENDAR.person (person_id),
    FOREIGN KEY (lendbywhom)
          REFERENCES OSM_CALENDAR.person (person_id),
);

