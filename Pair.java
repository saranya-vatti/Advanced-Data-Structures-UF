package com.company;

class Pair {
    double key;
    String val;
    Pair(double key, String val) {
        this.key = key;
        this.val = val;
    }

    @Override
    public String toString() {
        return "(" + key + "," + val + ")";
    }
}