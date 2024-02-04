type 'a m_lista = V | NV of 'a * 'a m_lista;;

let rec comprimento = function
  | V -> 0
  | NV (_, l) -> 1 + comprimento l;;

  let lista1 = NV (1, NV (2, NV (3, V)));;
  let exemplo = comprimento lista1;;
