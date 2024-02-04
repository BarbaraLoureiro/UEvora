class NaiveBayesUevora:
    def __init__(self,alph=0):
        '''Método especial utilizado neste caso para guardar os parametros da classe.
        '''
        self.alph=alph #Guardar o parametro alfa
    def fit(self,X_train,y_train):
        '''Esta função fit(_) neste caso serve para guardar a informação de train, ou seja, a informação
        utilizada para fazer as previsões.
        '''
        X_train.columns=[i.strip() for i in X_train.columns]#Esta linha serve para "normalizar" o nome das colunas que por vezes podem variar, quando os nomes das colunas são diferente nos ficheiros de train e test 
        self.X_train=X_train#Guardar informação dos previsores
        self.y_train=y_train#Guardar informação dos valores de previsão
    def predict(self,X_test):
        '''Esta função serve para prever as classes dos atributos que estão em X_test, com base nas 
        informação guardada no fit e retirada do self. A previsão é feita consuante a suavização presente
        no valor de alfa.'''
        X_test.columns=[i.strip() for i in X_test.columns]
        X_train=self.X_train
        y_train=self.y_train
        alph=self.alph
        df=X_train.join(y_train)
        dfs={}
        prob_labels={}
        labels=y_train.unique()
        for e in labels:
            prob_labels[e]=(y_train.value_counts()[e]+alph)/(y_train.count()+alph*len(y_train.unique()))
        dic_prob={}
        indexes=X_test.index
        predicts={}
        for t in labels:
                dfs[t]=df[df[y_train.name]==t]
        for e in range(len(X_test)):
            prob=list(prob_labels.values())[:]
            for t in X_train.columns:
                valor=X_test.iloc[e][t]
                if list(df[t]).count(valor)!=0 or alph!=0:# apenas entra se a contagem do valor no atributo for diferente de 0 e o alpha for diferente de zero para não anular a probabilidade
                    for chave,x in zip(dfs.keys(),range(len(dfs.keys()))):
                        prob[x]*=(list(dfs[chave][t]).count(valor)+alph)/(len(dfs[chave])+alph*len(X_train[t].unique()))
            for i in range(len(dfs.keys())):
                if prob[i]==max(prob):
                    predicts[indexes[e]]=list(prob_labels.keys())[i]
        return pd.Series(data=predicts, index=indexes,name=y_train.name)
         
    def accuracy_score(self,X_test,y_test):
        '''Esta função dá o valor da exatidão das previsões. Ou seja os valores que foram bem previstos
        a dividir por todos os valores.
        Exatidão = Bem Classificado/(Bem Classificados + Mal Classificados)
        '''
        y_pred=NaiveBayesUevora.predict(self,X_test)
        Bem=0
        for e in range(len(y_test)):
            if y_pred.iloc[e]==y_test.iloc[e]:
                Bem+=1
        return(Bem/len(y_test))
    def precision_score(self,X_test,y_test):
        '''Esta função dá o valor da precisão das previsões. ou seja, os valores que foram bem classificados
        como positivo, os verdadeiros positivos, a dividir pelos que foram classificados como
        positivos mas que foram mal classificados, os falsos postivos, a sumar aos verdadeiros positivos.
        Precisão = TP/(TP+FP).
        Caso a soma dos valores TP+FP for 0, a precisão é nula
        '''
        scores=[]
        TP=0
        FP=0
        y_pred=NaiveBayesUevora.predict(self,X_test)
        for value in y_test.unique():
            for e in range(len(y_test)):
                if y_pred.iloc[e]==y_test.iloc[e]==value:
                    TP+=1
                elif y_test.iloc[e]!=y_pred.iloc[e] and y_pred.iloc[e]==value:
                    FP+=1
            if FP+TP==0:
                scores.append(0)
            else:
                scores.append(TP/(TP+FP))
        return(sum(scores)/len(scores))
    
