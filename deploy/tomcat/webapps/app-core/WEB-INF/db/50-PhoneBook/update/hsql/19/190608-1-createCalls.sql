create table PHONEBOOK_CALLS (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PHONE_NUMBER_ID varchar(36) not null,
    MADE_ON timestamp not null,
    --
    primary key (ID)
);