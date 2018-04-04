create table CALL (
    ID int8 not null primary key,
    CREATED_DATE date not null,
    ANSWERED_DATE date,
    FINISHED_DATE date,
    EMPLOYEE_ID int8 not null references EMPLOYEE (ID)
);