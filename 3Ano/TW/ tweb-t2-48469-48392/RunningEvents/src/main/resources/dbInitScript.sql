

-- Delete all database content

DROP TABLE IF EXISTS my_user_role CASCADE;
DROP TABLE IF EXISTS my_user CASCADE;
DROP TABLE IF EXISTS evento CASCADE;
DROP TABLE IF EXISTS inscricao CASCADE;
DROP TABLE IF EXISTS refs CASCADE;
DROP TABLE IF EXISTS pago CASCADE; 
DROP TABLE IF EXISTS tempos CASCADE;

CREATE TABLE my_user (
  user_name 	varchar(30) NOT NULL,
  user_pass 	varchar(255) NOT NULL,
  nome 		varchar(255) NOT NULL,
  genero 	char(1)      NOT NULL,
  escalao       varchar(10)  NOT NULL,
  enable 	smallint NOT NULL DEFAULT 1,
  PRIMARY KEY (user_name) 
);

-- staff / teste123
insert  into my_user (user_name,user_pass,nome,genero,escalao,enable) values ('staff','$2a$10$bKWhb9hIUD3xxxtzfhvodugWIK3Gbw4vRySYOnBqy2O4gtqZ78jUK','STAFF', 'f', 'staff',1);
-- user2 / teste123
insert  into my_user (user_name,user_pass,nome,genero,escalao,enable) values ('user2','$2a$10$bKWhb9hIUD3xxxtzfhvodugWIK3Gbw4vRySYOnBqy2O4gtqZ78jUK', 'Lucas', 'm', 'junior', 1);


CREATE TABLE my_user_role (
  user_name 	varchar(30) NOT NULL,
  user_role 	varchar(15) NOT NULL,
  FOREIGN KEY (user_name) REFERENCES my_user (user_name)
);

insert  into my_user_role (user_name,user_role) values ('staff','ROLE_STAFF');
insert  into my_user_role (user_name,user_role) values ('user2','ROLE_ATLETA');


CREATE TABLE evento (
    eid             SERIAL         NOT NULL ,
    evento_name  	varchar(30)  NOT NULL,
    descricao  		varchar(255) NOT NULL,
    data  		    date         NOT NULL,
    preco       	int         NOT NULL DEFAULT 0,
    PRIMARY KEY (evento_name)
);

insert  into evento ( evento_name,descricao,data,preco) values('maratona','Lisboa, 10km','16/01/2023','70');
insert  into evento (evento_name,descricao,data,preco) values('meia-maratona','Lisboa, 5km','20/01/2023','50');


CREATE TABLE inscricao
(
    user_name           varchar(30) NOT NULL,
    evento_name  	varchar(30)  NOT NULL,
    dorsal              SERIAL         NOT NULL,
    PRIMARY KEY (evento_name, user_name),
    FOREIGN KEY (evento_name) REFERENCES evento (evento_name),
    FOREIGN KEY (user_name) REFERENCES my_user (user_name)

);

CREATE TABLE refs
(
    user_name   varchar(30) NOT NULL,
    entidade   int         NOT NULL,
    referencia int         NOT NULL,
    valor      varchar(20) NOT NULL,
    evento_name  	varchar(30)  NOT NULL,
    PRIMARY KEY (entidade, referencia),
    FOREIGN KEY (user_name) REFERENCES my_user (user_name),
    FOREIGN KEY (evento_name) REFERENCES evento (evento_name)
);


CREATE TABLE pago
(
    user_name   varchar(30) NOT NULL,
    evento_name  	varchar(30)  NOT NULL,   
    PRIMARY KEY (user_name, evento_name),
    FOREIGN KEY (user_name) REFERENCES my_user (user_name),
    FOREIGN KEY (evento_name) REFERENCES evento (evento_name)
);


CREATE TABLE tempos
(
    evento_name  	varchar(30)  NOT NULL,
    dorsal       int         NOT NULL,
    sectionID    varchar(8)  NOT NULL,
    elapsed_time timestamp   NOT NULL,
    PRIMARY KEY (dorsal, evento_name, sectionID),
    FOREIGN KEY (evento_name) REFERENCES evento (evento_name)
);




