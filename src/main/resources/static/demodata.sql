-- Inserting roles into DEPT_ROLE table
INSERT INTO DEPT_ROLE (role)
VALUES (1, 'ADMIN'),
       (2, 'MANAGER'),
       (3, 'EMPLOYEE');
-- Inserting users into USER table
INSERT INTO USER (real_name, username, password, role_id)
VALUES ('John Doe', 'admin', '123', 1),
       ('Jane Smith', 'employee', '123', 3),
       ('Michael Johnson', 'manager', '123', 2);

-- Inserting projects into PROJECT table
INSERT INTO PROJECT (name, description, user_id)
VALUES ('Project A', 'This is project A', 1),
       ('Project B', 'Description for project B', 2),
       ('Project C', 'Project C details', 3);

-- Inserting subprojects into SUBPROJECT table
INSERT INTO SUBPROJECT (name, description, project_id)
VALUES ('Subproject 1', 'Description for subproject 1', 1),
       ('Subproject 2', 'Details of subproject 2', 2),
       ('Subproject 3', 'Subproject details', 3);
-- Inserting tasks into TASK table
INSERT INTO TASK (task_name, task_description, project_id)
VALUES ('Task 1', 'Description for Task 1', 1),
       ('Task 2', 'Description for Task 2', 1),
       ('Task 3', 'Description for Task 3', 2),
       ('Task 4', 'Description for Task 4', 3),
       ('Task 5', 'Description for Task 5', 3);

-- Inserting employee tasks into EMPLOYEE_TASK table
INSERT INTO EMPLOYEE_TASK (user_id, task_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (3, 5);
