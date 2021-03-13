create table if not exists users(
username varchar(50) unique not null,
name varchar(200) not null,
age integer not null,
gender varchar(1),
town varchar(200),
password varchar(1000) not null,
balance decimal(10,4) not null,
constraint users_pk primary key(username),
CONSTRAINT users_balance_chk CHECK (balance>0)
);

create table if not exists charities(
id SERIAL,
name varchar(200) not null,
description varchar(600) not null,
required_amount decimal(10,4) default 0,
required_participants integer default 0,
donated_amount decimal(10,4) default 0,
participants integer default 0,
completed boolean default false,
thumbnail varchar(600) not null,
owner_username varchar(50) not null,
constraint charities_pk primary key (id),
constraint charities_owner_fk foreign key (owner_username) references users(username),
CONSTRAINT charities_required_chk CHECK (required_amount>0 or required_participants>0)
);



create table if not exists donations(
user_username varchar(50) not null,
charity_id integer not null,
place_date date not null,
amount  decimal(10,4),
constraint donations_pk primary key (user_username,charity_id,place_date),
constraint donations_user_fk foreign key (user_username) references users(username),
constraint donations_charity_fk foreign key (charity_id) references charities(id) on delete cascade,
CONSTRAINT donations_chk_amount CHECK (amount>0));



create table if not exists participation(
user_username varchar(50) not null,
charity_id integer not null,
sign_up_date date not null,
constraint participation_pk primary key (user_username,charity_id,sign_up_date),
constraint participation_user_fk foreign key (user_username) references users(username),
constraint participation_charity_fk foreign key (charity_id) references charities(id) on delete cascade);