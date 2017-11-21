package com.ufl;
import java.util.ArrayList;

class Node {
    // size = numOfKeys
    private ArrayList<Double> keys;

    /**
     * START - common methods
     * Methods pertaining to the keys that are present in the node
     * Methods that need to implemented by both Lead and InternalNode but can be
     * commonly implemented here.
     */

    public ArrayList<Double> getKeys() {
        return this.keys;
    }
    public double getKey(int index) {
        return this.keys.get(index);
    }
    public void setKeys(ArrayList<Double> keys) {
        this.keys = keys;
    }
    public double getLeastKey () {
        return keys.get(0);
    }
    public double getHighestKey () {
        return keys.get(keys.size() - 1);
    }
    public void removeKey(int index) {
        keys.remove(index);
    }
    public int getNumOfKeys() {
        // numOfKeys will be 2 or 3 or 4 for 2-3-4 trees (B trees of order 4)
        return keys.size();
    }
    public void addKey (int index, double key) {
        this.keys.add(index, key);
    }

    /**
     * END - common methods
     * Methods pertaining to the keys that are present in the node
     */

    /**
     * START - Null methods that will be overriden and implemented by InternalNode
     */
    public Node getChildNode(int index) {
        return null;
    }
    public Node getLastNode () {
        return null;
    }
    public void removeChildNode(int index) {
    }
    public ArrayList<Node> getChildren() {
        return null;
    }
    public void addChildNode (int index, Node node) {
    }
    /**
     * END - Null methods that will be overriden and implemented by InternalNode
     */

    /**
     * START - Null methods that will be overriden and implemented by Leaf
     */
    public String getValue (int index) {
        return "Null";
    }
    public Leaf getNext () {
        return null;
    }
    public void setNext (Leaf next) {
    }
    public void addVal (int index, String val) {
    }
    public void removeVal(int index) {
    }
    /**
     * END - Null methods that will be overriden and implemented by Leaf
     */

    /**
     * Method that is implemented differently by both InternalNode and Leaf.
     * @return {boolean} Whether the current node is a leaf.
     */
    public boolean isLeaf() {
        return true;
    }
}
