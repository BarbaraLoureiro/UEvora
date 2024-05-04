%estado_inicial(e([x,o,x,v,v,o,x,v,o],x)).
  %estado_inicial(e([x,o,v,o,x,o,x,v,v],x)).

 estado_inicial(e([v,v,v,v,v,v,v,v,v],x)).

  inv(x,o).
  inv(o,x).
  op1(e(L,J),(C),e(L1,J1)):- inv(J,J1), subs(v,J,L,L1,1,C).

  subs(A,J, [A|R], [J|R],C,C).
  subs(A,J, [B|R], [B|S],N,C):- M is N+1, subs(A,J,R,S,M,C).

  terminal(e([O,O,O,_,_,_,_,_,_],_)):- O==x; O == o.
  terminal(e([_,_,_,O,O,O,_,_,_],_)):- O==x; O == o.
  terminal(e([_,_,_,_,_,_,O,O,O],_)):- O==x; O == o.

  terminal(e([O,_,_,O,_,_,O,_,_],_)):- O==x; O == o.
  terminal(e([_,O,_,_,O,_,_,O,_],_)):- O==x; O == o.
  terminal(e([_,_,O,_,_,O,_,_,O],_)):- O==x; O == o.

  terminal(e([O,_,_,_,O,_,_,_,O],_)):- O==x; O == o.
  terminal(e([_,_,O,_,O,_,O,_,_],_)):- O==x; O == o.

  terminal(e(L,_)):- \+ member(v,L).
  valor(e(L,_),0,P):-  \+ member(v,L),!.

  
  valor(E,V,P):-terminal(E),
  X is P mod 2,
   (X== 1,V=3;X==0,V= -3). 

  avalia(e(Q,J),V):- findall(V1,aval(e(Q,J),V1),L), soma(L,V).

 
  
  aval(e([O,v,v|_R],_),V):- (O==x, V=1; O == o, V= -1).
  aval(e([v,O,v|_R],_),V):- O==x, V=1; O == o,V= -1.
  aval(e([v,v,O|_R],_),V):- O==x, V=1; O == o,V= -1.

  aval(e([O,_,_,v,_,_,v,_,_],_)):- O==x; O == o.
  aval(e([v,_,_,O,_,_,v,_,_],_)):- O==x; O == o.
  aval(e([v,_,_,v,_,_,O,_,_],_)):- O==x; O == o.

  soma([],0).
  soma([X|R],V):- soma(R,V1), V is V1+X.
