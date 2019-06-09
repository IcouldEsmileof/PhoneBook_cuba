-- begin PHONEBOOK_PHONE_NUMBER
create table PHONEBOOK_PHONE_NUMBER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    OWNER_ID varchar(36) not null,
    NUMBER_ varchar(13) not null,
    TYPE_ varchar(50) not null,
    --
    primary key (ID)
)^
-- end PHONEBOOK_PHONE_NUMBER
-- begin PHONEBOOK_PERSON
create table PHONEBOOK_PERSON (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME longvarchar not null,
    --
    primary key (ID)
)^
-- end PHONEBOOK_PERSON
-- begin PHONEBOOK_CALLS
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
)^
-- end PHONEBOOK_CALLS
