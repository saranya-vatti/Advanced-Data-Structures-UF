package com.company;
import com.company.Node;

import java.util.ArrayList;

class InternalNode extends Node {

    // Number of Keys
    // Arraylist of Keys
    // Arraylist of child pointers to other nodes - may be leaves or internal nodes

    // size = numOfKeys + 1
    private ArrayList<Node> subTreePointer;

    public ArrayList<Node> getSubTreePointer() {
        return this.subTreePointer;
    }

    @Override
    public Node getChildNode(int index) {
        return subTreePointer.get(index);
    }
    @Override
    public Node getLastNode () {
        return subTreePointer.get(subTreePointer.size() - 1);
    }
    public void setSubTreePointer(ArrayList<Node> subTreePointer) {
        this.subTreePointer = subTreePointer;
    }

    InternalNode(ArrayList<Integer> keys, ArrayList<Node> subTreePointer) {
        super(keys);
        this.subTreePointer = subTreePointer;
    }
    public boolean isLeaf() {
        return false;
    }
}