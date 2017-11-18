package com.company;
import com.company.Node;

import java.util.ArrayList;

class Leaf extends Node {
    private ArrayList<Integer> values;
    private Leaf prev;
    private Leaf next;
    public ArrayList<Integer> getValues() {
        return this.values;
    }
    public void setValues(ArrayList<Integer> vArr) {
        this.values = vArr;
    }
    public Leaf getNextLeaf() {
        return this.next;
    }
    public void setNextLeaf(Leaf nxt) {
        this.next = nxt;
    }
    public Leaf getPreviousLeaf() {
        return this.prev;
    }
    public void setPreviousLeaf(Leaf prv) {
        this.prev = prv;
    }
    public boolean isLeaf() {
        return true;
    }
    Leaf (ArrayList<Double> keys, ArrayList<Integer> values) {
        super(keys);
        this.values = values;
        this.prev = null;
        this.next = null;
    }
    @Override
    public int getValue (int index) {
        return this.values.get(index);
    }
    @Override
    public Node getNext () {
        return this.next;
    }
    @Override
    public void addVal (int index, int val) {
        this.values.add(index, val);
    }
    @Override
    public void removeVal(int index) {
        this.values.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for(int i=0;i<super.getKeys().size();i++) {
            result.append(super.getKey(i));
            result.append(",");
            result.append(this.getValue(i));
            result.append("; ");
        }
        return result.toString();
    }
}