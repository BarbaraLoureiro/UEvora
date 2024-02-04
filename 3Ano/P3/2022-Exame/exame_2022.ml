% EX2 -------------------------------------------------

type romano = 
	| Z 
	| I of romano
	| V of romano
	| X of romano
	| L of romano
	| C of romano
	| D of romano
	| M of romano;;


% ALINEA A

	let rec valor r =
	  match r with
	  | Z -> 0
	  | I r' -> 1 + valor r'
	  | V r' -> 5 + valor r'
	  | X r' -> 10 + valor r'
	  | L r' -> 50 + valor r'
	  | C r' -> 100 + valor r'
	  | D r' -> 500 + valor r'
	  | M r' -> 1000 + valor r';;

% ALINEA B

	let rec valor_romano n =
	  match n with
	  | 0 -> Z
	  | n when n >= 1000 -> M (valor_romano (n - 1000))
	  | n when n >= 500 -> D (valor_romano (n - 500))
	  | n when n >= 100 -> C (valor_romano (n - 100))
	  | n when n >= 50 -> L (valor_romano (n - 50))
	  | n when n >= 10 -> X (valor_romano (n - 10))
	  | n when n >= 5 -> V (valor_romano (n - 5))
	  | n -> I (valor_romano (n - 1));;

	let rec soma r1 r2 =
  		let valor_total = valor r1 + valor r2 in
  		valor_romano valor_total;;

% ALINEA C

type romano = 
  | Z
  | I of romano
  | V of romano
  | X of romano
  | L of romano
  | C of romano
  | D of romano
  | M of romano
  | IV of romano
  | IX of romano
  | XL of romano
  | XC of romano
  | CD of romano
  | CM of romano;;