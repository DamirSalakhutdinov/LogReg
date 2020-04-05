package com.jetbrains;

import java.io.BufferedReader;
import java.io.FileReader;

public class DataReader {
    public static RawData read(String path, int dataSize)
    {
        RawData df = new RawData(dataSize);
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            String[] s;
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
            System.err.println(e.getMessage());
        }
        return df;
    }
}
