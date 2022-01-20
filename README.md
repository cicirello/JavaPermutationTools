# JavaPermutationTools (JPT): A Java library for computation on permutations and sequences 

[<img alt="JavaPermutationTools - A Java library for computation on permutations and sequences" 
     src="../gh-pages/images/jpt.png" width="640">](#javapermutationtools-jpt-a-java-library-for-computation-on-permutations-and-sequences)

Copyright (C) 2018-2022 [Vincent A. Cicirello](https://www.cicirello.org/).

Website: https://jpt.cicirello.org/

API documentation: https://jpt.cicirello.org/api

| __Publications About the Library__ | [![DOI](http://joss.theoj.org/papers/10.21105/joss.00950/status.svg)](https://doi.org/10.21105/joss.00950) |
| :--- | :--- |
| __Packages and Releases__ | [![Maven Central](https://img.shields.io/maven-central/v/org.cicirello/jpt.svg?label=Maven%20Central&logo=apachemaven)](https://search.maven.org/artifact/org.cicirello/jpt) [![GitHub release (latest by date)](https://img.shields.io/github/v/release/cicirello/JavaPermutationTools?logo=GitHub)](https://github.com/cicirello/JavaPermutationTools/releases) |
| __Build Status__ | [![build](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml/badge.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) [![docs](https://github.com/cicirello/JavaPermutationTools/actions/workflows/docs.yml/badge.svg)](https://jpt.cicirello.org/api/) [![CodeQL](https://github.com/cicirello/JavaPermutationTools/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/codeql-analysis.yml) |
| __JaCoCo Test Coverage__ | [![coverage](../badges/jacoco.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) [![branch coverage](../badges/branches.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) |
| __Security__ | [![Snyk security score](https://snyk-widget.herokuapp.com/badge/mvn/org.cicirello/jpt/badge.svg)](https://snyk.io/vuln/maven%3Aorg.cicirello%3Ajpt) [![Snyk Known Vulnerabilities](https://snyk.io/test/github/cicirello/JavaPermutationTools/badge.svg)](https://snyk.io/test/github/cicirello/JavaPermutationTools) |
| __DOI__ | [![DOI](https://zenodo.org/badge/139182095.svg)](https://zenodo.org/badge/latestdoi/139182095) |
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

## Java 11+

Beginning with version 3.0.0, the library supports Java 11+. Our development process 
utilizes OpenJDK 11, and all jar files released via Maven Central, GitHub Packages, 
and GitHub Releases are built with a Java 11 target.

Versions prior to 3.0.0 previously required Java 8+.

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

## Java Modules

This library provides a Java module, `org.cicirello.jpt`. To use in your project,
add the following to your `module-info.java`:

```Java
module your.module.name.here {
	requires org.cicirello.jpt;
}
```

This module includes the `org.cicirello.permutations` and `org.cicirello.sequences`
packages as well as their subpackages. See the [API documentation](https://jpt.cicirello.org/api) 
for details.

Beginning with version 3.0.0, randomization and other math utilities, and some
generic utilities, have been
moved to a pair of new libraries [&rho;&mu;](https://github.com/cicirello/rho-mu)
and [org.cicirello.core](https://github.com/cicirello/core), which are
now dependencies of JavaPermutationTools. Your dependency manager (see next section)
will handle downloading these for you. 

To ease the transition of users of the library who may have been relying on those
utilities, we have configured the `module-info.java` for the `org.cicirello.jpt` module to
require these transitively so that your application should only need to
require `org.cicirello.jpt` to access the functionality of those new modules.
However, it is possible that you __may__ actually need the following to access
some of that functionality, such as the RandomIndexer class, or other math or utility classes:

```Java
module your.module.name.here {
	requires org.cicirello.jpt;
	requires org.cicirello.rho_mu;
	requires org.cicirello.core;
}
```

## Importing the Library from Maven Central

Add this to the dependencies section of your pom.xml, replacing the version number 
with the version you want to use.

```XML
<dependency>
  <groupId>org.cicirello</groupId>
  <artifactId>jpt</artifactId>
  <version>3.0.0</version>
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

In addition to the regular jar of the library, we also regularly publish a
`jar-with-dependencies`. The `jar-with-dependencies` is built for a Java 11 target,
but does not contain any module declarations (unlike the regular jar file which does).
Therefore, the `jar-with-dependencies` should be used on the classpath. 

## License

The JPT library is licensed under the [GNU General Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).

## Contribute

If you would like to contribute in any way, such 
as reporting bugs, suggesting new functionality, or code contributions 
such as bug fixes or implementations of new functionality, then start 
by reading the [contribution guidelines](https://github.com/cicirello/.github/blob/main/CONTRIBUTING.md).
This project has adopted 
the [Contributor Covenant Code of Conduct](https://github.com/cicirello/.github/blob/main/CODE_OF_CONDUCT.md).
