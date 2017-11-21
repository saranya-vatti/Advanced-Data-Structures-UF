package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class BPlusTree {
    private Node root;
    private final int MAX_NODE_SIZE;

    /**
     * Constructor for BPlusTree
     * We only need the max node size that is dictated by the order
     * @param order {int} Any number greater than 3. This would be the max size of a
     *             node + 1
     */
    BPlusTree(int order) {
        this.root = null;
        this.MAX_NODE_SIZE = order - 1;
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
    public ArrayList<Pair> search(Node node, double startKey, double endKey) {
        ArrayList<Pair> pairs = new ArrayList<>();

        // If the node is null, it means we have fallen off the tree and the there
        // are no keys within the given range. We return an empty object.
        if(node == null) return pairs;

        if (!node.isLeaf()) {

            // If current node is not a leaf, it means, the potential leaf with the
            // startKey is one of the
            // descendants of this node - we iterate over the node to find the child and
            // trickle down the path
            // We recursively follow the path from root to the leaf node where the
            // startKey is supposed to reside
            if (Double.compare(endKey, node.getLeastKey()) < 0) {
                return search(node.getChildNode(0), startKey, endKey);
            }
            if (Double.compare(startKey, node.getHighestKey()) > 0) {
                return search(node.getLastNode(), startKey, endKey);
            }
            ArrayList<Double> nodeKeys = node.getKeys();
            int index;
            for (index = nodeKeys.size() - 1; index >= 0; index--) {
                if (Double.compare(nodeKeys.get(index), startKey) < 0) break;
            }
            return search(node.getChildNode(index + 1), startKey, endKey);
        }

        // We found the leaf!
        // This is where the startKey resides, if its present
        ArrayList<Double> nodeKeys = node.getKeys();
        int index;
        for (index = nodeKeys.size() - 1; index >= 0; index--) {
            if (Double.compare(nodeKeys.get(index), startKey) < 0) break;
        }
        index++;
        if(index >= nodeKeys.size()) {
            Node next = node.getNext();
            if(next == null) {
                return pairs;
            } else {
                index = 0;
                node = node.getNext();
            }
        }
        // Every key in the leaf is greater than start key
        // We will then search the node from the start to find keys that fit range
        double currKey = node.getKey(index);
        while (Double.compare(currKey, endKey) <= 0) {
            Pair pair = new Pair(currKey, node.getValue(index));
            pairs.add(pair);
            index++;
            if (index >= node.getNumOfKeys()) {
                if(node.getNext() != null) {
                    node = node.getNext();
                } else {
                    break;
                }
                index = 0;
            }
            currKey = node.getKey(index);
        }
        return pairs;
    }

    /**
     * Returns all values associated with the key
     *
     * @param key Key of the values that are needed
     * @return List of all the values associated with the key
     */
    public ArrayList<Pair> search(double key) {
        return search(root, key, key);
    }

    /**
     * Returns all values associated with the key
     *
     * @param startKey Starting key of the values that are needed
     * @param endKey Ending key of the values that are needed
     * @return List of all the values for the keys that fall in the range
     */
    public ArrayList<Pair> search(double startKey, double endKey) {
        return search(root, startKey, endKey);
    }

    public void insert (double key, String value) {
        if(root == null) {
            ArrayList<Double> leafKeyList = new ArrayList<>();
            leafKeyList.add(key);
            ArrayList<String> leafValList = new ArrayList<>();
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
            visited.push(currNode);
            if(currNode.isLeaf() || Double.compare(key, currNode.getLeastKey()) >=0 &&
                    Double.compare(key,currNode.getHighestKey()) < 0 ) {
                int index;
                for (index = currNode.getKeys().size() - 1; index >= 0; index--) {
                    if (Double.compare(currNode.getKeys().get(index), key) < 0) break;
                }
                if (currNode.isLeaf()) {
                    currNode.addKey(index + 1, key);
                    currNode.addVal(index + 1, value);

                    // Now we use the nodes on the stack to check the size and split
                    // if required
                    currNode = visited.pop();
                    while(currNode.getNumOfKeys() > MAX_NODE_SIZE) {
                        ArrayList<Double> keyList = new ArrayList<>();
                        ArrayList<String> valList = new ArrayList<>();
                        ArrayList<Node> nodeList = new ArrayList<>();
                        for(int i=(currNode.getKeys().size()/2);i<currNode.getKeys().size();) {
                            keyList.add(currNode.getKey(i));
                            currNode.removeKey(i);

                            // If its a leaf, we need to create a new leaf and copy
                            // over the keys and values and copy over a key to the
                            // parent
                            if (currNode.isLeaf()) {
                                valList.add(currNode.getValue(i));
                                currNode.removeVal(i);
                            } else {

                                // If its an internal node, we create a new internal
                                // node, copy over the keys and child node and
                                // remove the key that we push to the parent
                                nodeList.add(currNode.getChildNode(i+1));
                                currNode.removeChildNode(i+1);
                            }
                        }
                        Node parent;
                        int parentIndex;
                        if(visited.isEmpty()) {

                            // We need to create a new parent as the root itself is
                            // maxed out
                            ArrayList<Double> newRootKeys = new ArrayList<>();
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
                            newLeaf.setNext(currNode.getNext());
                            currNode.setNext(newLeaf);
                        } else {

                            // We create a new internal node and add the pointer from
                            // the parent internal node to the new internal node at
                            // the correct index
                            InternalNode newNode = new InternalNode(keyList, nodeList);

                            // We have removed all the keys with mid+1. So the last
                            // element remaining in the node's keylist will be the
                            // earlier mid key
                            parent.addKey(parentIndex, newNode.getKey(0));

                            // We will remove the key from the first index as we
                            // already added this to parent
                            newNode.removeKey(0);
                            parent.addChildNode(parentIndex+1, newNode);
                        }
                        currNode = parent;
                    }
                    break;
                } else {
                    currNode = currNode.getChildNode(index + 1);
                }
                indexes.push(index+1);
            } else if (Double.compare(key, currNode.getLeastKey()) < 0) {
                currNode = currNode.getChildNode(0);
                indexes.push(0);
            } else {
                // key >= currNode.getHighestKey()
                indexes.push(currNode.getNumOfKeys());
                currNode = currNode.getLastNode();
            }
        }
    }

    /**
     * A debugger function where we can print out the tree. Helps in visualization
     * for small trees. For large trees/ trees with large order, it's tough to
     * differentation between the different child nodes within the same level.
     * @return {String} New line separated values, segregated per node, prepended by
     * the level number, example:
     */
    @Override
    public String toString() {
        if(root == null) return "";
        int level = 1;
        StringBuilder result = new StringBuilder("");
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while(!qu.isEmpty()) {
            Queue<Node> tmpque = new LinkedList<>(qu);
            while(!tmpque.isEmpty()) {
                Node parent = tmpque.remove();
                qu.remove();
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
                }
            }
            level++;
        }
        return result.toString();
    }
}