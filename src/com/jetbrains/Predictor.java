package com.jetbrains;

public class Predictor {
    public static int[] predict(float[][] X, float[] coef,
                                int dataSize, int numOfColumns) {
        int[] yPred = new int[dataSize];
        for (int i = 0; i < dataSize; i++) {
            float s = 0;
            for (int j = 0; j < numOfColumns; j++) {
                s += coef[j] * X[i][j];
            }
            s += coef[numOfColumns];
            yPred[i] = s >= 0 ? 1 : 0;
        }
        return yPred;
    }
}
