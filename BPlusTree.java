package com.company;
import com.company.InternalNode;
import com.company.Node;
import java.util.*;

import java.util.ArrayList;
import java.util.Stack;

class BPlusTree {
    private Node root;
    private int order;
    private int MAX_NODE_SIZE;
    private boolean isDebugMode = false;

    private void debug(String s) {
        if(isDebugMode) System.out.println(s);
    }

    BPlusTree(int order) {
        this.root = null;
        this.order = order;
        this.MAX_NODE_SIZE = order - 1;
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
        Stack<Integer> indexes= new Stack<>();
        indexes.push(-1);
        Node currNode = root;
        while(currNode != null) {
            debug("1 : " + this.toString());
            visited.push(currNode);
            if(currNode.isLeaf() || (key >= currNode.getLeastKey() && key < currNode
                    .getHighestKey())) {
                int index;
                for (index = currNode.getKeys().size() - 1; index >= 0; index--) {
                    if (currNode.getKeys().get(index) < key) break;
                }
                debug("2 : " + currNode.toString());
                debug("3 : " + index);
                if (currNode.isLeaf()) {
                    currNode.addKey(index + 1, key);
                    currNode.addVal(index + 1, value);
                    debug("4 : " + currNode.toString());
                    currNode = visited.pop();
                    debug("5 : " + currNode.toString());
                    debug("6 : " + currNode.getNumOfKeys());
                    debug("7 MAX_NODE_SIZE : " + MAX_NODE_SIZE);
                    while(currNode.getNumOfKeys() > MAX_NODE_SIZE) {
                        debug("8 : " + this.toString());
                        ArrayList<Integer> keyList = new ArrayList<>();
                        ArrayList<Double> valList = new ArrayList<>();
                        ArrayList<Node> nodeList = new ArrayList<>();
                        for(int i=(currNode.getKeys().size()/2);i<currNode.getKeys().size();) {
                            keyList.add(currNode.getKey(i));
                            currNode.removeKey(i);
                            if (currNode.isLeaf()) {
                                valList.add(currNode.getValue(i));
                                currNode.removeVal(i);
                            } else {
                                nodeList.add(currNode.getChildNode(i));
                                currNode.removeChildNode(i);
                            }
                        }
                        debug("9 Going to move keys : " + Arrays
                                .toString(keyList.toArray()));
                        if (currNode.isLeaf()) debug("10 Going to move " +
                                "values : " + Arrays.toString(valList.toArray()));
                        debug("11 : " + this.toString());
                        Node parent;
                        int parentIndex;
                        if(visited.isEmpty()) {
                            // We need to create a new parent as the root itself is
                            // maxed out
                            ArrayList<Integer> newRootKeys = new ArrayList<>();
                            ArrayList<Node> newRootChildren = new ArrayList<>();
                            newRootChildren.add(currNode);
                            parent = new InternalNode(newRootKeys, newRootChildren);
                            this.root = parent;
                            parentIndex = 0;
                        } else {
                            parent = visited.pop();
                            parentIndex = indexes.pop();
                        }

                        if (currNode.isLeaf()) {
                            // We create a new leaf and add the pointer from the
                            // parent internal node to the new leaf at the correct
                            // index
                            Leaf newLeaf = new Leaf(keyList, valList);
                            parent.addChildNode(parentIndex+1, newLeaf);

                            // We have removed all the keys with mid+1. So the last
                            // element remaining in the node's keylist will be the
                            // earlier mid key
                            parent.addKey(parentIndex, newLeaf.getKey(0));
                            debug("12 : " + parent.toString());
                        } else {
                            // We create a new internal node and add the pointer from
                            // the parent internal node to the new internal node at
                            // the correct index
                            InternalNode newNode = new InternalNode(keyList, nodeList);
                            parent.addChildNode(index, newNode);

                            // We have removed all the keys with mid+1. So the last
                            // element remaining in the node's keylist will be the
                            // earlier mid key
                            parent.addKey(parentIndex, newNode.getKey(0));
                            debug("12 : " + parent.toString());
                        }
                        currNode = parent;
                    }
                    break;
                } else {
                    currNode = currNode.getChildNode(index + 1);
                }
                indexes.push(index+1);
            } else if (key < currNode.getLeastKey()) {
                currNode = currNode.getChildNode(0);
                indexes.push(0);
            } else {
                // key >= currNode.getHighestKey()
                indexes.push(currNode.getNumOfKeys());
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
        while(!qu.isEmpty()) {
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
                result.append("\n");
            } else {
                result.append(Arrays.toString(parent.getKeys().toArray()));
                result.append("\n");
                qu.addAll(parent.getChildren());
                level++;
            }
        }
        return result.toString();
    }
}