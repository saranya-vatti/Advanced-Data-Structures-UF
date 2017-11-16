package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Node {
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
    }
    public boolean isLeaf() {
        return true;
    }
    public int getNumOfKeys() {
        // numOfKeys will be 2 or 3 or 4 for 2-3-4 trees (B trees of order 4)
        return keys.size();
    }
    public Node getChildNode(int index) {
        return null;
    }
    public int getLeastKey () {
        return keys.get(0);
    }
    public int getHighestKey () {
        return keys.get(keys.size() - 1);
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
    public void addVal (int index, double val) {
    }
    public void addKey (int index, int key) {
        this.keys.add(index, key);
    }
    public ArrayList<Node> getChildren() {
        return null;
    }
    public void addChildNode (int index, Node node) {
    }
    public void removeKey(int index) {
        keys.remove(index);
    }
    public void removeVal(int index) {
    }
    public void removeChildNode(int index) {
    }
}
