--INSERIR DADOS NA TABELA--

INSERT INTO espacos
    (local_zoo, area_local, temperatura_local, humidade_local, meio_local)
VALUES
    ('A1', '2000', 'quente', 'seca', 'misto'),
    ('A2', '1500', 'fria', 'fria', 'terrestre'),
    ('A3', '1200', 'quente', 'húmida', 'terrestre'),
    ('A4', '1100', 'quente', 'húmida', 'terrestre'),
    ('A5', '500', 'quente', 'húmida', 'terrestre');
    
INSERT INTO animais 
    (nome_animal, registo_animal, sexo_animal, local_zoo) 
VALUES 
    ('Hipo', '123444', 'masculino', 'A1'),
    ('Tapi', '223444', 'feminino', 'A1'),
    ('Hita', '323444', 'feminino', 'A1'),
    ('Kaki', '123666', 'masculino', 'A2'),
    ('Kalu', '223666', 'feminino', 'A2'),
    ('Kilu', '323666', 'feminino', 'A2'),
    ('Luka', '423666', 'feminino', 'A2'),
    ('Kuli', '524666', 'masculino', 'A2'),
    ('Taji', '123456', 'masculino', 'A3'),
    ('Mali', '222456', 'feminino', 'A3'),
    ('Aka', '322456', 'feminino', 'A3'),
    ('Táta', '422456', 'feminino', 'A4'),
    ('Mata', '622456', 'masculino', 'A4'),
    ('Cáta', '432456', 'masculino', 'A4'),
    ('Kata', '522456', 'feminino', 'A4'),
    ('Ará', '123555', 'masculino', 'A5'),
    ('Zará', '133555', 'masculino', 'A5'),
    ('Rará', '223555', 'feminino', 'A5'),
    ('Rara', '323555', 'masculino', 'A5'),
    ('Zula', '423555', 'feminino', 'A5'),
    ('Zura', '523555', 'feminino', 'A5');

INSERT INTO cativeiro 
    (registo_animal, data_nasc, registo_pai, registo_mae)
VALUES 
    ('323444', '2006/09/01', '123444', '223444'),
    ('323666', '2008/04/03', '123666', '223666'),
    ('524666', '2008/03/04', '123666', '423666'),
    ('322456', '2005/12/12', '123456', '222456'),
    ('422456', '2006/01/20', '123456', '222456'),
    ('522456', '2007/03/02', '432456', '422456'),
    ('622456', '2008/02/02', '123456', '522456'),
    ('323555', '2009/05/07', '123555', '223555'),
    ('423555', '2009/05/07', '123555', '223555'),
    ('523555', '2009/05/07', '123555', '223555');

INSERT INTO captura
    (registo_animal, data_estim, local_cap, data_cap)
VALUES
    ('123444', '2003/06/06', 'África, Madagáscar', '2004/06/06'),
    ('223444', '2003/12/06', 'África, Madagáscar','2004/06/06'),
    ('123666', '2005/06/20', 'Europa, Pirinéus', '2005/12/20'),
    ('223666', '2005/06/20', 'Europa, Ourense', '2005/12/20'),
    ('423666', '2006/08/20', 'Europa, Gerês', '2006/12/20'),
    ('123456', '2002/12/20', 'Índia, Angra', '2003/12/20'),
    ('222456', '2002/12/20', 'Índia, Deli', '2003/12/20'),
    ('432456', '2004/09/01', 'Índia, Calcutá','2005/01/01'),
    ('123555', '2005/06/20', 'América do Sul, Paraná', '2005/12/20'),
    ('133555', '2005/08/20', 'América do Sul, Paraná', '2005/12/20'),
    ('223555', '2006/10/20', 'América do Sul, Uruguai', '2006/12/20');

INSERT INTO class_bio
    (registo_animal, classe, ordem, familia, especie)
VALUES
    ('123456', 'mamíferos', 'carnívoros', 'felinos', 'tigre'),
    ('222456', 'mamíferos', 'carnívoros', 'felinos', 'tigre'),
    ('322456', 'mamíferos', 'carnívoros', 'felinos', 'tigre'),
    ('422456', 'mamíferos', 'carnívoros', 'felinos', 'tigre'),
    ('432456', 'mamíferos', 'carnívoros', 'felinos', 'tigre'),
    ('522456', 'mamíferos', 'carnívoros', 'felinos', 'tigre'),
    ('622456', 'mamíferos', 'carnívoros', 'felinos', 'tigre'),
    ('123444', 'mamíferos', 'artiodáctilos', 'hipopótamos', 'hipopótamo comum'),
    ('223444', 'mamíferos', 'artiodáctilos', 'hipopótamos', 'hipopótamo comum'),
    ('323444', 'mamíferos', 'artiodáctilos', 'hipopótamos', 'hipopótamo comum'),
    ('123666', 'mamíferos', 'artiodáctilos', 'cervídeos', 'veado'),
    ('223666', 'mamíferos', 'artiodáctilos', 'cervídeos', 'veado'),
    ('323666', 'mamíferos', 'artiodáctilos', 'cervídeos', 'veado'),
    ('423666', 'mamíferos', 'artiodáctilos', 'cervídeos', 'veado'),
    ('524666', 'mamíferos', 'artiodáctilos', 'cervídeos', 'veado'),
    ('123555', 'aves', 'psittaciformes', 'psittacidae', 'arara-azul-pequena'),
    ('133555', 'aves', 'psittaciformes', 'psittacidae', 'arara-azul-pequena'),
    ('223555', 'aves', 'psittaciformes', 'psittacidae', 'arara-azul-pequena'),
    ('323555', 'aves', 'psittaciformes', 'psittacidae', 'arara-azul-pequena'),
    ('423555', 'aves', 'psittaciformes', 'psittacidae', 'arara-azul-pequena'),
    ('523555', 'aves', 'psittaciformes', 'psittacidae', 'arara-azul-pequena');


INSERT INTO funcionarios
    (nome_func, nif_func, data_inicio)
VALUES
    ('Joaquim Silva', '123123123', '2003/02/01'),
    ('Manuel Santos', '123123124', '2003/04/01'),
    ('Maria Gomes', '123123125', '2003/01/01'),
    ('Mariana Silva', '123123126', '2004/02/01'),
    ('Jorge Gomes', '123123127', '2004/03/01'),
    ('Francisco Jorge', '123123128', '2004/03/01'), 
    ('Manuel Ferreira', '123123129', '2004/02/01'),
    ('Manuela Torres', '123123130', '2004/04/01'),
    ('Pedro Vale', '123123131', '2004/05/01'),
    ('Isabel Soares', '123123132', '2004/06/01');

INSERT INTO telefones 
    (nif_func, telefone)
VALUES
    ('123123123', '266787809'),
    ('123123123', '919999999'),
    ('123123124', '266787808'),
    ('123123124', '919999998'),
    ('123123125', '266787807'),
    ('123123125', '919999997'),
    ('123123126', '266787806'),
    ('123123126', '919999996'),
    ('123123127', '266787807'), 
    ('123123127', '919999995'),
    ('123123128', '266787806'),
    ('123123128', '919999994'),
    ('123123129', '266787806'),
    ('123123129', '919999996'),
    ('123123130', '266787806'),
    ('123123130', '919999996'),
    ('123123131', '266787816'),
    ('123123131', '919999986'),
    ('123123132', '266787826'),
    ('123123132', '919999976');

INSERT INTO tratadores
    (registo_animal, nif_func)
VALUES
    ('123456', '123123123'),
    ('222456', '123123123'),
    ('322456', '123123123'),
    ('422456', '123123123'),
    ('432456', '123123123'),
    ('522456', '123123123'),
    ('622456', '123123123'),
    ('123444', '123123124'),
    ('223444', '123123124'),
    ('323444', '123123124'),
    ('123666', '123123124'),
    ('223666', '123123124'),
    ('323666', '123123124'),
    ('423666', '123123124'),
    ('524666', '123123124'),
    ('123555', '123123125'),
    ('133555', '123123125'),
    ('223555', '123123125'),
    ('323555', '123123125'),
    ('423555', '123123125'),
    ('523555', '123123125');

INSERT INTO tratadores_auxiliares
    (nif_func, local_zoo)
VALUES
    ('123123126', 'A3'),
    ('123123126', 'A4'),
    ('123123126', 'A5'),
    ('123123127', 'A1'),
    ('123123128', 'A2'),
    ('123123128', 'A5');

INSERT INTO administradores
    ( nif_func)
VALUES
    ('123123129'),
    ('123123130');

INSERT INTO responsaveis
    (nif_func, nif_resp)
VALUES
    ('123123123', '123123125'),
    ('123123124', '123123125'),
    ('123123125', '123123130'),
    ('123123126', '123123130'),
    ('123123127', '123123130'),
    ('123123128', '123123130'),
    ('123123129', '123123130'),
    ('123123130', '123123129'),
    ('123123131', '123123129'),
    ('123123132', '123123131');

INSERT INTO veterinarios
    ( nif_func)
VALUES
    ('123123131'),
    ('123123132');

INSERT INTO consultas
    (registo_animal, nif_func, consult, descricao_consult, data_consult)
VALUES
    ('222456', '123123131', 'diagonóstico', 'grávida', '2005/08/12'),
    ('222456', '123123131', 'tratamento', 'cálcio injectado', '2005/09/12'),
    ('222456', '123123131', 'tratamento', 'parto', '2005/12/12'),
    ('222456', '123123131', 'diagnóstico', 'infecção', '2006/07/12'),
    ('222456', '123123131', 'tratamento', 'antibiótico injectado', '2006/07/12'),
    ('123666', '123123131', 'diagnóstico', 'infecção', '2009/05/12'),
    ('123666', '123123131', 'tratamento', 'antibiótico injectado', '2009/05/12'),
    ('123555', '123123131', 'diagnóstico', 'infecção', '2009/05/12'),
    ('123555', '123123131', 'tratamento', 'antibiótico injectado', '2009/05/12'),
    ('423555', '123123131', 'diagnóstico', 'infecção', '2009/05/12'),
    ('423555', '123123131', 'tratamento', 'antibiótico injectado', '2009/05/12'),
    ('223444', '123123131', 'diagnóstico', 'infecção', '2007/08/12'),
    ('223444', '123123131', 'tratamento', 'antibiótico injectado', '2007/08/12'),
    ('223444', '123123132', 'diagnóstico', 'grávida', '2006/07/12'),
    ('223444', '123123132', 'tratamento', 'cálcio injectado', '2006/07/12'),
    ('223444', '123123132', 'tratamento', 'parto', '2006/09/12'),
    ('223444', '123123132', 'diagnóstico', 'infecção', '2007/07/12'),
    ('223444', '123123132', 'tratamento', 'antibiótico injectado', '2007/07/12'),
    ('223444', '123123132', 'diagnóstico', 'grávida', '2007/07/12'),
    ('223444', '123123132', 'tratamento', 'cálcio injectado', '2007/07/12'),
    ('223444', '123123132', 'tratamento', 'parto', '2007/09/12'),
    ('423555', '123123132', 'diagnóstico', 'infecção', '2009/06/12'),
    ('423555', '123123132', 'tratamento', 'antibiótico injectado', '2009/06/12');