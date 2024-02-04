e(lisboa, santarem).
e(santarem, coimbra).
e(santarem, caldas).
e(caldas, lisboa).
e(coimbra, porto).
e(lisboa, evora).
e(evora, beja).
e(lisboa, faro).
e(beja, faro).

a(X, Y) :- e(X, Y).
a(X, Y) :- e(X, Z), a(Z, Y).
