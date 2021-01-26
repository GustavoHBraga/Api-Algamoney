CREATE TABLE pessoa (
	codigo 		BIGINT(20) primary key AUTO_INCREMENT,
    nome   		VARCHAR(50) NOT NULL,
    ativo  		BOOLEAN     NOT NULL,
    logradouro 		VARCHAR(50),
    numero     		VARCHAR(50),
    complemento 	VARCHAR(50),
    bairro 		VARCHAR(50),
    cep    		VARCHAR(50),
    cidade 		VARCHAR(50),
    estado 		VARCHAR(50)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

Insert into pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
	values('Gustavo Braga',true,'Rua Rio Paranapanema','147','Cond Serigueiras','Iapi','06233-000','Osasco','São Paulo');

Insert into pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
	values('Fernando Silva',true,'Rua Rio Mutinga','17','Lado do posto Br','Pirituba','06043-015','São Paulo','São Paulo');
	
Insert into pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
	values('Alessandra',true,'rua exemplo ','111','Março 2','Mutinga','06003-000','Osasco','São Paulo');
	
Insert into pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
	values('Gabriel Max',true,'Rua exemplo','112','ao lado da praça','Exemplo 1','11233-000','Barueri','São Paulo');
	
Insert into pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
	values('Miguel Castro',true,'Rua exemplo','113','qualquer coisa','barueri','18233-000','Barueri','São Paulo');

Insert into pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
	values('Marcelo ',true,'Rua Bahia','15','Cond alpha 1','Morumbi','015583-000','São Paulo','São Paulo');
	
Insert into pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
	values('Beatriz Sato ',true,'Rua Liberdade','85','exemplo 2','Liberdade','58583-000','São Paulo','São Paulo');