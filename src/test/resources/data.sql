drop table if exists movie;
drop table if exists director;

create table director
(
    id   serial primary key,
    name varchar(100) not null
);

create table movie
(
    id          serial primary key,
    title       varchar(100)                     not null,
    year        integer                          not null,
    director_id integer references director (id) not null,
    length      time                             not null,
    rating      integer                          not null

);

insert into director
values (1, 'Test1'),
       (2, 'Test2');

insert into movie
values (1, 'Title1', 2019, 1, '02:19:20'::time, 9),
       (2, 'Title1', 2019, 1, '02:19:20'::time, 9)
