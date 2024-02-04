% PROGRAÇÃO FUNCIONAL ---------------------------------------------------------------------------

% EX 4

	type color = Red | Black;;

	type rbtNode =
	  | NIL
	  | Node of color * int * rbtNode * rbtNode;;

	let example_tree : rbtNode = Node (Black, 5, Node (Red, 3, NIL, NIL), Node (Red, 8, NIL, NIL));;


% EX 5

	let rec membro tree x =
	  match tree with
	  | NIL -> false
	  | Node(_, v, left, right) ->
	    if x < v then membro left x
	    else if x > v then membro right x
	    else true;;


	let tree : rbtNode = Node (Black, 5, Node (Red, 3, NIL, NIL), Node (Red, 8, NIL, NIL));;


	let result = membro tree 3;;  (* result será true *)

	let result2 = membro tree 10;;  (* result2 será false *)


