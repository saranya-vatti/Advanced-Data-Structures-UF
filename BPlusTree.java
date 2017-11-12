package com.company;
import com.company.InternalNode;
import com.company.Node;

import java.util.ArrayList;

class BPlusTree {
    InternalNode root;

    BPlusTree(InternalNode root) {
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
}