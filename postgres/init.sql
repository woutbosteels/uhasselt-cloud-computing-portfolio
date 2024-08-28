set search_path = public;

create sequence building_seq
    increment by 50;

alter sequence building_seq owner to postgres;

create sequence room_seq
    increment by 50;

alter sequence room_seq owner to postgres;

create sequence temperature_measurement_seq
    increment by 50;

alter sequence temperature_measurement_seq owner to postgres;

create table building
(
    id            bigint not null
        primary key,
    building_name varchar(255)
);

alter table building
    owner to postgres;

create table room
(
    id          bigint not null
        primary key,
    room_name   varchar(255),
    building_id bigint
        constraint fk4kmfw73x2vpfymk0ml875rh2q
            references building
);

alter table room
    owner to postgres;

create table temperature_measurement
(
    id      bigint not null
        primary key,
    celsius real   not null,
    room_id bigint
        constraint fkddauuymaim6o20amv6ve8lije
            references room
);

alter table temperature_measurement
    owner to postgres;



insert into public.building(id, building_name)
VALUES (1, 'home');

insert into public.building(id, building_name)
VALUES (2, 'dorm');

insert into public.building(id, building_name)
VALUES (3, 'library');

insert into public.room(id, room_name, building_id)
VALUES (1, 'bathroom', 1);

insert into public.room(id, room_name, building_id)
VALUES (2, 'livingroom', 1);

insert into public.room(id, room_name, building_id)
VALUES (3, 'bedroom', 1);

insert into public.room(id, room_name, building_id)
VALUES (4, 'dormroom', 2);

insert into public.room(id, room_name, building_id)
values (5, 'readingroom', 3);

insert into public.room(id, room_name, building_id)
values (6, 'basement', 3);