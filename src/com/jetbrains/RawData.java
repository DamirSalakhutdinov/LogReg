package com.jetbrains;

public class RawData {
    int[] PassengerId;
    int[] Pclass;
    String[] Sex;
    float[] Age;
    int[] SibSp;
    int[] Parch;
    float[] Fare;
    String[] Embarked;

    public RawData(int length) {
        PassengerId = new int[length];
        Pclass = new int[length] ;
        Sex = new String[length] ;
        Age = new float[length];
        SibSp = new int[length];
        Parch = new int[length];
        Fare = new float[length];
        Embarked = new String[length];
    }
}
