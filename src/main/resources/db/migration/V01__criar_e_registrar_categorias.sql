CREATE TABLE categoria (
	codigo BIGINT(20) primary key AUTO_INCREMENT,
    nome   VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

Insert into categoria(nome) values('Lazer');
Insert into categoria(nome) values('Alimentação');
Insert into categoria(nome) values('Supermercado');
Insert into categoria(nome) values('Netflix');