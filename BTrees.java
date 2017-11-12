package com.company;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
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
}
class BPlusTree {
    InternalNode root;
    BPlusTree() {
        root = null;
    }
    public ArrayList<Double> search(Node node, int startKey, int endKey) {
        Node currNode = node;
        if (currNode.isLeaf()) {
            // TODO: verify that key < currNode.getLeastKey() and key > currNode
            // .getHighestKey() is not posibble
            ArrayList<Double> values = new ArrayList<>();
            int index = currNode.searchWithinNode(startKey);
            int currKey = currNode.getKey(index);
            while(currKey >= startKey && currKey <= endKey) {
                values.add(currNode.getValue(index));
                index++;
                if(index >= currNode.getNumOfKeys()) {
                    currNode = currNode.getNext();
                    index = 0;
                }
                currKey = currNode.getKey(index);
            }
            return values;
        }
        if (startKey < currNode.getLeastKey()) {
            return search(currNode.getChildNode(0), startKey, endKey);
        }
        if (startKey > currNode.getHighestKey()) {
            return search(currNode.getLastNode(), startKey, endKey);
        }
        ArrayList<Integer> currNodeKeys = currNode.getKeys();
        int index;
        for(index = currNodeKeys.size() - 1;index >= 0; index--) {
            if (currNodeKeys.get(index) <= startKey) break;
        }
        return search(currNode.getChildNode(index+1), startKey, endKey);
    }

    /**
     * Returns all values associated with the key
     * @param key Key of the values that are needed
     * @return List of all the values associated with the key
     */
    public ArrayList<Double> search(int key) {
        return search(root, key, key);
    }

    /**
     * Returns all values associated with the key
     * @param key1 Starting key of the values that are needed
     * @param key2 Ending key of the values that are needed
     * @return List of all the values for the keys that fall in the range
     */
    public ArrayList<Double> search(int key1, int key2) {
        return search(root, key1, key2);
    }
}
public class Main {
    private static int m;

    private static void initialize(int m) {
        // TODO - finish the function
    }

    private static void insert(double key, int value) {
        // TODO - finish the function
        // Search for the item - search should return [Node node, boolean isPresent}
        // Case 1 - insert into underfull leaf
        // All we need to do is insert the pair into the arraylist of pairs of
        // the leaf node. children will be null
        // Case 2 - Leaf gets full
        // Split the node: Left half pairs stay in the old leaf, Right half pairs go
        // to new node. In between key has to go to the parent.
        // Insert the in between key into parent, then order them properly,
        // On the way down, store the nodes in stack, on the way up, pop them and
        // update them
        System.out.println("Inserting Key : " + key + "; value : " + value);
    }

    /**
     * Returns all values associated with the key
     * @param key {double} Key we need to search for
     * @return values {int[]} All the values associated with the key.
     */
    private static int[] search(double key) {
        // TODO - finish the function
        // TODO - finish the function
        // TODO - print to a file
        int[] values = {};
        System.out.println("Searching for key : " + key);
        return values;
    }

    /**
     * Returns all values associated with the key
     * @param key1 {double} Starting key
     * @param key2 {double} Ending key
     * @return values {int[]} All the values between the two given keys
     */
    private static int[] search(double key1, double key2) {
        // TODO - finish the function
        // TODO - print to a file
        int[] values = {};
        System.out.println("Searching between keys : " + key1 + " and " + key2);
        return values;
    }

    public static void main(String[] args) {
        String fileName = args[0];
        String s;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((s = bufferedReader.readLine()) != null) {
                // TODO: use regex
                if(s.indexOf("Insert(") != -1) {
                    double key = Double.parseDouble(s.split("Insert\\(")[1].split(",")[0]);
                    int val = Integer.parseInt(s.split("Value")[1].split("\\)")[0]);
                    insert(key, val);
                } else if(s.indexOf("Search(") != -1) {
                    if(s.indexOf(",") == -1) {
                        double key = Double.parseDouble(s.split("Search\\(")[1].split("\\)")[0]);
                        search(key);
                    } else {
                        double key1 = Double.parseDouble(s.split("Search\\(")[1].split(",")[0]);
                        double key2 = Double.parseDouble(s.split("Search\\(")[1].split(",")[1].split("\\)")[0]);
                        search(key1, key2);
                    }
                }
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
}