run: MTA.class linalg
	java MTA

MTA.class: MTA.java Stop.class
	javac MTA.java

Stop.class: Stop.java
	javac Stop.java

linalg: ../Java?Packages/linalg/*.java
	javac ../Java?Packages/linalg/*.java
