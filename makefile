default:
	javac -g treesearch.java BPlusTree.java Leaf.java InternalNode.java Node.java Pair.java

run:
	treesearch.class

clean:
	$(RM) *.class