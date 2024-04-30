INSERT INTO USUARIOS (username, password, enabled, nombre, apellidos, email) VALUES ('aguerrar06', '$2a$10$m9uU6ThXKpUwU9aOivK8pecPHkuFhKYTsoIKL/qBgVm2ci9F82eqe', 1, 'Alvaro', 'Guerra Rodriguez', 'aguerrar06@gmail.com');
INSERT INTO USUARIOS (username, password, enabled, nombre, apellidos, email) VALUES ('lbarragan01', '$2a$10$tPwTbFeWqPNYXPOD0LRt2O1EARV4/YcvEynX6CMHTvEqKgeJxAJVm', 1, 'Lorena', 'Barragan Beautes', 'lorenabarraganb@gmail.com');

INSERT INTO ROLES (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO ROLES (nombre) VALUES ('ROLE_USER');

INSERT INTO USUARIOS_ROLES (usuario_id, roles_id) VALUES (1, 1);
INSERT INTO USUARIOS_ROLES (usuario_id, roles_id) VALUES (2, 1);
INSERT INTO USUARIOS_ROLES (usuario_id, roles_id) VALUES (2, 2);