package com.jetbrains;

import java.io.*;
import java.util.Scanner;

public class Main {
    static int dataSize = 418;
    static int numOfColumns = 8;
    static String dataPath = "res/test.csv";
    static String coefPath = "res/coef.txt";

    public static void main(String[] args) {
        float[] coef = new float[numOfColumns + 1];
        try {
            Scanner scanner = new Scanner(new File(coefPath));
            for (int i = 0; i < coef.length; i++) {
                coef[i] = scanner.nextFloat();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        RawData df = DataReader.read(dataPath, dataSize);
        float[][] X_test = Preprocessor.prepare(df, dataSize, numOfColumns);
        int[] yPred = Predictor.predict(X_test, coef, dataSize, numOfColumns);

        String result = "PassengerId,Survived";
        for (int i = 0; i < dataSize; i++) {
            result += "\n" + String.valueOf(df.PassengerId[i]) +
                    "," + String.valueOf(yPred[i]);
        }

        try {
            FileWriter myWriter = new FileWriter("submission.csv");
            myWriter.write(result);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
