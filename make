default:
	javac -g treesearch.java BPlusTree.java Leaf.java InternalNode.java Node.java Pair.java

clean:
	$(RM) *.class