drop database if exists railways;
create database railways;
use railways;

create table train (
                       id int auto_increment primary key,
                       number int,
                       seats smallint,
                       destinationStation_id int,
                       originStation_id int,
                       foreign key (destinationStation_id) references station (id),
                       foreign key (originStation_id) references station (id)
)  engine=InnoDB;

create table station (
                         id int auto_increment primary key,
                         station_name varchar(50) not null
)  engine=InnoDB;


create table passenger (
                           id int auto_increment primary key,
                           first_name varchar(50) not null,
                           last_name varchar(50),
                           birth_date date,
                           unique key (first_name, last_name, birth_date)
)  engine=InnoDB;

create table schedule (
                          id int auto_increment primary key,
                          train_id int,
                          station_id int,
                          arrive_time timestamp null,
                          departure_time timestamp null,
                          foreign key (train_id) references train (id),
                          foreign key (station_id) references station (id)
)  engine=InnoDB;

create table ticket (
                        id int auto_increment primary key,
                        passenger_id int,
                        train_id int,
                        foreign key (passenger_id) references passenger (id),
                        foreign key (train_id) references train (id)
)  engine=InnoDB;


create table user (
                      id int auto_increment primary key,
                      login varchar(30),
                      password varchar(30)
) engine=InnoDB;

drop table ticket;
delete from station;
delete from schedule;
delete from train;
delete from station;
drop table schedule;
SET GLOBAL time_zone = '+3:00';

SET time_zone = 'SYSTEM';
SELECT NOW(), FROM_UNIXTIME(946681261);
SET time_zone = '+03:00';
select current_timestamp();
SET time_zone = 'UTC';
ALTER TABLE schedule MODIFY arrive_time TIMESTAMP null;