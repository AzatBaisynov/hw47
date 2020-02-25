create table users(
id serial primary key,
login varchar(50) unique not null,
email varchar(100) unique not null,
password integer not null,
date_of_registration date not null
);

create table userlogs(
id serial primary key,
user_login varchar references users(login) not null,
lastlogin timestamp not null,
successibility boolean not null
);

drop table userlogs;
drop table users;

insert into users(login, email, password, date_of_registration)
values ('shabam', 'malam@bk.com', 123, current_date);

select users.login from users where login = 'vertus';
select users.login, users.password from users where password = 123;
insert into userlogs (user_id, lastlogin, successibility) values (1, current_timestamp, true);
