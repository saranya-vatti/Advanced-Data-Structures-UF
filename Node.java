package com.company;
import java.util.ArrayList;
import java.util.Collections;

class Node {
    // size = numOfKeys
    private ArrayList<Double> keys;

    public ArrayList<Double> getKeys() {
        return this.keys;
    }
    public double getKey(int index) {
        return this.keys.get(index);
    }
    Node (ArrayList<Double> keys) {
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
    public double getLeastKey () {
        return keys.get(0);
    }
    public double getHighestKey () {
        return keys.get(keys.size() - 1);
    }
    public Node getLastNode () {
        return null;
    }
    public String getValue (int index) {
        return "Null";
    }
    public Node getNext () {
        return null;
    }
    public void addVal (int index, String val) {
    }
    public void addKey (int index, double key) {
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
