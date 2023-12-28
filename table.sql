--CRIAR TABELA--
CREATE TABLE espacos
(
    local_zoo VARCHAR(2) NOT NULL PRIMARY KEY,
    area_local SERIAL NOT NULL,
    temperatura_local TEXT NOT NULL,
    humidade_local TEXT NOT NULL,
    meio_local TEXT NOT NULL
);

CREATE TABLE animais
(
    nome_animal TEXT NOT NULL,
    registo_animal VARCHAR(6) NOT NULL PRIMARY KEY,
    sexo_animal TEXT NOT NULL,
    local_zoo VARCHAR(2),
    FOREIGN KEY (local_zoo) REFERENCES espacos ON DELETE RESTRICT
);

CREATE TABLE cativeiro
(
    registo_animal VARCHAR(6) NOT NULL,
    data_nasc DATE NOT NULL,
    registo_pai VARCHAR(6) NOT NULL,
    registo_mae VARCHAR(6) NOT NULL,
    FOREIGN KEY (registo_animal) REFERENCES animais ON DELETE RESTRICT,
    FOREIGN KEY (registo_pai) REFERENCES animais ON DELETE RESTRICT,
    FOREIGN KEY (registo_mae) REFERENCES animais ON DELETE RESTRICT
);

CREATE TABLE captura
(
    registo_animal VARCHAR(6) NOT NULL,
    data_estim DATE NOT NULL,
    local_cap TEXT NOT NULL,
    data_cap DATE NOT NULL,
    FOREIGN KEY (registo_animal) REFERENCES animais ON DELETE RESTRICT
);

CREATE TABLE class_bio
(
    registo_animal VARCHAR(6) NOT NULL,
    classe TEXT NOT NULL,
    ordem TEXT NOT NULL,
    familia TEXT NOT NULL,
    especie TEXT NOT NULL,
    PRIMARY KEY(registo_animal, especie),
    FOREIGN KEY (registo_animal) REFERENCES animais ON DELETE RESTRICT
);

CREATE TABLE funcionarios
(
    nome_func TEXT NOT NULL,
    nif_func VARCHAR(9) NOT NULL PRIMARY KEY,
    data_inicio DATE NOT NULL
);

CREATE TABLE telefones
(
    nif_func VARCHAR(9) NOT NULL,
    telefone VARCHAR(9) NOT NULL,
    FOREIGN KEY (nif_func) REFERENCES funcionarios ON DELETE RESTRICT
);

CREATE TABLE tratadores
(
    registo_animal VARCHAR(6) NOT NULL,
    nif_func VARCHAR(9) NOT NULL,
    PRIMARY KEY (registo_animal, nif_func),
    FOREIGN KEY (registo_animal) REFERENCES animais ON DELETE RESTRICT,
    FOREIGN KEY (nif_func) REFERENCES funcionarios ON DELETE RESTRICT
);

CREATE TABLE tratadores_auxiliares
(
    nif_func VARCHAR(9) NOT NULL,
    local_zoo VARCHAR(2) NOT NULL,
    PRIMARY KEY (nif_func, local_zoo),
    FOREIGN KEY (nif_func) REFERENCES funcionarios ON DELETE RESTRICT,
    FOREIGN KEY (local_zoo) REFERENCES espacos ON DELETE RESTRICT
);

CREATE TABLE responsaveis
(
    nif_func VARCHAR(9) NOT NULL,
    nif_resp VARCHAR(9) NOT NULL ,
    PRIMARY KEY (nif_func, nif_resp), 
    FOREIGN KEY (nif_func) REFERENCES funcionarios ON DELETE RESTRICT,
    FOREIGN KEY (nif_resp) REFERENCES funcionarios ON DELETE RESTRICT
);

CREATE TABLE administradores
(
    nif_func VARCHAR(9) NOT NULL,
    FOREIGN KEY (nif_func) REFERENCES funcionarios ON DELETE RESTRICT
); 

CREATE TABLE veterinarios
(
    nif_func VARCHAR(9) NOT NULL,
    FOREIGN KEY (nif_func) REFERENCES funcionarios ON DELETE RESTRICT
); 

CREATE TABLE consultas 
( 
    registo_animal VARCHAR(6) NOT NULL,
    nif_func VARCHAR(9) NOT NULL,
    consult TEXT NOT NULL,
    descricao_consult TEXT NOT NULL,
    data_consult DATE NOT NULL,
    FOREIGN KEY (registo_animal) REFERENCES animais ON DELETE RESTRICT,
    FOREIGN KEY (nif_func) REFERENCES funcionarios ON DELETE RESTRICT
)