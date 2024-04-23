n(3).
m(3).

estado_inicial(e(pA(0,1),[(0,1),(0,3),(1,0),(1,2),(2,1),(2,3),(3,0),(3,2)])).
estado_final(e(3,3),[]).


%acções possíveis
%aspirar
op(e(pA(L,C), LSujas), aspirar, e(pA(L,C), LSujas1), 1) :- member((L,C),LSujas), 
							retira((L,C), LSujas, LSujas1).

%mover
op(e(pA(L,C), LSujas), baixo, e(pA(L1,C), LSujas), 1) :- m(M), L1 is L + 1, L < M.
op(e(pA(L,C), LSujas), cima, e(pA(L1,C), LSujas), 1) :- L1 is L - 1, L > 0.
op(e(pA(L,C), LSujas), esquerda, e(pA(L,C1), LSujas), 1) :- C1 is C - 1, C > 0.
op(e(pA(L,C), LSujas), direita, e(pA(L,C1), LSujas), 1) :- n(N), C1 is C + 1, C < N.

retira(_,[],[]).
retira(V,[V|R],R).
retira(V,[A|R],[A|S]):- A\=V, retira(V,R,S).

%estado_inicial(A),op(A,O,B,C).

