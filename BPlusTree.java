package com.company;
import com.company.InternalNode;
import com.company.Node;
import java.util.*;

import java.util.ArrayList;
import java.util.Stack;

class BPlusTree {
    Node root;
    int order;

    BPlusTree(int order) {
        this.root = null;
        this.order = order;
    }

    /**
     * Unit testing "search"
     * @param root Root of the already built B+ Tree
     */
    BPlusTree(Node root) {
        this.root = root;
    }

    /**
     * Function to do a range search for values using keys
     * If we are to search for one key - exact match, keep both start and end same
     * We travel down the tree from the given node till we find the leaf node
     * where the startKey or its closest key resides
     * Then, we will collect all the values of keys that match the range and return
     * If there are none, an empty list (null) is returned
     * @param node Starting node - is an internal node
     * @param startKey - Starting key of the range, both inclusive
     * @param endKey - Ending key of the range, both inclusive
     * @return List of values whose keys match the given range
     */
    public ArrayList<Double> search(Node node, int startKey, int endKey) {
        Node currNode = node;
        ArrayList<Integer> currNodeKeys = currNode.getKeys();
        int index;
        for (index = currNodeKeys.size() - 1; index >= 0; index--) {
            if (currNodeKeys.get(index) <= startKey) break;
        }
        if (currNode.isLeaf()) {
            // Every key in the leaf is greater than start key
            // We will then search the node from the start to find keys that fit range
            if(index == -1) index++;
            ArrayList<Double> values = new ArrayList<>();
            int currKey = currNode.getKey(index);
            while (currKey <= endKey) {
                values.add(currNode.getValue(index));
                index++;
                if (index >= currNode.getNumOfKeys()) {
                    if(currNode.getNext() != null) {
                        currNode = currNode.getNext();
                    } else {
                        break;
                    }
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
        return search(currNode.getChildNode(index + 1), startKey, endKey);
    }

    /**
     * Returns all values associated with the key
     *
     * @param key Key of the values that are needed
     * @return List of all the values associated with the key
     */
    public ArrayList<Double> search(int key) {
        return search(root, key, key);
    }

    /**
     * Returns all values associated with the key
     *
     * @param key1 Starting key of the values that are needed
     * @param key2 Ending key of the values that are needed
     * @return List of all the values for the keys that fall in the range
     */
    public ArrayList<Double> search(int key1, int key2) {
        return search(root, key1, key2);
    }

    public void insert (int key, double value) {
        if(root == null) {
            ArrayList<Integer> leafKeyList = new ArrayList<>();
            leafKeyList.add(key);
            ArrayList<Double> leafValList = new ArrayList<>();
            leafValList.add(value);
            root = new Leaf(leafKeyList, leafValList);
            return;
        }

        // Search for where it's rightful place is, all the while inserting the
        // nodes visited into a stack
        Stack<Node> visited = new Stack<>();
        visited.push(root);
        Node currNode = root;
        while(true) {
            System.out.println(this.toString());
            visited.push(currNode);
            if(currNode.isLeaf() || (key >= currNode.getLeastKey() && key < currNode
                    .getHighestKey())) {
                ArrayList<Integer> currNodeKeys = currNode.getKeys();
                int index;
                for (index = currNodeKeys.size() - 1; index >= 0; index--) {
                    if (currNodeKeys.get(index) <= key) break;
                }
                if (currNode.isLeaf()) {
                    // TODO: the splits
                    currNode.addKey(index + 1, key);
                    currNode.addVal(index + 1, value);
                    break;
                } else {
                    currNode = currNode.getChildNode(index + 1);
                }
            } else if (key < currNode.getLeastKey()) {
                currNode = currNode.getChildNode(0);
            } else {
                // key >= currNode.getHighestKey()
                currNode = currNode.getLastNode();
            }
        }
    }

    @Override
    public String toString() {
        if(root == null) return "";
        int level = 1;
        StringBuilder result = new StringBuilder("");
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while(true) {
            Node parent = qu.remove();
            result.append("Level ");
            result.append(level);
            result.append("-> ");
            if(parent.isLeaf()) {
                for(int i=0;i<parent.getKeys().size();i++) {
                    result.append(parent.getKey(i));
                    result.append(",");
                    result.append(parent.getValue(i));
                    result.append("; ");
                }
                break;
            } else {
                result.append(Arrays.toString(parent.getKeys().toArray()));
                qu.addAll(parent.getChildren());
            }
            level++;
        }
        return result.toString();
    }
}