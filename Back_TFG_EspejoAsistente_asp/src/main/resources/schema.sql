INSERT INTO usuario (id_usuario, username, es_admin) VALUES(1, 'alejandro', false);
INSERT INTO usuario (id_usuario, username, es_admin) VALUES(2, 'pepe', false);
INSERT INTO usuario (id_usuario, username, es_admin) VALUES(3, 'juan', false);

INSERT INTO credenciales (id_credenciales, password, id_usuario) VALUES(1, 'alejandro',1);
INSERT INTO credenciales (id_credenciales, password, id_usuario) VALUES(2, 'pepe',2);
INSERT INTO credenciales (id_credenciales, password, id_usuario) VALUES(3, 'juan',3);