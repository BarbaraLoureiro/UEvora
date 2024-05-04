% estado_inicial(e(posicoes),numero galinhas, numero patos).

estado_inicial(e([[p,g,p,g],[g,p,g,p],[p,p,g,g]],6,6)).

terminal(e(_,NG,NP)):-6 is NG+NP.

valor(e(_,G,P),1,_):- G>P.
valor(e(_,G,P),0,_):- G=P.
valor(e(_,G,P),-1,_):- G<P.

op1(e([[O|R]|L],6,6),corta([1,1]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).

op2(e([[O|R]|L],6,6),corta([1,2]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).

op3(e([[O|R]|L],6,6),corta([1,3]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).

op4(e([[O|R]|L],6,6),corta([2,1]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).

op5(e([[O|R]|L],6,6),corta([2,2]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).

op6(e([[O|R]|L],6,6),corta([2,3]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).

op7(e([[O|R]|L],6,6),corta([3,1]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).

op8(e([[O|R]|L],6,6),corta([3,2]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).

op9(e([[O|R]|L],6,6),corta([3,3]),e([R|L],NG,NP)):-(O==p, NG1=NG, NP1 is NP-1;
                                                    O==g, NG1 is NG-1, NP1=NP).