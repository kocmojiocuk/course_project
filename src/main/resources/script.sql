
create table accounts
(
	id int(11) unsigned auto_increment
		primary key,
	name varchar(20) not null,
	surname varchar(20) not null,
	login varchar(45) not null,
	password varchar(45) not null,
	role varchar(45) not null,
	mail varchar(45) not null
);

create table goals
(
	id int auto_increment
		primary key,
	goal varchar(500) not null
);

create table goals_statistics
(
	id_goals int not null,
	id_statistics int not null,
	primary key (id_goals, id_statistics)
);

create table statistics
(
	id int auto_increment
		primary key,
	operation varchar(45) not null,
	content varchar(500) not null,
	quantity int not null,
	cash int not null,
	data varchar(45) not null
);

