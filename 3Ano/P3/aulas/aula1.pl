homem(jorge).
homem(miguel).
homem(casimiro).
homem(tomas).

mulher(barbara).
mulher(sandra).
mulher(gracinda).
mulher(iris).

progenitor(jorge, tomas).
progenitor(gracinda, tomas).
progenitor(sandra, barbara).
progenitor(jorge, barbara).
progenitor(casimiro, sandra).
progenitor(casimiro, miguel).
progenitor(gracinda, sandra).
progenitor(gracinda, miguel).
progenitor(miguel, iris).

pai(X,Y) :- homem(X), progenitor(X,Y).

mae(X,Y) :- mulher(X), progenitor(X,Y).

avo(X,Y) :- progenitor(X,Z), progenitor(Z,Y).

meioirmao(X,Y) :- pai(Z,X), pai(Z,Y), mae(MY,Y), mae(MX,X), X\=Y, MX\=MY.
meioirmao(X,Y) :- mae(Z,X), mae(Z,Y), pai(MY,Y), pai(MX,X), X\=Y, MX\=MY.

tio(X,Y) :- progenitor(Z,Y), irmao(X,Z).

primo(X,Y) :- tio(Z,X), progenitor(Z,Y).

irmao(X,Y) :- pai(Z,X), pai(Z,Y), mae(W,X), mae(W,Y), X\=Y.

irma(X,Y) :-  mulher(X), pai(Z,X), pai(Z,Y), mae(W,X), mae(W,Y), X\=Y.

antepassado(X,Y) :- progenitor(X,Y).
antepassado(X,Y) :- progenitor(Z,Y), antepassado(X,Z).

descendente(X,Y) :- antepassado(Y,X).

