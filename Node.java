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
    public int searchWithinNode (int key, int start, int end) {
        if(keys == null) return -1;
        if(keys.get(start) == key) {
            int index = start;
            while(keys.get(index) == key) {
                index--;
            }
            return index+1;
        }
        if(keys.get(end) == key) {
            int index = end;
            while(keys.get(index) == key) {
                index++;
            }
            return index-1;
        }
        int mid = (start+end)/2;
        if(keys.get(start) < key && key <= mid) {
            return searchWithinNode(key, start + 1, mid);
        }
        if(mid <= key && key < keys.get(end)) {
            return searchWithinNode(key, mid , end - 1);
        }
        // key < keys.get(start) || key > keys.get(end)
        return -1;
    }
    public int searchWithinNode (int key) {
        // returns the first index of the key occurence in the arraylist
        return searchWithinNode(key, 0, numOfKeys);
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
