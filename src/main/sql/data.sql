DROP SCHEMA IF EXISTS task3 CASCADE;
CREATE SCHEMA task3;

SHOW search_path ;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP TABLE IF EXISTS task3.Role CASCADE;
DROP TABLE IF EXISTS task3.Employee CASCADE;
DROP TABLE IF EXISTS task3.Project CASCADE;
DROP TABLE IF EXISTS task3.EmployeeProject CASCADE;

-- Создание таблицы Department
CREATE TABLE task3.Role
(
    id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50)
);

-- Создание таблицы Employee
CREATE TABLE task3.Employee
(
    id            INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR(50),
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES task3.Role (id) ON DELETE SET NULL
);

-- Создание таблицы Project
CREATE TABLE task3.Project
(
    id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

-- Создание таблицы-связи EmployeeProject
CREATE TABLE task3.EmployeeProject
(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    employee_id INT,
    project_id  INT,
    FOREIGN KEY (employee_id) REFERENCES task3.Employee (id) ON DELETE CASCADE ,
    FOREIGN KEY (project_id) REFERENCES task3.Project (id) ON DELETE CASCADE
);


-- Вставка данных
INSERT INTO task3.Role (name)
VALUES ('Администратор'); -- 1
INSERT INTO task3.Role (name)
VALUES ('Технический директор'); -- 2
INSERT INTO task3.Role (name)
VALUES ('Программист Java'); -- 3
INSERT INTO task3.Role (name)
VALUES ('Программист DB'); -- 4
INSERT INTO task3.Role (name)
VALUES ('Программист QA'); -- 5
INSERT INTO task3.Role (name)
VALUES ('HR'); -- 6

INSERT INTO task3.Employee (name, role_id)
VALUES ('Сергей Павлович Королёв', 1); -- 1
INSERT INTO task3.Employee (name, role_id)
VALUES ('Юрий Гагарин', 2); -- 2
INSERT INTO task3.Employee (name, role_id)
VALUES ('Алексей Леонов', 3); -- 3
INSERT INTO task3.Employee (name, role_id)
VALUES ('Андриян Николаев', 3); -- 4
INSERT INTO task3.Employee (name, role_id)
VALUES ('Валерий Быковский', 3); -- 5
INSERT INTO task3.Employee (name, role_id)
VALUES ('Павел Попович', 3); -- 6
INSERT INTO task3.Employee (name, role_id)
VALUES ('Павел Беляев', 4); -- 7
INSERT INTO task3.Employee (name, role_id)
VALUES ('Владимир Комаров', 4); -- 8
INSERT INTO task3.Employee (name, role_id)
VALUES ('Герман Титов', 4); -- 9
INSERT INTO task3.Employee (name, role_id)
VALUES ('Валентина Терешкова', 5); -- 10
INSERT INTO task3.Employee (name, role_id)
VALUES ('Елена Кондакова', 5); -- 11
INSERT INTO task3.Employee (name, role_id)
VALUES ('Светлана Савицкая', 6); -- 12


INSERT INTO task3.Project (name)
VALUES ('Project A'); -- 1
INSERT INTO task3.Project (name)
VALUES ('Project B'); -- 2
INSERT INTO task3.Project (name)
VALUES ('Project C'); -- 3
INSERT INTO task3.Project (name)
VALUES ('NO Project'); -- 4

INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (1, 1);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (1, 2);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (1, 3);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (2, 1);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (2, 2);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (2, 3);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (3, 1);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (4, 2);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (5, 3);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (6, 1);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (7, 1);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (8, 2);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (9, 3);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (10, 1);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (10, 2);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (11, 1);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (11, 3);
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES (12, 4);


SELECT *
FROM task3.role;

SELECT *
FROM task3.employee;

SELECT *
FROM task3.project;

SELECT e.*, d.name
FROM task3.employee e
         JOIN task3.role d on d.id = e.role_id;

SELECT e.name, p.name
FROM task3.employee e
         JOIN task3.employeeproject e2 on e.id = e2.employee_id
         JOIN task3.project p on p.id = e2.project_id;

SELECT e.name, p.name
FROM task3.employee e
         JOIN task3.employeeproject e2 on e.id = e2.employee_id
         JOIN task3.project p on p.id = e2.project_id
WHERE e2.project_id = 2;


-- Создать нового работника
INSERT INTO task3.employee (name, role_id)
VALUES ('Сергей Крикалев', 3);

-- Создать новый проект
INSERT INTO task3.Project (name)
VALUES ('Project 0'); -- 5

-- Создание в таблице task3.EmployeeProject новой связи где name = 'Сергей Крикалев'
INSERT INTO task3.EmployeeProject (employee_id, project_id)
VALUES ((SELECT e.id FROM task3.employee e WHERE name = 'Сергей Крикалев'), 4);

-- Изменение project_id=5 в таблице task3.EmployeeProject где name = 'Сергей Крикалев'
UPDATE task3.EmployeeProject
SET project_id=5
WHERE employee_id = (SELECT e.id FROM task3.employee e WHERE name = 'Сергей Крикалев');

-- DELETE FROM task3.employee WHERE name = 'Сергей Крикалев';

-- DELETE FROM task3.Project WHERE name = 'Project 0';


-- INSERT INTO task3.employee (name, department_id)
-- VALUES ('ЧЧЧ ЧЧЧ', 1);
--
-- -- Удаление работника где name = 'Сергей Крикалев'
-- DELETE FROM task3.employee WHERE name = 'ЧЧЧ ЧЧЧ';

