-- a) em que locais do zoo se podem visitar aves? --

SELECT DISTINCT a.local_zoo
FROM animais AS a
NATURAL INNER JOIN class_bio AS cb
WHERE cb.classe = 'aves'
ORDER BY a.local_zoo ASC;

-- b) Em que locais do zoo não há carnívoros?--

SELECT DISTINCT a.habitat_animal
FROM animais AS a
NATURAL INNER JOIN class_bio AS cb
WHERE cb.ordem != 'carnívoros'
ORDER BY a.local_zoo ASC;

-- c) Indique os irmãos da Kilu. --

SELECT a.nome_animal
FROM animais AS a
NATURAL INNER JOIN cativeiro AS ctv
WHERE ctv.registo_pai = (SELECT ctv.registo_pai FROM cativeiro AS ctv INNER JOIN animais AS a ON a.registo_animal = ctv.registo_animal WHERE A.nome_animal = 'Kilu') AND a.nome_animal != 'Kilu';

-- d) Indique os telefones do tratador responsável pela Kata. --

SELECT telefone
FROM telefones 
NATURAL INNER JOIN tratadores 
NATURAL INNER JOIN animais
WHERE animais.nome_animal = 'Kata';
                        
-- e) Indique os telefones do responsável pelo auxiliar responsável pela local onde está a Kata. --

SELECT telefone
FROM telefones
INNER JOIN responsaveis
ON telefones.nif_func = responsaveis.nif_resp 
WHERE responsaveis.nif_func = (SELECT nif_func FROM tratadores_auxiliares INNER JOIN animais ON tratadores_auxiliares.local_zoo = animais.local_zoo WHERE nome_animal = 'Kata');

-- f) Indique os tratamentos (data e tratamento) que a Mali já fez no zoo. --

SELECT vet.consult, vet.descricao_consult, vet.data_consult
FROM veterinarios AS vet
INNER JOIN animais AS a 
ON vet.registo_animal = a.registo_animal
WHERE a.nome_animal = 'Mali'
ORDER BY vet.data_consult ASC;

-- g) Indique os nomes dos veterinários que já diagnosticaram uma gravidez a um carnívoro.--

SELECT f.nome_func
FROM funcionarios AS f
INNER JOIN veterinarios AS vet
ON f.nif_func = vet.nif_func
INNER JOIN class_bio AS cb
ON vet.registo_animal = cb.registo_animal
WHERE vet.descricao_consult = 'grávida' AND cb.ordem = 'carnívoros';

-- h) Indique para cada família da ordem artiodáctilos quantos animais tem o zoo. --



-- i) Indique para cada espécie quais os pares de animais que podem ser acasalados, sabendo que não se devem acasalar pais com filhos ou irmãos. --



-- j) Qual ́e a ordem com mais animais no zoo? --

SELECT cb.ordem 
FROM class_bio AS cb
WHERE (SELECT COUNT() FROM cb WHERE cb = 'carnívoros')>(SELECT COUNT() FROM cb WHERE cb = 'artiodáctilos')
ORDER BY 

-- k) Qual ́e a ordem dos animais que têm mais de 5 consultas por ano (diagnóstico ou tratamento). --

SELECT cb.ordem
FROM class_bio AS cb 
INNER JOIN veterinarios AS vet 
ON cb.registo_animal = vet.registo_animal
WHERE vet.consult--


-- l) Indique o número de animais nascidos em cativeiro. --

SELECT COUNT(*)
FROM cativeiro;

-- m) Qual ́e o animal (nome e espécie) mais velho do zoo? --

SELECT animais.nome_animal, class_bio.especie
FROM (SELECT MIN(data_nasc) AS d from cativeiro) AS r, animais, class_bio
WHERE r.registo_animal = animais.registo_animal;

-- n) Qual ́e o local húmido com mais mamíferos? --

SELECT habitat_animal
FROM animais AS a 
INNER JOIN espacos AS e
ON a.habitat_animal = e.local_zoo
INNER JOIN class_bio AS cb 
ON cb.registo_animal = a.registo_animal
WHERE e.local_zoo = 'húmida' AND cb.classe = 'mamíferos'

-- o) Para cada tratador indique o número de mamíferos por que ́e responsável? --

SELECT nome_func, COUNT(registo_animal FROM funcionarios AS f
INNER JOIN tratadores AS t
ON f.nif_func = t.nif_func
INNER JOIN class_bio AS cb
ON t.registo_animal = cb.registo_animal
WHERE f.funcao_func = 'tratador' AND cb.classe = 'mamíferos';)

SELECT nome_func, num_mami 
FROM funcionarios AS f
INNER JOIN tratadores AS t
ON f.nif_func = t.nif_func
INNER JOIN class_bio AS cb
ON t.registo_animal = cb.registo_animal
WHERE f.funcao_func = 'tratador' AND WITH num_mami AS (SELECT COUNT(*) FROM cb WHERE cb.classe = 'mamíferos');




SELECT DISTINCT nome_func
FROM funcionarios AS f
INNER JOIN tratadores AS t
ON f.nif_func = t.nif_func
INNER JOIN class_bio AS cb
ON t.registo_animal = cb.registo_animal
WHERE f.funcao_func = 'tratador' AND cb.classe = 'mamíferos';


-- p) Indique o nome dos animais que já foram tratados por todos os veterinários? --

SELECT a.nome_animal
FROM animais AS a
INNER JOIN veterinarios AS vet 
ON a.registo_animal = vet.registo_animal
WHERE a.registo_animal = (SELECT vet.registo_animal FROM vet INNER JOIN funcionarios AS f ON vet.nif_func = f.nif_func WHERE f.funcao_func = 'veterinário');