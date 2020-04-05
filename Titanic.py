#!/usr/bin/env python

import pandas as pd
df = pd.read_csv('res/train.csv')

drop_columns = ['PassengerId', 'Survived', 'Name', 'Ticket', 'Cabin']

y = df.Survived
X = df.drop(drop_columns, axis=1)

X.Age = X.Age.fillna(method='ffill')

import numpy as np
from sklearn.impute import SimpleImputer
imp_mf = SimpleImputer(strategy = 'most_frequent')
X.Embarked = imp_mf.fit_transform(X.Embarked.values.reshape(-1, 1))

from sklearn.preprocessing import OneHotEncoder
enc = OneHotEncoder(sparse=False, drop='first')
oh_cols = enc.fit_transform(X.loc[:,['Sex', 'Embarked']])

X = X.drop(['Sex', 'Embarked'], axis = 1)

oh_cols = pd.DataFrame(oh_cols, columns=['male', 'Q', 'S'], dtype=int)
X = pd.concat([X, oh_cols], axis = 1)

from sklearn.preprocessing import StandardScaler
scaler = StandardScaler()
X = scaler.fit_transform(X)

from sklearn.linear_model import LogisticRegression
log_reg = LogisticRegression(C = 0.005)
log_reg.fit(X, y)

result = ' '.join([str(coef) for coef in log_reg.coef_[0]] + [str(log_reg.intercept_[0])])
f = open("res/coef.txt", "w")
f.write(result)
f.close()

