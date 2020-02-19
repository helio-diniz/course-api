CREATE TABLE usuario(
	id BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(60) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
CREATE TABLE permissao(
	id BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
CREATE TABLE usuario_permissao(
	usuario_id BIGINT(20) NOT NULL,
	permissao_id BIGINT(20) NOT NULL,
	PRIMARY KEY(usuario_id, permissao_id),
	FOREIGN KEY(usuario_id) REFERENCES usuario(id),
	FOREIGN KEY(permissao_id) REFERENCES permissao(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
INSERT INTO usuario (id, nome, email, senha) values (1, 'Administrator', 'admin@challenge.com', '$2a$10$APFWWAOTREvEpmHm1upVw.LESH0MQ3/p24NuCbVzQ6HJNOzdMMreS');
INSERT INTO usuario (id, nome, email, senha) values (2, 'João Martins', 'joao.martins@challenge.com', '$2a$10$APFWWAOTREvEpmHm1upVw.LESH0MQ3/p24NuCbVzQ6HJNOzdMMreS');
	
INSERT INTO permissao (id, descricao) values (1, 'ROLE_REGISTER_CATEGORY');
INSERT INTO permissao (id, descricao) values (2, 'ROLE_SEARCH_CATEGORY');
INSERT INTO permissao (id, descricao) values (3, 'ROLE_REGISTER_COURSE');
INSERT INTO permissao (id, descricao) values (4, 'ROLE_DELETE_COURSE');
INSERT INTO permissao (id, descricao) values (5, 'ROLE_SEARCH_COURSE');
	
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 1);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 2);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 3);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 4);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 5);

INSERT INTO usuario_permissao (usuario_id, permissao_id) values (2, 2);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (2, 5);


