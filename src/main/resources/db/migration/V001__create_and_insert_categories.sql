CREATE TABLE categoria(
	codigo BIGINT(20) NOT NULL AUTO_INCREMENT,
	descricao VARCHAR(100) NOT NULL,
	CONSTRAINT pk_categoria PRIMARY KEY(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (descricao) values ('Comportamental');
INSERT INTO categoria (descricao) values ('Programação');
INSERT INTO categoria (descricao) values ('Qualidade');
INSERT INTO categoria (descricao) values ('Processos');
