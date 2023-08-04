# JavaPermutationTools (JPT): A Java library for computation on permutations and sequences 

[![JavaPermutationTools - A Java library for computation on permutations and sequences](https://jpt.cicirello.org/images/jpt640.png)](#javapermutationtools-jpt-a-java-library-for-computation-on-permutations-and-sequences)

Copyright (C) 2018-2023 [Vincent A. Cicirello](https://www.cicirello.org/).

Website: https://jpt.cicirello.org/

API documentation: https://jpt.cicirello.org/api

| __Publications About the Library__ | [![DOI](http://joss.theoj.org/papers/10.21105/joss.00950/status.svg)](https://doi.org/10.21105/joss.00950) |
| :--- | :--- |
| __Packages and Releases__ | [![Maven Central](https://img.shields.io/maven-central/v/org.cicirello/jpt.svg?label=Maven%20Central&logo=apachemaven)](https://central.sonatype.com/artifact/org.cicirello/jpt/) [![GitHub release (latest by date)](https://img.shields.io/github/v/release/cicirello/JavaPermutationTools?logo=GitHub)](https://github.com/cicirello/JavaPermutationTools/releases) |
| __Build Status__ | [![build](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml/badge.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) [![docs](https://github.com/cicirello/JavaPermutationTools/actions/workflows/docs.yml/badge.svg)](https://jpt.cicirello.org/api/) [![CodeQL](https://github.com/cicirello/JavaPermutationTools/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/codeql-analysis.yml) |
| __JaCoCo Test Coverage__ | [![coverage](https://raw.githubusercontent.com/cicirello/JavaPermutationTools/badges/jacoco.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) [![branch coverage](https://raw.githubusercontent.com/cicirello/JavaPermutationTools/badges/branches.svg)](https://github.com/cicirello/JavaPermutationTools/actions/workflows/build.yml) |
| __Security__ | [![Snyk security score](https://snyk-widget.herokuapp.com/badge/mvn/org.cicirello/jpt/badge.svg)](https://snyk.io/vuln/maven%3Aorg.cicirello%3Ajpt) [![Snyk Known Vulnerabilities](https://snyk.io/test/github/cicirello/JavaPermutationTools/badge.svg)](https://snyk.io/test/github/cicirello/JavaPermutationTools) |
| __DOI__ | [![DOI](https://zenodo.org/badge/139182095.svg)](https://zenodo.org/badge/latestdoi/139182095) |
| __Other Information__ | [![GitHub](https://img.shields.io/github/license/cicirello/JavaPermutationTools)](https://github.com/cicirello/JavaPermutationTools/blob/master/LICENSE) [![style](https://img.shields.io/badge/style-Google%20Java%20Style-informational)](https://google.github.io/styleguide/javaguide.html) | 
| __Support__ | [![GitHub Sponsors](https://img.shields.io/badge/sponsor-30363D?logo=GitHub-Sponsors&logoColor=#EA4AAA)](https://github.com/sponsors/cicirello) [![Liberapay](https://img.shields.io/badge/Liberapay-F6C915?logo=liberapay&logoColor=black)](https://liberapay.com/cicirello) [![Ko-Fi](https://img.shields.io/badge/Ko--fi-F16061?logo=ko-fi&logoColor=white)](https://ko-fi.com/cicirello) | 

## How to Cite

If you use this library in your research, please cite the following paper:

> Cicirello, Vincent A (2018). JavaPermutationTools: A Java Library of Permutation Distance Metrics. *Journal of Open Source Software*, 3(31), 950.  https://doi.org/10.21105/joss.00950 .

## Overview

The JavaPermutationTools (JPT) library provides Java classes and interfaces, etc that 
enable representing and generating permutations and sequences, as well as performing 
computation on permutations and sequences. It includes implementations of a variety 
of permutation distance metrics as well as distance metrics on sequences (i.e., Strings, 
arrays, and other ordered data types). 

## Java 17+

We currently support Java 17+. See the following table for mapping between library version
and minimum supported Java version.

| version | Java requirements |
| --- | --- |
| 4.w.x to 5.y.z | Java 17+ |
| 3.x.y | Java 11+ |
| 1.x.y to 2.x.y | Java 8+ |

The jar files of the library are released via Maven Central, GitHub Packages, 
and GitHub Releases.

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

To run all static analysis tools (i.e., SpotBugs, Find Security Bugs,
refactor-first), execute `mvn package -Panalysis` to enable a Maven 
profile that executes the various static analysis tools that we are 
using. The SpotBugs html report will be found in the `target` directory, 
or you can use the SpotBugs GUI with: `mvn spotbugs:gui -Panalysis`. The 
refactor-first report will be found in the `target/site` directory.

To run all of the above: `mvn package -P "analysis,coverage"`.

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
for details of all packages included in this module.

Beginning with version 3.0.0, randomization and other math utilities, and some
generic utilities, have been
moved to a pair of new libraries [&rho;&mu;](https://github.com/cicirello/rho-mu)
and [org.cicirello.core](https://github.com/cicirello/core), which are
now dependencies of JavaPermutationTools. Your dependency manager (see next section)
will handle downloading these for you. 

If you are directly utilizing the functionality of the dependencies, then you may instead 
need the following:

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
  <version>5.0.0</version>
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
`jar-with-dependencies`. The `jar-with-dependencies` does not contain any module 
declarations (unlike the regular jar file which does). Therefore, the 
`jar-with-dependencies` should be used on the classpath. 

## License

The JPT library is licensed under the [GNU General Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).

## Contribute

If you would like to contribute in any way, such 
as reporting bugs, suggesting new functionality, or code contributions 
such as bug fixes or implementations of new functionality, then start 
by reading the [contribution guidelines](https://github.com/cicirello/.github/blob/main/CONTRIBUTING.md).
This project has adopted 
the [Contributor Covenant Code of Conduct](https://github.com/cicirello/.github/blob/main/CODE_OF_CONDUCT.md).
