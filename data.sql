
create table Buildings
(
    id      integer,
    unswid  varchar(16)  not null unique,
    name    varchar(128) not null,
    campus  ENUM ('K', 'P', 'Z', 'C', 'X'),
    gridref char(4),
    primary key (id)
);

create table Room_types
(
    id          integer,
    description varchar(64) not null,
    primary key (id)
);

create table Rooms
(
    id       integer,
    unswid   varchar(16) not null unique,
    rtype    integer,
    name     varchar(16) not null,
    longname varchar(128),
    building integer,
    capacity integer check (capacity >= 0),
    primary key (id),
    foreign key (rtype) references Room_types (id),
    foreign key (building) references Buildings (id)
);

-- Data initialization (Seeding)
insert into Buildings (id, unswid, name, campus, gridref)
values (1234, 'MB', 'Morven Brown Building', 'K', 'C20'),
       (5678, 'K17', 'CSE Building', 'X', 'K17'),
       (4321, 'EE', 'Electrical Engineering Building', 'Z', 'G17');

insert into Room_types (id, description)
values (8923, 'Lecture Theatre'),
       (3984, 'Tutorial Room'),
       (0394, 'Office');

insert into Rooms (id, unswid, rtype, name, longname, building, capacity)
values (8760, 'D99', 8923, 'AS01', 'Adams Smith', 4321, 120),
       (9769, 'F19', 0394, 'GH03', 'Grace Hopper', 4321, 5),
       (9873, 'M45', 3984, 'MH01', 'Margaret Hamilton', 1234, 60)


SELECT * FROM Rooms;
