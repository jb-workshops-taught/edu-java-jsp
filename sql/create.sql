use javaproject;

-- First we're going to create users, user_roles, and roles, in the format jetty 
-- wants them for BASIC auth.  We're just making the /admin/hello demo happy here.
create table users
(
  id integer auto_increment primary key,
  username varchar(100) not null unique key,
  passwd varchar(100) not null
);

create table user_roles
(
  user_id integer not null,
  role_id integer not null,
  unique key (user_id, role_id),
  index(user_id)
);

create table roles
(
  id integer auto_increment primary key,
  role varchar(100) not null unique key
);

-- The following lines insert the BASIC auth users and roles, so jetty can recognize these particular users.
insert into roles (role) values ("customer");
insert into roles (role) values ("admin");
insert into users (username, passwd) values ("admin1", "admin321");
insert into users (username, passwd) values ("cust1", "customer987");

insert into user_roles (user_id, role_id) select users.id, roles.id from users, roles where username="admin1" and roles.role="admin";
insert into user_roles (user_id, role_id) select users.id, roles.id from users, roles where username="cust1" and roles.role="customer";


-- Okay, now basic auth tables are done.  We want to set up LOLCat tables next.
create table lolcat
(
  id integer not null auto_increment primary key,
  title varchar(255),
  filename varchar(255),
  image_format varchar(6) not null,
  image_data longblob not null,
  created timestamp not null default now()
);




