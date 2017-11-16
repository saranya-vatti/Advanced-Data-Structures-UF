package com.company;
import com.company.Node;
import java.util.*;
import java.util.ArrayList;

class InternalNode extends Node {

    // Number of Keys
    // Arraylist of Keys
    // Arraylist of child pointers to other nodes - may be leaves or internal nodes

    // size = numOfKeys + 1
    private ArrayList<Node> children;

    public ArrayList<Node> getSubTreePointer() {
        return this.children;
    }

    @Override
    public Node getChildNode(int index) {
        return children.get(index);
    }
    @Override
    public Node getLastNode () {
        return children.get(children.size() - 1);
    }
    public ArrayList<Node> getChildren() {
        return this.children;
    }

    InternalNode(ArrayList<Integer> keys, ArrayList<Node> subTreePointer) {
        super(keys);
        this.children = subTreePointer;
    }
    public boolean isLeaf() {
        return false;
    }

    @Override
    public void addChildNode(int index, Node node) {
        children.add(index, node);
    }

    @Override
    public void removeChildNode(int index) {
        this.children.remove(index);
    }

    @Override
    public String toString() {
        return "IN : " + Arrays.toString(super.getKeys().toArray());
    }
}