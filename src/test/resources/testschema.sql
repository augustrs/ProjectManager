CREATE TABLE DEPT_ROLE
(
    role_id int auto_increment primary key,
    role    varchar(10) not null unique
);

CREATE TABLE USERS (
                       user_id   int auto_increment primary key,
                       real_name varchar(100) not null,
                       username  varchar(50)  not null unique,
                       password  varchar(60)  not null,
                       role_id   int          not null,
                       foreign key (role_id) references DEPT_ROLE (role_id)
);

CREATE TABLE PROJECT (
                         project_id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         description VARCHAR(255),
                         user_id INT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES USERS (user_id)
);

CREATE TABLE SUBPROJECT
(
    subproject_id int auto_increment primary key,
    name          varchar(50) not null,
    description   varchar(255),
    project_id    int         not null,
    foreign key (project_id) references PROJECT (project_id)
);

CREATE TABLE STATUS
(
    status_id int auto_increment primary key,
    status    varchar(20) not null unique
);

CREATE TABLE TASK
(
    task_id          int auto_increment primary key,
    task_name        varchar(30),
    task_description varchar(255),
    time             int not null,
    price            float not null,
    status_id        int not null,
    subproject_id    int not null,
    foreign key (status_id) references STATUS(status_id),
    foreign key (subproject_id) references SUBPROJECT(subproject_id)
);


CREATE TABLE EMPLOYEE_TASK
(
    user_id int,
    task_id int,
    primary key (user_id, task_id),
    foreign key (user_id) references USERS (user_id),
    foreign key (task_id) references TASK (task_id)
);

INSERT INTO DEPT_ROLE (role) VALUES
                                 ('Admin'),
                                 ('Manager'),
                                 ('Employee');

INSERT INTO USERS (real_name, username, password, role_id) VALUES
                                                              ('Admin', 'admin', '123', 1),
                                                              ('Manager', 'manager', '123', 2),
                                                              ('Employee', 'member', '123', 3),
                                                              ('Søren Bromose', 'emp1', '123', 3),
                                                              ('Joakim Slangerup', 'emp2', '123', 3),
                                                              ('Markus Hiten', 'emp3', '123', 3),
                                                              ('Oliver Hyllerup', 'emp4', '123', 3),
                                                              ('Númre sytten', 'emp5', '123', 3),
                                                              ('Ejner med J', 'emp6', '123', 3),
                                                              ('George Gærløs', 'emp7', '123', 3),
                                                              ('Anders And', 'emp8', '123', 3),
                                                              ('Juan Reinhold', 'emp9', '123', 3),
                                                              ('Sugar Gillete', 'emp10', '123', 3);

INSERT INTO STATUS (status) VALUES
                                ('Pending'),
                                ('In Progress'),
                                ('Completed');

INSERT INTO PROJECT (name, description, user_id) VALUES
                                                     ('Project 1', 'Description of Project 1', 2),
                                                     ('Project 2', 'Description of Project 2', 2),
                                                     ('Project 3', 'Description of Project 3', 2);

INSERT INTO SUBPROJECT (name, description, project_id) VALUES
                                                           ('Subproject 1', 'Description of Subproject 1', 1),
                                                           ('Subproject 2', 'Description of Subproject 2', 1),
                                                           ('Subproject 3', 'Description of Subproject 3', 2),
                                                           ('Subproject 4', 'Description of Subproject 4', 3);

INSERT INTO TASK (task_name, task_description, time, price, status_id, subproject_id) VALUES
                                                                                          ('Task 1', 'Description of Task 1', 10, 100.00, 1, 1),
                                                                                          ('Task 2', 'Description of Task 2', 15, 150.00, 1, 1),
                                                                                          ('Task 3', 'Description of Task 3', 20, 200.00, 2, 2),
                                                                                          ('Task 4', 'Description of Task 4', 25, 250.00, 2, 3),
                                                                                          ('Task 5', 'Description of Task 5', 30, 300.00, 3, 4);
INSERT INTO EMPLOYEE_TASK (user_id, task_id) VALUES
(3, 1),
(4, 1),
(5, 1),
(4, 2),
(5, 2),
(6, 2),
(5, 3),
(6, 3),
(7, 3),
(6, 4),
(7, 4),
(8, 4),
(7, 5),
(8, 5),
(9, 5);