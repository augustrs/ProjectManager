CREATE SCHEMA IF NOT EXISTS project_manager_schema;
USE project_manager_schema;

CREATE TABLE DEPT_ROLE (
                           role_id int auto_increment primary key,
                           role varchar(10) not null unique
);

CREATE TABLE USER (
                      user_id int auto_increment primary key,
                      real_name varchar(100) not null,
                      username varchar(50) not null unique,
                      password varchar(60) not null,
                      role_id int not null,
                      foreign key (role_id) references DEPT_ROLE(role_id)
);

CREATE TABLE MANAGER (
                         manager_id int auto_increment primary key,
                         user_id int not null,
                         foreign key (user_id) references USER(user_id)
);

CREATE TABLE PROJECT (
                         project_id int auto_increment primary key,
                         name varchar(50) not null,
                         description varchar(255),
                         manager_id int not null,
                         foreign key (manager_id) references MANAGER(manager_id)
);

CREATE TABLE TASK (
                      task_id int auto_increment primary key,
                      task_name varchar(30),
                      task_description varchar(255),
                      project_id int not null,
                      foreign key (project_id) references PROJECT(project_id)
);

CREATE TABLE EMPLOYEE (
                          employee_id int auto_increment primary key,
                          user_id int not null,
                          foreign key (user_id) references USER(user_id)
);

CREATE TABLE EMPLOYEE_TASK (
                               employee_id int,
                               task_id int,
                               primary key(employee_id, task_id),
                               foreign key(employee_id) references EMPLOYEE(employee_id),
                               foreign key(task_id) references TASK(task_id)
);