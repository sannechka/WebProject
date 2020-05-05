create table hibernate_sequence
(
    next_val bigint
) engine = MyISAM;
insert into hibernate_sequence
values (1);
insert into hibernate_sequence
values (1);
insert into hibernate_sequence
values (1);

create table message
(
    id       integer       not null,
    filename varchar(255),
    text     varchar(2048) not null,
    user_id  bigint,
    room_id  bigint,
    time     date,
    primary key (id)
)
    engine = MyISAM;

create table room
(
    id   bigint       not null,
    name varchar(255) not null,
    primary key (id)
)
    engine = MyISAM;

create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
)
    engine = MyISAM;

create table user_rooms
(
    user_id bigint not null,
    room_id bigint not null,
    primary key (room_id, user_id)
)
    engine = MyISAM;

create table usr
(
    id       bigint       not null,
    active   bit,
    password varchar(255) not null,
    username varchar(255) not null,
    filename varchar(255),
    primary key (id)
) engine = MyISAM;


alter table message
    add constraint message_user_fk
        foreign key (user_id) references usr (id);

alter table message
    add constraint message_room_fk
        foreign key (room_id) references room (id);

alter table user_role
    add constraint user_role_user_fk
        foreign key (user_id) references usr (id);

alter table user_rooms
    add constraint user_rooms_room_fk
        foreign key (room_id) references room (id);

alter table user_rooms
    add constraint user_rooms_usr_fk
        foreign key (user_id) references usr (id)
