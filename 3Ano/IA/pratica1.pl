% [pratica1].

homem('Afonso Henriques','rei de Portugal',1109).
homem('Henrique de Borgonha','conde de Portugal',1069).
homem('Sancho I','rei de Portugal',1154).
homem('Fernando II','rei de Leão',1137).
homem('Afonso IX', 'rei de Leão e Castela', 1171).
homem('Afonso II', 'rei de Portugal',1185).
homem('Sancho II', 'rei de Portugal',1207).
homem('Afonso III', 'rei de Portugal',1210).


mulher('Teresa de Castela', 'condessa de Portugal', 1080).
mulher('Mafalda', 'condessa de Saboia', 1125).
mulher('Urraca', 'infanta de Portugal',1151).
mulher('Dulce de Barcelona','infanta de Aragão',1160).
mulher('Berengária', 'infanta de Portugal',1194).
mulher('Urraca C','infanta de Castela',1186).


filho('Afonso Henriques','Henrique de Borgonha').
filho('Afonso Henriques','Teresa de Castela').
filho('Urraca','Afonso Henriques').
filho('Sancho I','Afonso Henriques').
filho('Urraca','Mafalda').
filho('Sancho I','Mafalda').
filho('Afonso IX','Urraca').
filho('Afonso IX','Fernando II').
filho('Afonso II','Sancho I').
filho('Afonso II','Dulce de Barcelona').
filho('Berengária','Sancho I').
filho('Berengária','Dulce de Barcelona').
filho('Sancho II','Afonso II').
filho('Afonso III','Afonso II').
filho('Sancho II','Urraca C').
filho('Afonso III','Urraca C').

pessoa(A):- homem(A,_,_).
pessoa(A):- mulher(A,_,_).

filhop(A,B):- filho(A,B), homem(B,_,_).
filhom(A,B):- filho(A,B), mulher(B,_,_).

irmao(A,B):-  filhom(A,C), filhom(B,C), 
	      \+ (filhop(A,D), filhop(B,D)),  A\=B.

irmao(A,B):-  filhop(A,C), filhop(B,C),
              \+ (filhom(A,D), filhom(B,D)),  A\=B. 
                   
irmao(A,B):-  filhop(A,C), filhop(B,C),
              filhom(A,D), filhom(B,D),  A\=B.

irmaos(A,L):- pessoa(A), findall(B,irmao(A,B),L).

primoDireito(A,B):- filho(A,C), filho(B,D), irmao(C,D), A\=B.

primoAfastado(A,B):- filho(A, paiA), filho(paiA, avoC), filho(B, paiB), filho(paiB, avoC), A \= B, \+ irmao(A,B).

primos(A,L):- findall(P, (primoDireito(A,P); primoAfastado(A,P)), L).


