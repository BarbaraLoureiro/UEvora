homem(joao).
homem(rui).
homem(manuel).
homem(ricardo).

mulher(maria).
mulher(ana).
mulher(rita).
mulher(silvia).
mulher(joana).

progenitor(joao, maria).
progenitor(joao, rui).
progenitor(manuel, joao).
progenitor(ricardo, manuel).
progenitor(ana, rui).
progenitor(joana, maria).
progenitor(rita, joao).
progenitor(rita, silvia).

pai(X,Y) :- homem(X), progenitor(X,Y).

mae(X,Y) :- mulher(X), progenitor(X,Y).

avo(X,Y) :- progenitor(X,Z), progenitor(Z,Y).

meioirmao(X,Y) :- pai(Z,X), pai(Z,Y), mae(MY,Y), mae(MX,X), X\=Y, MX\=MY.
meioirmao(X,Y) :- mae(Z,X), mae(Z,Y), pai(MY,Y), pai(MX,X), X\=Y, MX\=MY.

tio(X,Y) :- progenitor(Z,Y), irmao(X,Z).

primo(X,Y) :- tio(Z,X), progenitor(Z,Y).

irmao(X,Y) :- pai(Z,X), pai(Z,Y), mae(W,X), mae(W,Y), X\=Y.

irma(X,Y) :-  mulher(X), pai(Z,X), pai(Z,Y), mae(W,X), mae(W,Y), X\=Y.
