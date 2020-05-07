drop database if exists railways;
create database railways;
use railways;

create table train
(
    id                    int auto_increment primary key,
    number                int,
    seats                 smallint,
    destinationStation_id int,
    originStation_id      int,
    foreign key (destinationStation_id) references station (id),
    foreign key (originStation_id) references station (id)
) engine = InnoDB;

create table station
(
    id   int auto_increment primary key,
    name varchar(50) not null
) engine = InnoDB;


create table passenger
(
    id         int auto_increment primary key,
    first_name varchar(50) not null,
    last_name  varchar(50),
    birth_date date,
    unique key (first_name, last_name, birth_date)
) engine = InnoDB;

create table schedule
(
    id             int auto_increment primary key,
    train_id       int,
    station_id     int,
    arrive_time    timestamp null,
    departure_time timestamp null,
    foreign key (train_id) references train (id),
    foreign key (station_id) references station (id)
) engine = InnoDB;

create table ticket
(
    id           int auto_increment primary key,
    passenger_id int,
    train_id     int,
    foreign key (passenger_id) references passenger (id),
    foreign key (train_id) references train (id)
) engine = InnoDB;


create table user
(
    id       int auto_increment primary key,
    login    varchar(30),
    password varchar(30)
) engine = InnoDB;

drop table ticket;
delete
from station;
delete
from schedule;
delete
from train;
delete
from station;
delete
from ticket;
drop table schedule;
drop table passenger;
delete
from passenger;
SET GLOBAL time_zone = '+3:00';

alter table user rename to admin;

insert into admin value (1, 'admin', '000');

SET time_zone = 'SYSTEM';
SELECT NOW(), FROM_UNIXTIME(946681261);
SET time_zone = '+03:00';
select current_timestamp();
SET time_zone = 'UTC';
ALTER TABLE schedule
    MODIFY arrive_time TIMESTAMP null;
ALTER TABLE schedule
    drop column arrive_time;

CREATE TABLE user_roles
(
    user_role_id int(11)     NOT NULL AUTO_INCREMENT,
    username     varchar(45) NOT NULL,
    role         varchar(45) NOT NULL,
    PRIMARY KEY (user_role_id),
    UNIQUE KEY uni_username_role (role, username),
    KEY fk_username_idx (username),
    CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE users
(
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    enabled  TINYINT     NOT NULL DEFAULT 1,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles
(
    user_role_id int(11)     NOT NULL AUTO_INCREMENT,
    username     varchar(45) NOT NULL,
    role         varchar(45) NOT NULL,
    PRIMARY KEY (user_role_id),
    UNIQUE KEY uni_username_role (role, username),
    KEY fk_username_idx (username),
    CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);

insert into users (username, password, enabled)
VALUES ('employee', '000', 1);
delete
from railways.user_roles;
INSERT INTO user_roles (username, role)
VALUES ('employee', 'ROLE_EMPLOYEE');
delete
from train
where train.number = 1;
delete
from schedule
where id = 4;
delete from railways.user_roles where railways.user_roles.user_role_id = 2;
alter table railways.user_roles change username = ''