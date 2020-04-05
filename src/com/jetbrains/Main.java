package com.jetbrains;

import java.io.*;
import java.util.Scanner;

public class Main {
    static int dataSize = 418;
    static int numOfColumns = 9;
    public static void main(String[] args) {
        Scanner scanner = null;
        float[] coef = null;
        try {
            scanner = new Scanner(new File("/home/sdild/Kaggle/Titanic/coef.txt"));
            coef = new float[numOfColumns];
            for (int i = 0; i < coef.length; i++) {
                coef[i] = scanner.nextFloat();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        RawData df = new RawData(dataSize);
        String columnNames = null;
        try(BufferedReader br = new BufferedReader(new FileReader("/home/sdild/Kaggle/Titanic/test.csv")) ) {
            columnNames = br.readLine();
            String line;
            String[] s = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                s = line.split(",");
                df.PassengerId[i] = Integer.parseInt(s[0].trim());
                df.Pclass[i] = Integer.parseInt(s[1].trim());
                df.Sex[i] = s[4];
                df.Age[i] = s[5].isEmpty() ? (float) -1 : Float.parseFloat(s[5].trim());
                df.SibSp[i] = Integer.parseInt(s[6].trim());
                df.Parch[i] = Integer.parseInt(s[7].trim());
                df.Fare[i] = s[9].isEmpty() ? (float) -1 : Float.parseFloat(s[9].trim());
                df.Embarked[i] = s[11];
                i++;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage()); // or log error
        }

        float[][] X_test   = new float[dataSize][numOfColumns];
        float[] means = new float[numOfColumns];

        for (int i = 0; i < dataSize; i++) {
            X_test[i][0] = (float) df.Pclass[i];
            if (df.Age[i] != -1.0) {
                X_test[i][1] = df.Age[i ];
            } else {
                X_test[i][1] = df.Age[i - 1];
            }
            X_test[i][2] = df.SibSp[i];
            X_test[i][3] = df.Parch[i];
            if (df.Fare[i] != -1.0) {
                X_test[i][4] = df.Fare[i];
            } else {
                X_test[i][4] = df.Fare[i - 1];
            }
            if (df.Sex[i].equals("male")) {
                X_test[i][5] = 1;
            } else {
                X_test[i][5] = 0;
            }
            if (df.Embarked[i].equals("Q")) {
                X_test[i][6] = 1;
            } else if (df.Embarked[i].equals("S")) {
                X_test[i][7] = 1;
            }
            X_test[i][8] = 1;
            for (int j = 0; j < numOfColumns; j++) {
                means[j] += X_test[i][j];
            }
        }

        float[] sd = new float[numOfColumns];

        for (int i = 0; i < numOfColumns; i++) {
            means[i] /= dataSize;
            for (int j = 0; j < dataSize; j++) {
                sd[i] += (X_test[j][i] - means[i]) * (X_test[j][i] - means[i]);
            }
            sd[i] = (float) Math.sqrt(sd[i] / (dataSize - 1));
        }

        String result = "PassengerId,Survived";
        int[] yPred = new int[dataSize];
        for (int i = 0; i < dataSize; i++) {
            float s = 0;
            for (int j = 0; j < numOfColumns - 1; j++) {
                s += coef[j] * (X_test[i][j] - means[j]) / sd[j];
            }
            s += coef[numOfColumns - 1];
            yPred[i] = s >= 0 ? 1 : 0;
            result += "\n" + String.valueOf(df.PassengerId[i]) + "," + String.valueOf(yPred[i]);
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
