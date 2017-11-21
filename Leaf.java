package com.ufl;
import java.util.ArrayList;

class Leaf extends Node {
    private ArrayList<String> values;
    private Leaf next;
    public boolean isLeaf() {
        return true;
    }
    Leaf (ArrayList<Double> keys, ArrayList<String> values) {
        super.setKeys(keys);
        this.values = values;
        this.next = null;
    }
    @Override
    public String getValue (int index) {
        return this.values.get(index);
    }
    @Override
    public Leaf getNext () {
        return this.next;
    }
    @Override
    public void setNext(Leaf nxt) {
        this.next = nxt;
    }
    @Override
    public void addVal (int index, String val) {
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