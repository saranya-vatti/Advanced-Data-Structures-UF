package com.company;
import com.company.Node;

import java.util.ArrayList;

class Leaf extends Node {
    private ArrayList<Double> values;
    private Leaf prev;
    private Leaf next;
    public ArrayList<Double> getValues() {
        return this.values;
    }
    public void setValues(ArrayList<Double> vArr) {
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
    Leaf (ArrayList<Integer> keys, ArrayList<Double> values) {
        super(keys);
        this.values = values;
        this.prev = null;
        this.next = null;
    }
    @Override
    public double getValue (int index) {
        return this.values.get(index);
    }
    @Override
    public Node getNext () {
        return this.next;
    }
    @Override
    public void addVal (int index, double val) {
        this.values.add(index, val);
    }
}