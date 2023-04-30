--liquibase formatted sql

--changeset Stanislav Lychagin:insert_users
insert into role(id, name) values
    ('a7575574-5802-4048-ad1c-829a5bab4fb2', 'ADMIN'),
    ('61999dc0-db80-4992-bc9b-081d230d7010', 'USER');

insert into system_user(id, username, password, first_name, last_name, email) values
    ('b343e101-4ff1-4a5b-8be9-dad71f9c6e98', 'admin', '$2a$10$43Wx6glh7JlUAvuuCU9PSOvrYxduTtwMdR9Ni/8TCBNDCrR8HZP7C', 'Станислав', 'Лычагин', 'sa.lycahgin1@gmail.com'),
    ('b343e101-4ff1-4a5b-8be9-dad71f9c6e99', 'user1', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий1', 'Фамилия', 'sa.lycahgin1@gmail.com'),
    ('e325ec60-f4b3-40e4-964e-692acd56c5a3', 'user2', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий2', 'Фамилия', 'sa.lycahgin1@gmail.com'),
    ('04a0b138-52c2-4eb1-9574-471f94edcec6', 'user3', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий3', 'Фамилия', 'sa.lycahgin1@gmail.com'),
    ('b78bff88-cc6f-4134-ae00-3a50d8d0abfc', 'user4', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий4', 'Фамилия', 'sa.lycahgin1@gmail.com'),
    ('3245082d-275a-49b3-bae1-0efb2638c06a', 'user5', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий5', 'Фамилия', 'sa.lycahgin1@gmail.com'),
    ('7934e5a9-6033-490b-b53d-539b6cc3f070', 'user6', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий6', 'Фамилия', 'sa.lycahgin1@gmail.com'),
    ('b5306bec-3a95-4866-b06a-4a5d8321fa7b', 'user7', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий7', 'Фамилия', 'sa.lycahgin1@gmail.com'),
    ('d68da598-f035-4aa5-81a8-9afdd6f70c4d', 'user8', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий8', 'Фамилия', 'sa.lycahgin1@gmail.com'),
    ('83e2480a-7609-4e57-9388-b12080d2dcfd', 'user9', '$2a$10$aOKmig3woP/ZFkgIhZnOpeExLhis4Bf85cI6o9SwvZJkQ/n1Kaopu', 'Рабочий9', 'Фамилия', 'sa.lycahgin1@gmail.com');

insert into user_role_link(system_user_id, role_id) values
    ('b343e101-4ff1-4a5b-8be9-dad71f9c6e98', 'a7575574-5802-4048-ad1c-829a5bab4fb2'),
    ('b343e101-4ff1-4a5b-8be9-dad71f9c6e99', '61999dc0-db80-4992-bc9b-081d230d7010'),
    ('e325ec60-f4b3-40e4-964e-692acd56c5a3', '61999dc0-db80-4992-bc9b-081d230d7010'),
    ('04a0b138-52c2-4eb1-9574-471f94edcec6', '61999dc0-db80-4992-bc9b-081d230d7010'),
    ('b78bff88-cc6f-4134-ae00-3a50d8d0abfc', '61999dc0-db80-4992-bc9b-081d230d7010'),
    ('3245082d-275a-49b3-bae1-0efb2638c06a', '61999dc0-db80-4992-bc9b-081d230d7010'),
    ('7934e5a9-6033-490b-b53d-539b6cc3f070', '61999dc0-db80-4992-bc9b-081d230d7010'),
    ('b5306bec-3a95-4866-b06a-4a5d8321fa7b', '61999dc0-db80-4992-bc9b-081d230d7010'),
    ('d68da598-f035-4aa5-81a8-9afdd6f70c4d', '61999dc0-db80-4992-bc9b-081d230d7010'),
    ('83e2480a-7609-4e57-9388-b12080d2dcfd', '61999dc0-db80-4992-bc9b-081d230d7010');

insert into user_group(id, name) values
    ('1ab5f855-94bc-4654-b65f-c4f76cb753a6', 'Рабочие цеха №1'),
    ('7628d94f-53fe-41ae-ac0b-a10ed484ead5', 'Рабочие цеха №2'),
    ('b9fb897e-251a-45a8-8237-c1a4349ae5b5', 'Обслуживающий персонал цеха №1'),
    ('ab54f5f0-313a-4163-a02c-858c81e2748f', 'Обслуживающий персонал цеха №2');

insert into system_user_group_link(user_group_id, system_user_id) values
    ('1ab5f855-94bc-4654-b65f-c4f76cb753a6', 'b343e101-4ff1-4a5b-8be9-dad71f9c6e99'),
    ('1ab5f855-94bc-4654-b65f-c4f76cb753a6', 'e325ec60-f4b3-40e4-964e-692acd56c5a3'),
    ('1ab5f855-94bc-4654-b65f-c4f76cb753a6', '04a0b138-52c2-4eb1-9574-471f94edcec6'),
    ('1ab5f855-94bc-4654-b65f-c4f76cb753a6', 'b78bff88-cc6f-4134-ae00-3a50d8d0abfc'),
    ('7628d94f-53fe-41ae-ac0b-a10ed484ead5', '3245082d-275a-49b3-bae1-0efb2638c06a'),
    ('7628d94f-53fe-41ae-ac0b-a10ed484ead5', '7934e5a9-6033-490b-b53d-539b6cc3f070'),
    ('b9fb897e-251a-45a8-8237-c1a4349ae5b5', 'b5306bec-3a95-4866-b06a-4a5d8321fa7b'),
    ('b9fb897e-251a-45a8-8237-c1a4349ae5b5', 'd68da598-f035-4aa5-81a8-9afdd6f70c4d'),
    ('ab54f5f0-313a-4163-a02c-858c81e2748f', '83e2480a-7609-4e57-9388-b12080d2dcfd');