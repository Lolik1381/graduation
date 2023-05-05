--liquibase formatted sql

--changeset Stanislav Lychagin:create_equipment
create table equipment(
    id varchar(36) primary key,
    name varchar(255) not null
);

comment on table equipment is 'Оборудование';
comment on column equipment.id is 'Идентификатор оборудования';
comment on column equipment.name is 'Наименование оборудования';

--changeset Stanislav Lychagin:add_column_equipment_id_for_task_template
alter table task_template add column equipment_id varchar(36) references equipment;

comment on column task_template.equipment_id is 'Идентификатор оборудования';