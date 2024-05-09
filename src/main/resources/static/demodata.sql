-- Inserting data into DEPT_ROLE
INSERT INTO DEPT_ROLE (role) VALUES
                                 ('Admin'),
                                 ('Manager'),
                                 ('Employee');

-- Inserting data into USER
INSERT INTO USER (real_name, username, password, role_id) VALUES
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

-- Inserting data into PROJECT (assuming manager_id is the user_id of the manager)
INSERT INTO PROJECT (name, description, user_id) VALUES
                                                     ('Project 1', 'Description of Project 1', 2),
                                                     ('Project 2', 'Description of Project 2', 2),
                                                     ('Project 3', 'Description of Project 3', 2);

-- Inserting data into SUBPROJECT (assuming project_id is the project_id of the corresponding project)
INSERT INTO SUBPROJECT (name, description, project_id) VALUES
                                                           ('Subproject 1', 'Description of Subproject 1', 1),
                                                           ('Subproject 2', 'Description of Subproject 2', 1),
                                                           ('Subproject 3', 'Description of Subproject 3', 2),
                                                           ('Subproject 4', 'Description of Subproject 4', 3);

-- Inserting data into TASK (assuming status_id is the status_id of the corresponding status and subproject_id is the subproject_id of the corresponding subproject)
INSERT INTO TASK (task_name, task_description, time, price, status_id, subproject_id) VALUES
                                                                                          ('Task 1', 'Description of Task 1', 10, 100.00, 1, 1),
                                                                                          ('Task 2', 'Description of Task 2', 15, 150.00, 1, 1),
                                                                                          ('Task 3', 'Description of Task 3', 20, 200.00, 2, 2),
                                                                                          ('Task 4', 'Description of Task 4', 25, 250.00, 2, 3),
                                                                                          ('Task 5', 'Description of Task 5', 30, 300.00, 3, 4);
-- Inserting data into EMPLOYEE_TASK (assuming user_id is the user_id of the corresponding employee and task_id is the task_id of the corresponding task)
INSERT INTO EMPLOYEE_TASK (user_id, task_id) VALUES
-- Task 1 assigned to employees 3, 4, and 5
(3, 1),
(4, 1),
(5, 1),
-- Task 2 assigned to employees 4, 5, and 6
(4, 2),
(5, 2),
(6, 2),
-- Task 3 assigned to employees 5, 6, and 7
(5, 3),
(6, 3),
(7, 3),
-- Task 4 assigned to employees 6, 7, and 8
(6, 4),
(7, 4),
(8, 4),
-- Task 5 assigned to employees 7, 8, and 9
(7, 5),
(8, 5),
(9, 5);