
import pandas as pd
import pickle
from sklearn.ensemble import GradientBoostingClassifier

class modelo:
    
    def predict(X_test):
        mod=pickle.load(open('modelo','rb'))
        y_pred=mod.predict(X_test)

        return y_pred
    
    def predict_2(X_test):
        X_test_2vars=X_test[['Y2s2_complete', 'Y4s2_complete']]
        mod_2vars=pickle.load(open('modelo_2','rb'))
        y_pred_2=mod_2vars.predict(X_test_2vars)

        return y_pred_2
    