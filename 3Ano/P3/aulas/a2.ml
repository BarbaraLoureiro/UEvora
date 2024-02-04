type 'a abp =
  | Folha
  | No of 'a abp * 'a * 'a abp

let exemplo_abp : int abp =
  No (
      No (Folha, 2, Folha),
      5,
      No (Folha, 8, Folha)
    )

let rec lookup tree key =
  match tree with
  | Folha -> false
  | No (esquerda, valor, direita) ->
        if key = valor then
          true
        else if key < valor then
          lookup esquerda key
        else
          lookup direita key;;


lookup exemplo 5;;