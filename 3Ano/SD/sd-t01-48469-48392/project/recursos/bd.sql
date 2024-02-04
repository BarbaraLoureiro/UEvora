/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  b4rbara
 * Created: Dec 4, 2023
 */

-- public.artistas definition

-- Drop table

-- DROP TABLE public.artistas;

CREATE TABLE artistas (
        artistid serial NOT NULL,
	nome varchar NULL,
	tipoarte varchar NULL,
	localizacao varchar NULL,
	atuar bool NULL,
	estado bool NULL
);

INSERT INTO artistas (nome,tipoarte,localizacao,atuar,estado) VALUES
	 ('Gustavo','Dança','Évora',true,true),
	 ('Bárbara','Teatro','Lisboa',true,false),
	 ('Ricardo','Dança','Aveiro',false,true);
