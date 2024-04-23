%estado_fechado - serve para marcar os estados que já foram visitados
%max_estados - número máximo de estados 
%n_estados - número total de estados visitados durante uma pesquisa

:- dynamic(estado_fechado/1).
:- dynamic(max_estados/1).
:- dynamic(n_estados/1).

max_estados(0).
n_estados(0).

increment:- retract(n_estados(N)), N1 is N+1, asserta(n_estados(N1)).

actmax(N):- max_estados(N1), N1 >= N,!.
actmax(N):- retract(max_estados(_N1)), asserta(max_estados(N)).
 
%Descricao do problema:
%estado_inicial(Estado)
%estado_final(Estado)
%op(Eact,OP,Eseg,Custo)
%estado(Estado,estado_pai,Operador,Custo,H+C,Profundidade)

pesquisa(Problema,Alg):-
  consult(Problema),
  estado_inicial(S0),
  pesquisa(Alg,[estado(S0,[],[],0,0)],Solucao),
  escreve_seq_solucao(Solucao),
  retract(n_estados(Ns)),retract(max_estados(NL)),
  asserta(n_estados(0)),asserta(max_estados(0)),
  write(n_estados(visitados(Ns),lista(NL))),
  retractall(estado_fechado(_)).


pesquisa(profundidade,E,S):- pesquisa_profundidade(E,S).

pesquisa(largura,Ln,Sol):- pesquisa_largura(Ln,Sol).

pesquisa(it,Ln,Sol):- pesquisa_it(Ln,Sol,1).
pesquisa_it(Ln,Sol,P):- retractall(estado_fechado(E)), pesquisa_pLim(Ln,Sol,P).
pesquisa_it(Ln,Sol,P):- P1 is P+1, pesquisa_it(Ln,Sol,P1).


%pesquisa em profundidade
pesquisa_profundidade([estado(E, Pai, Op, C, P)|_], estado(E, Pai, Op, C, P)):-
  estado_final(E), increment.

pesquisa_profundidade([E|R], Sol):- increment ,asserta(estado_fechado(E)),
  expandep(E, Lseg), 
  esc(E),
  insere_inicio(Lseg, R, Resto),
  length(Resto,N),actmax(N),
  pesquisa_profundidade(Resto, Sol).


%pesquisa em largura
pesquisa_largura([estado(E,Pai,Op,C,P)|_],estado(E,Pai,Op,C,P)):- 
  estado_final(E), increment.

pesquisa_largura([E|R],Sol):- increment ,asserta(estado_fechado(E)),expandep(E,Lseg),
  esc(E),
  insere_fim(Lseg,R,Resto),
  length(Resto,N),actmax(N),
  pesquisa_largura(Resto,Sol).

expandep(estado(E,Pai,Op,C,P),L):- findall(estado(En,estado(E,Pai,Op,C,P),Opn,Cnn,P1),
                                      (op(E,Opn,En,Cn),
                                      \+ estado_fechado(estado(En,_,_,_,_)),
                                      P1 is P+1, Cnn is Cn+C),
                                      L).


%pesquisa iterativa
pesquisa_pLim([estado(E,Pai,Op,C,P)|_],estado(E,Pai,Op,C,P),_):- 
  estado_final(E), increment.


%pesquisa em profundidade limitada
pesquisa_pLim([E|R],Sol,Pl):- increment ,asserta(estado_fechado(E)),
    expandePl(E, Lseg,Pl), 
    esc(E),
    insere_inicio(Lseg, R, Resto),
    length(Resto,N),actmax(N),
    pesquisa_pLim(Resto, Sol,Pl).

expandePl(estado(E,Pai,Op,C,P),[],Pl):- Pl =< P, ! .
expandePl(estado(E,Pai,Op,C,P),L,_):- findall(estado(En,estado(E,Pai,Op,C,P),Opn,Cnn,P1),
                                      (op(E,Opn,En,Cn),
                                      \+ estado_fechado(estado(En,_,_,_,_)),
                                      P1 is P+1, Cnn is Cn+C),
                                      L).

insere_inicio([], L, L).
insere_inicio(L, [], L).
insere_inicio(R, T, L):-append(R,T,L).

%pesquisa ansiosa
%pesquisa_a([],_):- !,fail.
pesquisa_a([estado(E,Pai,Op,C,HC,P)|_],estado(E,Pai,Op,C,HC,P)):- estado_final(E),increment.

pesquisa_a([E|R],Sol):- increment, asserta(estado_fechado(E)), expande(E,Lseg), esc(E),
  insere_ord(Lseg,R,Resto),
  length(Resto,N), actmax(N),
  pesquisa_a(Resto,Sol).
                              
%pesquisa greedy
%pesquisa_g([],_):- !,fail.
pesquisa_g([estado(E,Pai,Op,C,HC,P)|_],estado(E,Pai,Op,C,HC,P)):- estado_final(E).

pesquisa_g([E|R],Sol):- increment,  asserta(estado_fechado(E)),  expande_g(E,Lseg), %esc(E),
  insere_ord(Lseg,R,Resto), length(Resto,N), actmax(N),
  pesquisa_g(Resto,Sol).

expande(estado(E,Pai,Op,C,HC,P),L):- findall(estado(En,estado(E,Pai,Op,C,HC,P),Opn,Cnn,HCnn,P1),
	(op(E,Opn,En,Cn), \+ estado_fechado(estado(En,_,_,_,_,_)),
	P1 is P+1, Cnn is Cn+C, h(En,H), HCnn is Cnn+H), L).

expande_g(estado(E,Pai,Op,C,HC,P),L):- findall(estado(En,estado(E,Pai,Op,C,HC,P),Opn,Cnn,H,P1),
	(op(E,Opn,En,Cn),
  \+ estado_fechado(estado(En,_,_,_,_,_)),
	P1 is P+1, Cnn is Cn+C, h(En,H)), L).

insere_ord([],L,L).
insere_ord([A|L],L1,L2):- insereE_ord(A,L1,L3), insere_ord(L,L3,L2).

insereE_ord(A,[],[A]).
insereE_ord(A,[A1|L],[A,A1|L]):- menor_estado(A,A1),!.
insereE_ord(A,[A1|L], [A1|R]):- insereE_ord(A,L,R).

menor_estado(estado(_,_,_,_,N,_), estado(_,_,_,_,N1,_)):- N < N1.

insere_fim([],L,L).
insere_fim(L,[],L).
insere_fim(R,[A|S],[A|L]):- insere_fim(R,S,L).


escreve_seq_solucao(estado(E,Pai,Op,Custo,Prof)):- 
  write(custo(Custo)),nl,
  write(profundidade(Prof)),nl,
  escreve_seq_accoes(estado(E,Pai,Op,_,_)).

escreve_seq_accoes([]).
escreve_seq_accoes(estado(E,Pai,Op,_,_)):- 
  escreve_seq_accoes(Pai),
  write(e(Op,E)),nl.

esc(A):- write(A), nl.