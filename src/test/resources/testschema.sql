-- Drop the schema if it exists
DROP SCHEMA IF EXISTS project_manager_schema;

-- Create the schema
CREATE SCHEMA project_manager_schema;

-- Use the schema
USE project_manager_schema;

-- Create DEPT_ROLE table
CREATE TABLE DEPT_ROLE
(
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(10) NOT NULL UNIQUE
);

-- Create USERS table
CREATE TABLE USERS
(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    real_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES DEPT_ROLE (role_id)
);

-- Create PROJECT table
CREATE TABLE PROJECT
(
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (user_id)
);

-- Create SUBPROJECT table
CREATE TABLE SUBPROJECT
(
    subproject_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    project_id INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES PROJECT (project_id)
);

-- Create STATUS table
CREATE TABLE STATUS
(
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(20) NOT NULL UNIQUE
);

-- Create TASK table
CREATE TABLE TASK
(
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    task_name VARCHAR(30),
    task_description VARCHAR(255),
    time INT NOT NULL,
    price FLOAT NOT NULL,
    status_id INT NOT NULL,
    subproject_id INT NOT NULL,
    FOREIGN KEY (status_id) REFERENCES STATUS(status_id),
    FOREIGN KEY (subproject_id) REFERENCES SUBPROJECT(subproject_id)
);

-- Create EMPLOYEE_TASK table
CREATE TABLE EMPLOYEE_TASK
(
    user_id INT,
    task_id INT,
    PRIMARY KEY (user_id, task_id),
    FOREIGN KEY (user_id) REFERENCES USERS (user_id),
    FOREIGN KEY (task_id) REFERENCES TASK (task_id)
);

-- Inserting data into DEPT_ROLE
INSERT INTO DEPT_ROLE (role) VALUES
                                 ('Admin'),
                                 ('Manager'),
                                 ('Employee');

-- Inserting data into USERS
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

-- Inserting data into STATUS
INSERT INTO STATUS (status) VALUES
                                ('Pending'),
                                ('In Progress'),
                                ('Completed');

-- Inserting data into PROJECT
INSERT INTO PROJECT (name, description, user_id) VALUES
                                                     ('Project 1', 'Description of Project 1', 2),
                                                     ('Project 2', 'Description of Project 2', 2),
                                                     ('Project 3', 'Description of Project 3', 2);

-- Inserting data into SUBPROJECT
INSERT INTO SUBPROJECT (name, description, project_id) VALUES
                                                           ('Subproject 1', 'Description of Subproject 1', 1),
                                                           ('Subproject 2', 'Description of Subproject 2', 1),
                                                           ('Subproject 3', 'Description of Subproject 3', 2),
                                                           ('Subproject 4', 'Description of Subproject 4', 3);

-- Inserting data into TASK
INSERT INTO TASK (task_name, task_description, time, price, status_id, subproject_id) VALUES
                                                                                          ('Task 1', 'Description of Task 1', 10, 100.00, 1, 1),
                                                                                          ('Task 2', 'Description of Task 2', 15, 150.00, 1, 1),
                                                                                          ('Task 3', 'Description of Task 3', 20, 200.00, 2, 2),
                                                                                          ('Task 4', 'Description of Task 4', 25, 250.00, 2, 3),
                                                                                          ('Task 5', 'Description of Task 5', 30, 300.00, 3, 4);

-- Inserting data into EMPLOYEE_TASK
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
