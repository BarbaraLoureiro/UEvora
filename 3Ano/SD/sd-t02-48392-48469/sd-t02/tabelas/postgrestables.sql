DROP TABLE artistas CASCADE;
DROP TABLE users CASCADE;
DROP TABLE performances CASCADE;
DROP TABLE donativos CASCADE;

CREATE TABLE artistas (
    aid serial PRIMARY KEY,
    nome varchar NULL,
    tipo varchar NULL,
    estado varchar NULL
);

CREATE TABLE users (
    uid serial PRIMARY KEY,
    username varchar UNIQUE NOT NULL,
    email varchar UNIQUE NOT NULL,
    password varchar NOT NULL,
    usertype varchar NOT NULL
);

CREATE TABLE performances (
    aid int NOT NULL,
    pdata date NOT NULL,
    latitude varchar NOT NULL,
    longitude varchar NOT NULL,
    FOREIGN KEY (aid) REFERENCES artistas (aid)
);

CREATE TABLE donativos (
    aid int NOT NULL,
    valor varchar NOT NULL,
    ddata date NOT NULL,
    FOREIGN KEY (aid) REFERENCES artistas (aid)
);

INSERT INTO users(username,email,password,usertype) VALUES				
('barbara','barbara@sapo.pt','$2a$10$bKWhb9hIUD3xxxtzfhvodugWIK3Gbw4vRySYOnBqy2O4gtqZ78jUK','ADMIN'),		
('gustavo','gustavo@sapo.pt','$2a$10$bKWhb9hIUD3xxxtzfhvodugWIK3Gbw4vRySYOnBqy2O4gtqZ78jUK','USER');


INSERT INTO artistas (nome,tipo,estado) VALUES
	 ('Maria','Dança','aprovado'),
	 ('André','Teatro','desaprovado'),
	 ('João','Pintura','aprovado');
	 
INSERT INTO performances (aid,pdata,latitude,longitude) VALUES
    (1, '2024-12-05','69.8013','133.7360'),
    (3, '2024-01-20','-55.3569','147.3561'),
    (1,'2024-06-03','22.9330','-65.2302');
   