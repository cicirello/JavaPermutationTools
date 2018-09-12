# JavaPermutationTools (JPT): A Java API for computation on permutations and sequences 

Copyright (C) 2018 Vincent A. Cicirello.

https://www.cicirello.org/

## Overview

JPT is organized into the following packages:
* __org.cicirello.permutations__: Permutation class and a class for iterating over permutations.
* __org.cicirello.permutations.distance__: Collection of permutation distance metric implementations.
* __org.cicirello.sequences__: Classes and interfaces for representing and manipulating sequences (i.e., Strings, arrays, etc).
* __org.cicirello.sequences.distance__: Collection of distance metrics on Strings and other sequences.
* __org.cicirello.math.stats__: Implementations of statistical concepts.

Javadoc documentation is in the /docs folder as well as at https://jpt.cicirello.org/

Source code is found in the /src folder.  JUnit test classes are found in the /tests folder, which
mirrors the package structure of /src.

Compiled jar files of the library are in the /lib folder.

The /examples folder includes source code to illustrate how to use the library.  The /replication
folder includes source code that recreates results found in publications associated with the library.

## Building the Library

The /build directory contains an ant build file.  The build file also executes the JUnit tests, and the build directory contains
the relevant jar files for the JUnit libraries.  If you prefer, you can replace these with the latest versions available
from https://junit.org/junit4/ (just be sure to edit the property fields in the build.xml to point to the locations of the JUnit jar files).

To execute the build process do one of the following.  If your working directory is the build directory, then simply execute
`ant` from the command line.  If your working directory is the parent of build, then execute: `ant -f build/build.xml`

The default of the provided ant build file, compiles all source files and all JUnit tests, executes all test cases,
and generates the jar file of the library. The build process will terminate on any test case failures.  The default 
does not compile the example programs or the experiment replication programs.  To include those in the build as well
then execute `ant all` (if your working directory is the build directory) or `ant -f build/build.xml all` (if your 
working directory is the parent of build).

If you use the ant build file, the build will generate the following directories: bin (for the compiled Java classes),
testbin (for the compiled JUnit tests), lib (for the jar file of the library), and exbin (for the compiled example
programs and experiment replication programs).


## Contribute

Report bugs, suggestions, feature requests, etc via the [issues tracker](https://github.com/cicirello/JavaPermutationTools/issues).  If you would like to directly contribute new code, you may also fork the repository, and create pull requests.
