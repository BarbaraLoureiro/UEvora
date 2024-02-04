% EX1 - UNIFICAÇÕES -----------------------------------------------------------------------

/*
ALINEA A -> H = a, T = [b,c]

ALINEA B -> A = maca, R = [uva]

ALINEA C -> R = [b,c], S = a

ALINEA D -> A = a, B = [], R = []

ALINEA E -> UM = dois, DOIS = um

ALINEA F -> FALSE
*/


% EX2 - ESTRUTURAS --------------------------------------------------------------------------

% ALINEA A

/*
	romano(z).
	romano(i(X)):- romano(X).
	romano(v(X)):- romano(X).
	romano(x(X)):- romano(X).
	romano(l(X)):- romano(X).
	romano(c(X)):- romano(X).
	romano(d(X)):- romano(X).
	romano(m(X)):- romano(X).

*/

% ALINEA B

	romano(z).
	romano(i(z)).
	romano(i(i(X))):- romano(i(X)).
	romano(v(z)).
	romano(v(v(X))):- romano(v(X)).
	romano(v(i(X))):- romano(i(X)).
	romano(x(z)).
	romano(x(i(X))):- romano(i(X)).
	romano(x(v(X))):- romano(v(X)).
	romano(x(x(X))):- romano(x(X)).
	romano(l(z)).
	romano(l(i(X))):- romano(i(X)).
	romano(l(v(X))):- romano(v(X)).
	romano(l(x(X))):- romano(x(X)).
	romano(l(l(X))):- romano(l(X)).
	romano(c(z)).
	romano(c(i(X))):- romano(i(X)).
	romano(c(v(X))):- romano(v(X)).
	romano(c(x(X))):- romano(x(X)).
	romano(c(l(X))):- romano(l(X)).
	romano(c(c(X))):- romano(c(X)).
	romano(d(z)).
	romano(d(i(X))):- romano(i(X)).
	romano(d(v(X))):- romano(v(X)).
	romano(d(x(X))):- romano(x(X)).
	romano(d(l(X))):- romano(l(X)).
	romano(d(c(X))):- romano(c(X)).
	romano(d(d(X))):- romano(d(X)).
	romano(m(z)).
	romano(m(i(X))):- romano(i(X)).
	romano(m(v(X))):- romano(v(X)).
	romano(m(x(X))):- romano(x(X)).
	romano(m(l(X))):- romano(l(X)).
	romano(m(c(X))):- romano(c(X)).
	romano(m(d(X))):- romano(d(X)).
	romano(m(m(X))):- romano(m(X)).

	
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



	
	
	
	
		

		


	
	
