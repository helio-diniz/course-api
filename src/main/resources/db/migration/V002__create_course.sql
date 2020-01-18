CREATE TABLE curso(
	codigo BIGINT(20) NOT NULL AUTO_INCREMENT,
	data_inicio DATE NOT NULL,
	data_termino DATE NOT NULL,
	quantidade_alunos INT,
	categoria_codigo BIGINT(20) NOT NULL,
	CONSTRAINT pk_curso PRIMARY KEY(codigo),
	CONSTRAINT fk_categoria FOREIGN KEY (categoria_codigo) REFERENCES categoria(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;