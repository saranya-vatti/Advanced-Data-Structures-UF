package com.company;
import java.util.ArrayList;
import java.util.Collections;

class Node {
    // numOfKeys will be 2 or 3 or 4 for 2-3-4 trees (B trees of order 4)
    private int numOfKeys;

    // size = numOfKeys
    private ArrayList<Integer> keys;

    public ArrayList<Integer> getKeys() {
        return this.keys;
    }
    public int getKey(int index) {
        return this.keys.get(index);
    }
    public void setKeys(ArrayList<Integer> keys) {
        this.keys = keys;
    }
    Node (ArrayList<Integer> keys) {
        Collections.sort(keys);
        this.keys = keys;
        this.numOfKeys = keys.size();
    }
    public boolean isLeaf() {
        return false;
    }
    public int getNumOfKeys() {
        return this.numOfKeys;
    }
    public Node getChildNode(int index) {
        return null;
    }
    public int getLeastKey () {
        return keys.get(0);
    }
    public int getHighestKey () {
        return keys.get(numOfKeys - 1);
    }
    public Node getLastNode () {
        return null;
    }
    public double getValue (int index) {
        return Double.NaN;
    }
    public Node getNext () {
        return null;
    }
}
