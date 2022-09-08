drop database if exists superherodb;
create database superherodb;

use superherodb;

create table organization(
	id int primary key auto_increment,
    name varchar(30) not null,
    description varchar(30),
    address varchar(30) not null
);

create table superhero(
	id int primary key auto_increment,
    name varchar(30) not null,
    description varchar(30),
    power varchar(30) not null
);

create table superhero_org(
	superheroId int not null,
    orgId int not null,
    primary key (superheroId, orgId),
    foreign key (superheroId) references superhero(id),
    foreign key (orgId) references organization(id)
);

create table location(
	id int primary key auto_increment,
    name varchar(30) not null,
    description varchar(30),
    address varchar(30) not null,
    latitude float not null,
    longitude float not null
);

create table sighting(
	id int primary key auto_increment,
	superheroId int not null,
    locationId int not null,
    sightingDate date not null,
    foreign key (superheroId) references superhero(id),
    foreign key (locationId) references location(id)
);




drop database if exists superherodbtest;
create database superherodbtest;

use superherodbtest;

create table organization(
	id int primary key auto_increment,
    name varchar(30) not null,
    description varchar(30),
    address varchar(30) not null
);

create table superhero(
	id int primary key auto_increment,
    name varchar(30) not null,
    description varchar(30),
    power varchar(30) not null
);

create table superhero_org(
	superheroId int not null,
    orgId int not null,
    primary key (superheroId, orgId),
    foreign key (superheroId) references superhero(id),
    foreign key (orgId) references organization(id)
);

create table location(
	id int primary key auto_increment,
    name varchar(30) not null,
    description varchar(30),
    address varchar(30) not null,
    latitude float not null,
    longitude float not null
);

create table sighting(
	id int primary key auto_increment,
	superheroId int not null,
    locationId int not null,
    sightingDate date not null,
    foreign key (superheroId) references superhero(id),
    foreign key (locationId) references location(id)
);