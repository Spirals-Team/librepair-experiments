create table CALL (
    ID bigserial primary key,
    CREATED_DATE timestamp not null,
    ANSWERED_DATE timestamp,
    FINISHED_DATE timestamp,
    EMPLOYEE_ID int8 references EMPLOYEE (ID)
);