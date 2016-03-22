JFLAGS = -g
JC = javac
CLASSES = $(shell ls | grep .java)

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
