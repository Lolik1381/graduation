--liquibase formatted sql

--changeset Stanislav Lychagin:create_schedlock
create table shedlock (
    name varchar(64),
    lock_until timestamp(3) NULL,
    locked_at timestamp(3) NULL,
    locked_by varchar(255),
    primary key (name)
);