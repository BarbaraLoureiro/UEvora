% estado_inicial(AgenteX, AgenteY, MaquinaX, MaquinaY, ObjetosPendentes).
%estado_inicial(e(2, 1, 2, 2, [e(2, 4)])).
estado_inicial(e(2, 3, 2, 4, [e(2, 4)])).


% estado_final(AgenteX, AgenteY, MaquinaX, MaquinaY, ObjetosColetados).
estado_final(e(_, _, 2, 7, [])).


%posicao_bloqueios(c,l)
posicao_bloqueios(e(1,6)).
posicao_bloqueios(e(3,6)).
posicao_bloqueios(e(4,2)).
posicao_bloqueios(e(4,3)).
posicao_bloqueios(e(4,4)).
posicao_bloqueios(e(4,7)).
posicao_bloqueios(e(7,6)).



% op(EstadoAtual, Operador, EstadoSeguinte, Custo).
op(e(Ax, Ay, Cx, Cy, ObjetosPendentes), pegar_objeto, e(Ax, Ay, Cx, Cy, NovosObjetosPendentes), 1) :-
    select(e(Cx, Cy), ObjetosPendentes, NovosObjetosPendentes).



%op(Estado_act,operador,Estado_seg,Custo)
op(e(X,Y,Xc,Yc,O),cima,e(X,Y1,Xc,Y1c,O),1) :-
    Y1 is Y+1,
    (X =:= Xc , Y1 =:= Yc),
    Y1c is Yc+1,
    Y1c >=1, Y1c =< 7, Xc >= 1, Xc =< 7,
    \+ posicao_bloqueios(e(Xc,Y1c)).


op(e(X,Y,Xc,Yc,O),cima,e(X,Y1,Xc,Y1c,O),1) :-
    Y1 is Y+1,
    \+ (X =:= Xc , Y1 =:= Yc),
    Y1c is Yc,
    Y1 >=1, Y1 =< 7, X >= 1, X =< 7,
    \+ posicao_bloqueios(e(X,Y1)).


op(e(X,Y,Xc,Yc,O),direita,e(X1,Y,X1c,Yc,O),1) :-
    X1 is X+1,  
    (X1 =:= Xc , Y =:= Yc),
    X1c is Xc+1,
    X1c >= 1, X1c =< 7, Yc >= 1, Yc =< 7,
    \+ posicao_bloqueios(e(X1c,Yc)).


op(e(X,Y,Xc,Yc,O),direita,e(X1,Y,X1c,Yc,O),1) :-
    X1 is X+1,
    \+ (X1 =:= Xc , Y =:= Yc),
    X1c is Xc,
    X1 >= 1, X1 =< 7, Y >= 1, Y =< 7,
    \+ posicao_bloqueios(e(X1,Y)).


op(e(X,Y,Xc,Yc,O),baixo,e(X,Y1,Xc,Y1c,O),1) :-
    Y1 is Y-1,
    (X =:= Xc , Y1 =:= Yc),
    Y1c is Yc-1,
    Y1c >= 1, Y1c =< 7, Xc >= 1, Xc =< 7,
    \+ posicao_bloqueios(e(Xc,Y1c)).
       

op(e(X,Y,Xc,Yc,O),baixo,e(X,Y1,Xc,Y1c,O),1) :-
    Y1 is Y-1,
    \+ (X =:= Xc , Y1 =:= Yc),
    Y1c is Yc,
    Y1 >= 1, Y1 =< 7, X >= 1, X =< 7,
    \+ posicao_bloqueios(e(X,Y1)).
            

op(e(X,Y,Xc,Yc,O),esquerda,e(X1,Y,X1c,Yc,O),1) :-
    X1 is X-1,
    (X1 =:= Xc , Y =:= Yc),   
    X1c is Xc-1,
    X1c >= 1, X1c =< 7, Yc >= 1, Yc =< 7,
    \+ posicao_bloqueios(e(X1c,Yc)).
    

op(e(X,Y,Xc,Yc,O),esquerda,e(X1,Y,X1c,Yc,O),1) :-
    X1 is X-1,
    \+ (X1 =:= Xc , Y =:= Yc),
    X1c is Xc,
    X1 >= 1, X1 =< 7, Y >= 1, Y =< 7,
    \+ posicao_bloqueios(e(X1,Y)).
            
%Heurísticas
% Heurística que considera a distância até a posição final e os objetos pendentes para coleta
h(e(_,_,Xi,Yi,ObjetosPendentes),R):-
    estado_final(e(_,_,Xf,Yf,_)),
    Distancia is abs(Xi-Xf) + abs(Yi-Yf), % Distância de Manhattan até a posição final
    length(ObjetosPendentes,Length), % Número de objetos ainda a serem coletados
    R is Distancia + Length. % A heurística é a soma da distância até a posição final e o número de objetos pendentes
