--liquibase formatted sql

--changeset Stanislav Lychagin:add_is_temporary_password
alter table system_user add column is_temporary_password boolean not null default true;