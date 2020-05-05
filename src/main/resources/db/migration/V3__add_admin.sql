insert into usr (id, username, password, active)
values (1, 'admin', '$2a$08$peZkcMKSzm7o0LWq.12yiuoXv91duiemotHJgbCxkMBgT8JwgZUXK', true);

insert into user_role (user_id, roles)
values ('1', 'USER'),
       ('1', 'ADMIN');

insert into user_rooms (user_id, room_id)
values ('1', '1');
