% EX1 - UNIFICAÇÕES -----------------------------------------------------------------------

/*
 ALINEA A -> H = a, T = [c,d]

 ALINEA B -> A = maca, R = uva

 ALINEA C -> R = [S,c]

 ALINEA D -> A = a, B = [], R = []
 
 ALINEA E -> UM = dois, DOIS = um

 ALINEA F -> X = c(d,e)

*/

% EX2 - ESTRUTURAS ---------------------------------------------------------------------------

% ALINEA A

	romano(z).
	romano(i(X)) :- romano(X).
	romano(v(X)) :- romano(X).
	romano(x(X)) :- romano(X).
	romano(l(X)) :- romano(X).
	romano(c(X)) :- romano(X).
	romano(d(X)) :- romano(X).
	romano(m(X)) :- romano(X).

% ALINEA B

	% Base case: Soma de zero com qualquer número romano é o próprio número romano.
	soma(z, X, X) :- romano(X).

	% Regras de soma para números romanos.
	
	soma(X, z, X) :- romano(X).  % Soma de qualquer número romano com zero é o próprio número romano.

	soma(i(X), Y, Result) :- soma(X, Y, Result1), Result = i(Result1).  % Regra para i(X) + Y = i(X+Y)
	soma(v(X), Y, Result) :- soma(X, Y, Result1), Result = v(Result1).  % Regra para v(X) + Y = v(X+Y)
	soma(x(X), Y, Result) :- soma(X, Y, Result1), Result = x(Result1).  % Regra para x(X) + Y = x(X+Y)
	soma(l(X), Y, Result) :- soma(X, Y, Result1), Result = l(Result1).  % Regra para l(X) + Y = l(X+Y)
	soma(c(X), Y, Result) :- soma(X, Y, Result1), Result = c(Result1).  % Regra para c(X) + Y = c(X+Y)
	soma(d(X), Y, Result) :- soma(X, Y, Result1), Result = d(Result1).  % Regra para d(X) + Y = d(X+Y)
	soma(m(X), Y, Result) :- soma(X, Y, Result1), Result = m(Result1).  % Regra para m(X) + Y = m(X+Y)

% ALINEA C

	romano_desfaz(z, 0).
	romano_desfaz(i(X), Y) :- romano_desfaz(X, Y1), Y is Y1 + 1.
	romano_desfaz(v(X), Y) :- romano_desfaz(X, Y1), Y is Y1 + 5.
	romano_desfaz(x(X), Y) :- romano_desfaz(X, Y1), Y is Y1 + 10.
	romano_desfaz(l(X), Y) :- romano_desfaz(X, Y1), Y is Y1 + 50.
	romano_desfaz(c(X), Y) :- romano_desfaz(X, Y1), Y is Y1 + 100.
	romano_desfaz(d(X), Y) :- romano_desfaz(X, Y1), Y is Y1 + 500.
	romano_desfaz(m(X), Y) :- romano_desfaz(X, Y1), Y is Y1 + 1000.

	romano_faz(z, 0).
	romano_faz(m(X), Y):- Y >= 1000, Y1 is Y - 1000, romano_faz(X,Y1).
	romano_faz(d(X), Y):- Y < 1000, Y >= 500, Y1 is Y - 500, romano_faz(X,Y1).
	romano_faz(c(X), Y):- Y < 500, Y >= 100, Y1 is Y - 100, romano_faz(X,Y1).
	romano_faz(l(X), Y):- Y < 100, Y >= 50, Y1 is Y - 50, romano_faz(X,Y1).
	romano_faz(x(X), Y):- Y < 50, Y >= 10, Y1 is Y - 10, romano_faz(X,Y1).
	romano_faz(v(X), Y):- Y < 10, Y >= 5, Y1 is Y - 5, romano_faz(X,Y1).
	romano_faz(i(X), Y):- Y < 5, Y >= 1, Y1 is Y - 1, romano_faz(X,Y1).


	normaliza(X, Y):- romano_desfaz(X, Z), romano_faz(Y, Z).

% ALINEA D

/*
Para redefinir o predicado normaliza/'2 para usar todas as regras dos algarismos romanos, é preciso modificar os predicados romano_faz e romano_desfaz para lidar com a notação subtrativa usada nos algarismos romanos.

Na notação subtrativa, quatro é representado como IV (um antes de cinco) em vez de IIII, nove é IX (um antes de dez) em vez de VIIII e assim por diante. Isto aplica-se a todas as potências de dez, então quarenta é XL, noventa é XC e assim por diante.

por exemplo:

romano_desfaz(i(x(z)), Y) :- Y is 9.
romano_desfaz(i(c(z)), Y) :- Y is 90.

romano_faz(i(x(z)), Y):- Y >= 9, Y1 is Y - 9.
romano_faz(i(c(z)), Y):- Y >= 90, Y1 is Y - 90.

*/'


