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
