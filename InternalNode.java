import java.util.ArrayList;
import java.util.Arrays;

class InternalNode extends Node {

    // Arraylist of child pointers to other nodes - may be leaves or internal nodes
    // size will always be number of keys + 1
    private ArrayList<Node> children;

    /**
     * Returns the child node at that index.
     * @param index {int}
     * @return {Node} Child node may be another internal node or a leaf
     */
    @Override
    public Node getChildNode(int index) {
        return children.get(index);
    }

    /**
     * Returns the last node whose keys are all greater than the great key in the
     * current node
     * @return {Node} Child node may be another internal node or a leaf
     */
    @Override
    public Node getLastNode () {
        return children.get(children.size() - 1);
    }

    /**
     * Returns the entire list of child nodes. Used while debugging to print the
     * entire tree
     * @return {ArrayList} List of children. This is never null as internal nodes
     * will always contain child nodes - child leaves or child internal nodes.
     */
    public ArrayList<Node> getChildren() {
        return this.children;
    }

    /**
     * Constructor for internal node
     * @param keys {ArrayList<Double>} Contains the keys in the current node
     * @param children {ArrayList<Node>} List of children - may be nodes or leaves
     */
    InternalNode(ArrayList<Double> keys, ArrayList<Node> children) {
        super.setKeys(keys);
        this.children = children;
    }

    /**
     * Implemented from parent class
     * @return {false}
     */
    public boolean isLeaf() {
        return false;
    }

    /**
     * Adds the child node at the given index. This is never called with wrong
     * indices, but still handled.
     * @param index {int}
     * @param node {Node} May be internal node or leaf.
     */
    @Override
    public void addChildNode(int index, Node node) {
        if (index < 0) index = 0;
        if (index > children.size()) index = children.size() - 1;
        children.add(index, node);
    }

    /**
     * Removes the node from the given index
     * @param index {int}
     */
    @Override
    public void removeChildNode(int index) {
        this.children.remove(index);
    }

    /**
     * For debugging purposes. Prints the node's contents prefixed with "IN" for
     * "Internal Node"
     * @return {String} Returns the default string implementation for arrays with
     * double type keys.
     */
    @Override
    public String toString() {
        return "IN : " + Arrays.toString(super.getKeys().toArray());
    }
}