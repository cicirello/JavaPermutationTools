# JavaPermutationTools (JPT): A Java library for computation on permutations and sequences 

Copyright (C) 2018-2020 Vincent A. Cicirello.

https://www.cicirello.org/

| __Publications About the Library__ | [![DOI](http://joss.theoj.org/papers/10.21105/joss.00950/status.svg)](https://doi.org/10.21105/joss.00950) |
| :--- | :--- |
| __Packages and Releases__ | [![Maven Central](https://img.shields.io/maven-central/v/org.cicirello/jpt.svg?label=Maven%20Central)](https://search.maven.org/artifact/org.cicirello/jpt) [![GitHub release (latest by date)](https://img.shields.io/github/v/release/cicirello/JavaPermutationTools?logo=GitHub)](https://github.com/cicirello/JavaPermutationTools/releases) [![DOI](https://zenodo.org/badge/139182095.svg)](https://zenodo.org/badge/latestdoi/139182095) |
| __Source and Build Info__ | [![build](https://github.com/cicirello/JavaPermutationTools/workflows/build/badge.svg)](https://github.com/cicirello/JavaPermutationTools/actions?query=workflow%3Abuild) [![GitHub](https://img.shields.io/github/license/cicirello/JavaPermutationTools)](https://github.com/cicirello/JavaPermutationTools/blob/master/LICENSE) |

## How to Cite

If you use this library in your research, please cite the following paper:

> Cicirello, Vincent A (2018). JavaPermutationTools: A Java Library of Permutation Distance Metrics. *Journal of Open Source Software*, 3(31), 950.  https://doi.org/10.21105/joss.00950 .

## Overview

The JavaPermutationTools (JPT) library provides Java classes and interfaces, etc that 
enable representing and generating permutations and sequences, as well as performing 
computation on permutations and sequences. It includes implementations of a variety 
of permutation distance metrics as well as distance metrics on sequences (i.e., Strings, 
arrays, and other ordered data types). 

## Repository Organization

The GitHub repository is organized as follows:
* The [/build](build) directory contains an ant build file, and other resources related to building the library.
* The [/dist](dist) directory contains the compiled jar files of the library.
* The [/docs](docs) directory contains the javadoc documentation in a sub-directory [/docs/api](docs/api). The /docs directory is also the location of the website for the project hosted via GitHub pages at https://jpt.cicirello.org/.
* The [/examples](examples) directory contains several example programs that use the library, and which demonstrate usage of various features.
* The [/replication](replication) directory includes source code that recreates results found in publications associated with the library.
* The [/src](src) directory contains all of the source code.
* The [/tests](tests) directory contains JUnit test cases for all functionality of the library.

## Java 8

The library supports Java 8 or higher, including both Oracle JDK 8 and OpenJDK 8.

## Versioning Scheme

The JPT uses [Semantic Versioning](https://semver.org/) with version 
numbers of the form: MAJOR.MINOR.PATCH, where differences in MAJOR 
correspond to incompatible API changes, differences in MINOR correspond 
to introduction of backwards compatible new functionality, and PATCH 
corresponds to backwards compatible bug fixes. 

## Building the Library (with ant)

The /build directory contains an ant build file.  The build file 
also executes the JUnit tests, and the build directory contains
the relevant jar files for the JUnit libraries.  If you prefer, 
you can replace these with the latest versions available
from https://junit.org/junit4/ (just be sure to edit the property 
fields in the build.xml to point to the locations of the JUnit jar files).

To execute the build process do one of the following.  If your working 
directory is the build directory, then simply execute
`ant` from the command line.  If your working directory is the 
parent of build, then execute: `ant -f build/build.xml`

The default of the provided ant build file, compiles all source 
files and all JUnit tests, executes all test cases,
generates the jar file of the library, and compiles the example 
programs and the experiment replication programs. The 
build process will terminate on any test case failures.  

If you use the ant build file, the build will generate the 
following directories: bin (for the compiled Java classes),
testbin (for the compiled JUnit tests), dist (for the jar file of the library), 
and exbin (for the compiled example
programs and experiment replication programs).

## Running the Example Programs

The examples directory contains example usage of several of the classes of the API.
The replication directory additionally contains more complex examples that replicate the
experiments from published papers that have used the library.  Those directories contain
READMEs that provide more detail on the examples, including on running the examples.

## Importing the Library from Maven Central

Add this to the dependencies section of your pom.xml, replacing the version number 
with the version you want to use (note that the library has been available in Maven
Central since version 2.1.2).

```XML
<dependency>
  <groupId>org.cicirello</groupId>
  <artifactId>jpt</artifactId>
  <version>2.1.2</version>
</dependency>
```

## Importing the Library from Github Packages

If you'd prefer to import from Github Packages, rather than Maven Central, 
then: (1) add the dependency as indicated in previous section above, 
and (2) add the following to the repositories section of your pom.xml:

```XML
<repository>
  <id>github</id>
  <name>GitHub cicirello Apache Maven Packages</name>
  <url>https://maven.pkg.github.com/cicirello/JavaPermutationTools</url>
  <releases><enabled>true</enabled></releases>
  <snapshots><enabled>true</enabled></snapshots>
</repository>
```

## License

The JPT library is licensed under the [GNU General Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).


## Contribute

Report bugs, suggestions, feature requests, etc via the [issues tracker](https://github.com/cicirello/JavaPermutationTools/issues).  If you would 
like to directly contribute new code, you may also fork the repository, 
and create pull requests.
