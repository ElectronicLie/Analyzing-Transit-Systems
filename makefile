run: compile
	java Main.java

compile: *.java ../Java?Packages/linalg/*.java ../Java?Packages/polynomials/*.java
	javac *.java
	javac ../Java?Packages/linalg/*.java
	javac ../Java?Packages/polynomials/*.java
