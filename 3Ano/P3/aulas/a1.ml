let rec membro = function
  |[]->(function _ ->false)
  |x::y -> (function e -> if x=e then true else membro y e);;
  
let exemplo = membro [1;2;3] 2
let exemplo2 =membro [1;2;3] 4

let rec conta = function a -> function
  | [] ->0
  | hd :: tl -> if hd=a then 1+conta a tl else conta a tl;;

let exemplo3 = conta 3 [1;1;2;2;3;3;4;4]

let rec append list1 list2 =
  match list1 with
  | [] -> list2  
  | x :: rest -> x :: append rest list2 ;;

let exemplo_append = append [1; 2] [3; 4];;

let rec soma_lista = 
  function [] -> 0 
      | hd::x -> hd + soma_lista x;;

let exemplo_soma = soma_lista [1;2];;

let rec remove = function a -> function
  | []->[]
  | hd::tl -> if hd=a then remove a tl else hd::(remove a tl);; 

let exemplo_remove = remove 3 [1;2;3;4;3;2;1];;

let rec contagem = function
  [] -> []
  |e::l->(e,1+conta e l)::contagem(remove e l);;

let exemplo_contagem = contagem ['a';'b';'c';'a';'c';'c'];;