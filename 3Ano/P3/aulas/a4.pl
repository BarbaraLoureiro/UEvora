lista([]). 
lista([_|L]) :- lista(L).

membro(X, [X|_]).
membro(X, [_|L]) :- membro(X, L).

prefixo([], _).
prefixo([X|A], [X|B]) :- prefixo(A, B).

sufixo(A, A).
sufixo(A, [_|B]) :- sufixo(A, B).

sublista(S, L) :- prefixo(P, L), sufixo(S, P).

catena([], L, L).
catena([X|Xs], L, [X|Y]) :- catena(Xs, L, Y).
