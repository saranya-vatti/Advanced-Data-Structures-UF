package com.company;
import com.company.InternalNode;
import com.company.Node;

import java.util.ArrayList;

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
            while (currKey >= startKey && currKey <= endKey) {
                values.add(currNode.getValue(index));
                index++;
                if (index >= currNode.getNumOfKeys()) {
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
        for (index = currNodeKeys.size() - 1; index >= 0; index--) {
            if (currNodeKeys.get(index) <= startKey) break;
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