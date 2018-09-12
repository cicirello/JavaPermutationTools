# Replication Directory Readme:

This directory contains source code to replicate data and/or experimental
results from publications that have used this library.  
Although the replication programs are commented, to fully understand what it is that
they compute, you will likely need to consult the relevant publications.

Make sure the jar file of the library is in your classpath when
you compile and/or run any of these. Specifically, make sure jpt1.jar is in your
classpath (or any later version--I intend to ensure later versions
are backwards compatible).

The following replication programs are currently available:

* org.cicirello.replication.flairs2013.Flairs2013: This program generates the correlation
data that appears in Tables 1 and 2, as well as the data necessary to generate the
graphs in Figures 1, 2, 3, 4, and 5 of the paper:
V. A. Cicirello and R. Cernera, "Profiling the distance characteristics 
of mutation operators for permutation-based genetic algorithms," 
in Proceedings of the 26th FLAIRS Conference. AAAI Press, May 2013, pp. 46–51.
https://www.cicirello.org/publications/cicirello2013flairs.html 

* org.cicirello.replication.ieeetevc2016.FDC: This program generates the fitness 
distance correlations for the "Permutation in a Haystack" fitness landscapes 
found in Table II of the paper:
V.A. Cicirello, "The Permutation in a Haystack Problem and the Calculus of Search Landscapes," 
IEEE Transactions on Evolutionary Computation, 20(3):434-446, June 2016.
https://www.cicirello.org/publications/cicirello2016evc.html

## Building the Replication Programs

See the README for the repository itself (in the parent of this directory).
The replication programs are built using ant and the build.xml from the build directory.

## Running the Replication Programs

Assuming you use the ant build file provided, the compiled replication programs will be
in the exbin directory.  If your working directory is the root
of the repository, then you can run the replication programs from the command line with the following:

```
java -cp "lib/jpt1.jar;exbin" org.cicirello.replication.flairs2013.Flairs2013
java -cp "lib/jpt1.jar;exbin" org.cicirello.replication.ieeetevc2016.FDC
```

Note that depending on your processor speed, some of these may be slow (certainly slower than
the examples from the examples folder).  The generated data is simply output to the console.
Simply redirect to a text file if you'd like to save the experimental data.

