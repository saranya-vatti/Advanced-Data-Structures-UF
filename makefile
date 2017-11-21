JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Pair.java \
		Node.java \
		InternalNode.java \
		Leaf.java \
		BPlusTree.java \
		treesearch.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class