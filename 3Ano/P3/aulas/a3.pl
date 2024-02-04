num(z).
num(s(X)) :- num(X).

le(z, X) :- num(X).
le(s(A), s(B)) :- le(A, B).

lt(z, s(X)) :- num(X).
lt(s(A), s(B)) :- lt(A, B).

soma(z, X, X) :- num(X).
soma(s(X), Y, s(Z)) :- soma(X, Y, Z).

le(A, B) :- soma(A, X, B), num(X).
