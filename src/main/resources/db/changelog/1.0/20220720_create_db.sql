--liquibase formatted sql

--changeset Stanislav Lychagin:create_table_system_user_group
create table user_group(
    id varchar(36) primary key,
    name varchar(255) not null unique
);

comment on table user_group is 'Группы пользователей';
comment on column user_group.id is 'Идентификатор группы';
comment on column user_group.name is 'Наименование группы';

--changeset Stanislav Lychagin:create_table_system_role
create table role(
    id varchar(36) primary key,
    name varchar(255) not null unique
);

comment on table role is 'Роль пользователя';
comment on column role.id is 'Идентификатор роли';
comment on column role.name is 'Наименование роли';

--changeset Stanislav Lychagin:create_table_system_user
create table system_user(
    id varchar(36) primary key,
    username varchar(255) not null unique,
    password text not null,
    first_name varchar(1000) not null,
    last_name varchar(1000) not null,
    email varchar(320) not null
);

comment on table system_user is 'Пользователь';
comment on column system_user.id is 'Идентификатор пользователя';
comment on column system_user.username is 'Логин пользователя';
comment on column system_user.password is 'Пароль пользователя';
comment on column system_user.first_name is 'Имя пользователя';
comment on column system_user.last_name is 'Фамилия пользователя';
comment on column system_user.email is 'Email пользователя';

--changeset Stanislav Lychagin:create_table_system_user_group_link
create table system_user_group_link(
    user_group_id varchar(36) references user_group,
    system_user_id varchar(36) references system_user,
    primary key (user_group_id, system_user_id)
);

comment on table system_user_group_link is 'Связочная таблица пользователей и групп';
comment on column system_user_group_link.user_group_id is 'Идентификатор группы';
comment on column system_user_group_link.system_user_id is 'Идентификатор пользователя';

--changeset Stanislav Lychagin:create_table_user_role_id
create table user_role_link(
    system_user_id varchar(36) references system_user,
    role_id varchar(36) references role,
    primary key (system_user_id, role_id)
);

comment on table user_role_link is 'Связочная таблица пользователей и ролей';
comment on column user_role_link.system_user_id is 'Идентификатор пользователя';
comment on column user_role_link.role_id is 'Идентификатор роли';

--changeset Stanislav Lychagin:create_table_task_template
create table task_template(
    id varchar(36) primary key,
    header varchar(255) not null,
    description varchar(1024) not null,
    status varchar(255) not null
);

comment on table task_template is 'Шаблон задания';
comment on column task_template.id is 'Идентификатор шаблона задания';
comment on column task_template.header is 'Заголовок задания';
comment on column task_template.description is 'Описание задания';
comment on column task_template.status is 'Статус задания';

--changeset Stanislav Lychagin:create_task_template_check
create table task_template_check(
    id varchar(36) primary key,
    task_template_id varchar(36) references task_template not null,
    name varchar(255) not null,
    description varchar(1024),
    required_photo bool default false,
    required_comment bool default false,
    required_control_value bool default false,
    check_order integer not null,
    control_value_type varchar(255)
);

comment on table task_template_check is 'Шаблон проверки';
comment on column task_template_check.task_template_id is 'Идентификатор шаблона задания';
comment on column task_template_check.name is 'Наименование проверки';
comment on column task_template_check.description is 'Описание проверки';
comment on column task_template_check.required_photo is 'Обязательность прикрепление фотографии';
comment on column task_template_check.required_comment is 'Обязательность написание комеентария';
comment on column task_template_check.required_control_value is 'Обязательность добавления контрольных значений';
comment on column task_template_check.control_value_type is 'Тип контрольного значения';

--changeset Stanislav Lychagin:create_table_task
create table task(
    id varchar(36) primary key,
    task_template_id varchar(36) references task_template not null,
    status varchar(36) not null,
    system_user_id varchar(36) references system_user,
    group_id varchar(36) references user_group,
    expire_date timestamp with time zone not null,
    create_at timestamp with time zone not null,
    update_at timestamp with time zone not null
);

comment on table task is 'Задание';
comment on column task.task_template_id is 'Идентификатор шаблона задания';
comment on column task.status is 'Статус задания';
comment on column task.system_user_id is 'Идентификатор пользователя в системе';
comment on column task.group_id is 'Идентификатор группы';
comment on column task.expire_date is 'Дата/Время истечения задания';
comment on column task.create_at is 'Дата/Время создания задания';
comment on column task.update_at is 'Дата/Время последнего обновления задания';

--changeset Stanislav Lychagin:create_task_check
create table task_check(
    id varchar(36) primary key,
    task_id varchar(36) references task not null,
    task_template_check_id varchar(36) references task_template_check not null,
    comment text,
    control_value varchar(255),
    status varchar(255),
    create_at timestamp with time zone not null,
    update_at timestamp with time zone not null,
    update_by varchar(255)
);

comment on table task_check is 'Задание';
comment on column task_check.task_id is 'Идентификатор задания';
comment on column task_check.task_template_check_id is 'Идентификатор шаблона проверки';
comment on column task_check.comment is 'Комментарий';
comment on column task_check.control_value is 'Контрольное значение';
comment on column task_check.status is 'Статус';
comment on column task_check.create_at is 'Дата/Время создания проверки';
comment on column task_check.update_at is 'Дата/Время последнего обновления проверки';
comment on column task_check.update_by is 'ФИО пользователя';

--changeset Stanislav Lychagin:create_unique_index_uq_task_id_task_template_check_id
create unique index uq_task_id_task_template_check_id on task_check(task_id, task_template_check_id) where status = 'ACTIVE';

--changeset Stanislav Lychagin:create_task_scheduler
create table task_scheduler(
    id varchar(36) primary key,
    task_template_id varchar(36) references task_template not null,
    type varchar(255) not null,
    cron varchar(36),
    trigger_date timestamp with time zone,
    status varchar(36) not null,
    user_id varchar(36) references system_user,
    group_id varchar(36) references user_group,
    expire_delay bigint not null
);

comment on table task_scheduler is 'Планировщик заданий';
comment on column task_scheduler.id is 'Идентификатор планировщика задания';
comment on column task_scheduler.task_template_id is 'Идентификатор шаблона задания';
comment on column task_scheduler.type is 'Тип планировщика задания';
comment on column task_scheduler.cron is 'Переодичность выполнения в формате cron';
comment on column task_scheduler.trigger_date is 'Дата/Время выполнения';
comment on column task_scheduler.status is 'Статус планировщика задания';
comment on column task_scheduler.user_id is 'Идентификатор пользователя';
comment on column task_scheduler.group_id is 'Идентификатор группы';
comment on column task_scheduler.expire_delay is 'Время в миллисекундах через которое задание просрочится';

--changeset Stanislav Lychagin:create_file
create table file(
    id varchar(36) primary key,
    name varchar(255),
    extension varchar(255)
);

comment on table file is 'Файл';
comment on column file.id is 'Идентификатор файла в хранилище';
comment on column file.name is 'Наименование файла';
comment on column file.extension is 'Расширение файла';

--changeset Stanislav Lychagin:create_task_check_file_link
create table task_check_file_link(
    task_check_id varchar(36) references task_check,
    file_id varchar(36) references file,
    primary key (task_check_id, file_id)
);

comment on table task_check_file_link is 'Связочная таблица контрольных проверок и файлов';
comment on column task_check_file_link.task_check_id is 'Идентификатор контрольной проверки';
comment on column task_check_file_link.file_id is 'Идентификатор файла';