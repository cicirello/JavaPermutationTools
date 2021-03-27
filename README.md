# JavaPermutationTools (JPT): A Java library for computation on permutations and sequences 

Copyright (C) 2018-2021 Vincent A. Cicirello.

https://www.cicirello.org/

Website: https://jpt.cicirello.org/

| __Publications About the Library__ | [![DOI](http://joss.theoj.org/papers/10.21105/joss.00950/status.svg)](https://doi.org/10.21105/joss.00950) |
| :--- | :--- |
| __Packages and Releases__ | [![Maven Central](https://img.shields.io/maven-central/v/org.cicirello/jpt.svg?label=Maven%20Central)](https://search.maven.org/artifact/org.cicirello/jpt) [![GitHub release (latest by date)](https://img.shields.io/github/v/release/cicirello/JavaPermutationTools?logo=GitHub)](https://github.com/cicirello/JavaPermutationTools/releases) [![DOI](https://zenodo.org/badge/139182095.svg)](https://zenodo.org/badge/latestdoi/139182095) |
| __Build Status__ | [![build](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml/badge.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) [![docs](https://github.com/cicirello/JavaPermutationTools/actions/workflows/docs.yml/badge.svg)](https://jpt.cicirello.org/api/) [![CodeQL](https://github.com/cicirello/JavaPermutationTools/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/codeql-analysis.yml) |
| __JaCoCo Test Coverage__ | [![coverage](https://github.com/cicirello/JavaPermutationTools/blob/master/.github/badges/jacoco.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) [![branch coverage](https://github.com/cicirello/JavaPermutationTools/blob/master/.github/badges/branches.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) |
| __License__ | [![GitHub](https://img.shields.io/github/license/cicirello/JavaPermutationTools)](https://github.com/cicirello/JavaPermutationTools/blob/master/LICENSE) | 

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
* The [/src](src) directory contains all of the source code for JavaPermutationTools.
* The [/tests](tests) directory contains JUnit test cases for all functionality of the library.
* The [/docs](docs) directory contains the javadoc documentation in a sub-directory /docs/api. The /docs directory is also the location of the website for the project hosted via GitHub pages at https://jpt.cicirello.org/.

## Java 8

The library supports Java 8 or higher, including both Oracle JDK 8 and OpenJDK 8.

## Versioning Scheme

The JPT uses [Semantic Versioning](https://semver.org/) with version 
numbers of the form: MAJOR.MINOR.PATCH, where differences in MAJOR 
correspond to incompatible API changes, differences in MINOR correspond 
to introduction of backwards compatible new functionality, and PATCH 
corresponds to backwards compatible bug fixes. 

## Building the Library (with Maven)

The JavaPermutationTools library is built using Maven. The root of the
repository contains a Maven `pom.xml`.  To build the library, 
execute `mvn package` at the root of the repository, which
will compile all classes, run all tests, run javadoc, and generate 
jar files of the library, the sources, and the javadocs. All build 
outputs will then be found in the directory `target`.

To include generation of a code coverage report during the build,
execute `mvn package -Pcoverage` at the root of the repository to 
enable a Maven profile that executes JaCoCo during the test 
phase. The JaCoCo report will also be found in the target directory.

## Example Programs

There are several example programs available in a separate 
repository: [cicirello/jpt-examples](https://github.com/cicirello/jpt-examples). The
examples repository contains example usage of several of the classes of the 
library. Each of the examples contains detailed comments within the source 
code explaining the example. Running the examples without reading the source 
comments is not advised. Some of the example in the examples repository are
based on the experiments from published papers that have either used the library
directly, or which led to some of the code in the library.

## Importing the Library from Maven Central

Add this to the dependencies section of your pom.xml, replacing the version number 
with the version you want to use (note that the library has been available in Maven
Central since version 2.1.2).

```XML
<dependency>
  <groupId>org.cicirello</groupId>
  <artifactId>jpt</artifactId>
  <version>2.4.0</version>
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

## Downloading Jar Files

If you don't use a dependency manager that supports importing from Maven Central,
or if you simply prefer to download manually, prebuilt jars are also attached to 
each [GitHub Release](https://github.com/cicirello/JavaPermutationTools).

## License

The JPT library is licensed under the [GNU General Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).


## Contribute

Report bugs, suggestions, feature requests, etc via the [issues tracker](https://github.com/cicirello/JavaPermutationTools/issues).  If you would 
like to directly contribute new code, you may also fork the repository, 
and create pull requests.
