package com.example.cmp354project;

public class singleton {

    private static singleton instance;

    public static singleton getInstance() {
        if (instance == null)
            instance = new singleton();
        return instance;
    }

    private singleton() {
    }

    private int intValue;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
}