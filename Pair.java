class Pair {
    double key;
    String val;
    Pair(double key, String val) {
        this.key = key;
        this.val = val;
    }

    /**
     * For the final output
     * @return {String} Key and Value pairs. Used to print the leaf contents.
     */
    @Override
    public String toString() {
        return "(" + key + "," + val + ")";
    }
}