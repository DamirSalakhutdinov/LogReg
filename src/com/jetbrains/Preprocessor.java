package com.jetbrains;

public class Preprocessor {
    public static float[][] prepare(RawData df, int dataSize, int numOfColumns) {
        float[][] X_test   = new float[dataSize][numOfColumns];

        for (int i = 0; i < dataSize; i++) {
            X_test[i][0] = (float) df.Pclass[i];
            X_test[i][1] = df.Age[i] != -1.0 ? df.Age[i] : df.Age[i - 1];
            X_test[i][2] = df.SibSp[i];
            X_test[i][3] = df.Parch[i];
            X_test[i][4] = df.Fare[i] != -1.0 ? df.Fare[i] : df.Fare[i - 1];
            X_test[i][5] = df.Sex[i].equals("male") ? 1 : 0;
            X_test[i][6] = df.Embarked[i].equals("Q") ? 1 : 0;
            X_test[i][7] = df.Embarked[i].equals("S") ? 1 : 0;
        }
        stdScale(X_test, dataSize, numOfColumns);
        return X_test;
    }
    public static void stdScale(float[][] X, int dataSize, int numOfColumns) {
        float[] means = new float[numOfColumns];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                means[j] += X[i][j];
            }
        }
        float[] sd = new float[numOfColumns];
        for (int i = 0; i < numOfColumns; i++) {
            means[i] /= dataSize;
            for (int j = 0; j < dataSize; j++) {
                sd[i] += (X[j][i] - means[i]) * (X[j][i] - means[i]);
            }
            sd[i] = (float) Math.sqrt(sd[i] / (dataSize - 1));
            for (int j = 0; j < dataSize; j++) {
                X[j][i] = (X[j][i] - means[i]) / sd[i];
            }
        }
    }
}
