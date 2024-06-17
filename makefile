run: Mta.class linalg
	java Mta 1

intxns: Mta.class linalg
	java Mta 2

adj: Mta.class linalg
	java Mta 4

pca: Mta.class linalg
	java Mta 5

test: Mta.class linalg
	java Mta test

compile: Mta.class linalg
	java Mta

Mta.class: Mta.java Stop.class TransitSystem.class Stop.class
	javac Mta.java

Stop.class: Stop.java
	javac Stop.java

TransitSystem.class: EvenTransitSystem.java AdjacencyTransitSystem.java
	javac EvenTransitSystem.java
	javac AdjacencyTransitSystem.java

linalg: ../Java?Packages/linalg/*.java
	javac ../Java?Packages/linalg/*.java
