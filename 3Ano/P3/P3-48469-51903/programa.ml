(* Definição dos tipos *)
type piece = I | S | O | T

(* Matrizes representando a forma inicial de cada peça do Tetris *)
let matriz_i =
  [|
    [| true; true; true; true |];
  |]

let matriz_s =
  [|
    [| false; true; true;|];
    [| true; true; false;|];
  |]

let matriz_o =
  [|
    [| true; true |];
    [| true; true |];
  |]

let matriz_t =
  [|
    [| true; true; true |];
    [| false; true; false |];
  |]

(* Função para rotacionar uma matriz (representando uma peça) *)
let rotate matrix = 
  let n = Array.length matrix in
  let m = Array.length matrix.(0) in
  let result = Array.make_matrix m n false in
  (* Itera sobre a matriz original e preenche a matriz resultante com os elementos rotacionados *)
  for i = 0 to n - 1 do
    for j = 0 to m - 1 do
      result.(j).(n - i - 1) <- matrix.(i).(j)
    done
  done;
  result

(* Função para verificar se uma peça cabe no tabuleiro sem sobreposição ou sair dos limites *)
let fits board matrix x y =
  (* Obtém a altura e a largura da matriz *)
  let height = Array.length matrix
  and width = Array.length matrix.(0) in
  (* Função recursiva para verificar se a peça cabe *)
  let rec check i j =
    (* Se a altura for maior ou igual à altura da matriz, a peça cabe *)
    if i >= height then true
    (* Se a largura for maior ou igual à largura da matriz, verifica a próxima linha *)
    else if j >= width then check (i + 1) 0
    else
      (* Verifica se a posição atual da matriz é verdadeira e se a posição correspondente no tabuleiro está vazia *)
      let ni = x + i and nj = y + j in
      if matrix.(i).(j) then
        (ni < Array.length board && nj < Array.length board.(0) && ni >= 0 && nj >= 0 && board.(ni).(nj) = None) && check i (j + 1)
      else
        check i (j + 1)
  in
  (* Inicia a verificação a partir do elemento (0,0) *)
  check 0 0
  
(* Função para imprimir o estado atual do tabuleiro *)
let print_board board =
  Array.iter (fun row ->
    Array.iter (fun cell ->
      match cell with
      | None -> print_string "_"
      | Some I -> print_string "I"
      | Some S -> print_string "S"
      | Some O -> print_string "O"
      | Some T -> print_string "T"
    ) row;
    print_newline ()
  ) board;
  print_newline ()

(* Processa cada movimento: rotaciona a peça, verifica se encaixa, e atualiza o tabuleiro *)
let process_move board (piece, rotations, right_shift) =
  let matrix = match piece with
    | I -> matriz_i
    | S -> matriz_s
    | O -> matriz_o
    | T -> matriz_t
  in
  let rotated_matrix = ref matrix in
  for _ = 1 to rotations do
    rotated_matrix := rotate !rotated_matrix
  done;
  let height = Array.length !rotated_matrix
  and width = Array.length !rotated_matrix.(0) in
  let y_position = ref 0 in
  (* Encontra a posição mais baixa possível para a peça (se houver) *)
  while !y_position + height <= Array.length board &&
        fits board !rotated_matrix !y_position right_shift do
    incr y_position
  done;
  decr y_position;
  (* Se a peça couber, atualiza o tabuleiro *)
  if !y_position >= 0 then begin
    for i = 0 to height - 1 do
      for j = 0 to width - 1 do
        if !rotated_matrix.(i).(j) then
          board.(!y_position + i).(j + right_shift) <- Some piece
      done
    done;
    print_board board;  (* Adicionado para imprimir após cada jogada *)
    board
  end else
    failwith "Movimento inválido"
    
  
(* Função principal do jogo: itera sobre as jogadas, processa cada uma e determina se o jogo é válido *) 
let paktris moves =
  let n = 4 and m = 4 in
  let board = Array.make_matrix n m None in
  try
    ignore (List.fold_left process_move board moves);
    true
  with
  | Failure _ -> false
    
(* Testa o jogo com dois conjuntos de jogadas e imprime os resultados *)
let () =
  print_endline (string_of_bool (paktris [(I, 0, 0); (I, 0, 0); (O, 0, 2); (O, 0, 0)]));
  print_endline (string_of_bool (paktris [(I, 1, 0); (S, 1, 1); (O, 0, 1)]))
