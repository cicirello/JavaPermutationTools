The program found in this directory generates the data used in the manuscript titled
"Classification and Java Package of Permutation Distance Metrics" submitted in July 2018 by Vincent A. Cicirello to
the Journal of Statistical Software.

JSSReplication.java is the source code of this Java program, which uses the various java packages of JavaPermutationTools (JPT).

The jar file jss.jar is an executable jar file that includes the permutation distance metric library, and everything else
needed by this replication program.

Usage:

From a command prompt, you can run the program as follows:

java -jar jss.jar

This runs the main method of JSSReplication.java which generates all of the data from the
paper in question (with permutations of length 10, the default).

If you want to include ReversalDistance in the analysis, you can run with the
following command line options:

java -jar jss.jar 10 R

If you want to see what happens if you change the length of the permutations, the first command line option is
the permutation length (default is 10).  For example,

java -jar jss.jar 8  (To run the analysis with permutations of length 8 without ReversalDistance.)

java -jar jss.jar 8 R (To run the analysis with permutations of length 8 with ReversalDistance.)
