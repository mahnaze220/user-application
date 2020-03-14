drop table if exists USER cascade;
drop table if exists PHONE_NUMBER cascade;
drop table if exists EMAIL cascade;

create table USER(
  id int not null AUTO_INCREMENT,
  lastName varchar(20) not null,
  firstName varchar(30) not null,
  PRIMARY KEY (id)
);

create table PHONE_NUMBER(
  id int not null AUTO_INCREMENT,
  number varchar(20) not null,
  user int not null,
  PRIMARY KEY (id),
  FOREIGN KEY(user) references USER(id)
);

create table EMAIL(
  id int not null AUTO_INCREMENT,
  mail varchar(100) not null,
  user int not null,
  PRIMARY KEY (id),
  FOREIGN KEY(user) references USER(id)
);