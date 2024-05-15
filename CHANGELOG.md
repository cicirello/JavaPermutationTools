# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased] - 2024-05-15

**Breaking Changes**: Due to breaking changes, the next release will be a major release (see the Removed section below for details). Timing of that major release will likely be in the Fall of 2023 to coincide with the planned transition to Java 21 upon its release.

### Added
* SequenceSampler.getDefault() method for creating an instance of the default implementation of SequenceSampler.
* SequenceSampler.getDefault(RandomGenerator) method for creating an instance of the default implementation of SequenceSampler from a given source of randomness.
* SequenceSampler.getDefault(seed) method for creating an instance of the default implementation of SequenceSampler, specifying a seed for the random number generator.
* Seeded constructors added to SequenceReservoirSampler, SequencePoolSampler, SequenceInsertionSampler, SequenceCompositeSampler.
* Default constructors added to SequenceReservoirSampler, SequencePoolSampler, SequenceInsertionSampler, SequenceCompositeSampler.

### Changed
* Refactored to improve code quality and to perform minor optimizations of the following classes:
  * org.cicirello.permutations.distance.EditDistance
  * org.cicirello.sequences.distance.EditDistance
  * org.cicirello.sequences.distance.EditDistanceDouble
* Refactored to improve code quality and to optimize SequenceReservoirSampler, SequencePoolSampler, SequenceInsertionSampler, SequenceCompositeSampler.
* Minor optimizations to Permutation.scramble() methods.

### Deprecated

### Removed
* Removed the previously deprecated (in v5.1.0) constructor `org.cicirello.sequences.distance.EditDistance(double, double, double)`. To compute edit distance with double-valued costs for arrays and other sequences, use the existing `EditDistanceDouble` class instead. This does not impact the class with the same name that computes edit distance for permutations (i.e., the `org.cicirello.permutations.distance.EditDistance` class still accepts doubles for the costs).
* Removed default method implementations in SequenceSampler interface (all interface methods now implemented in the implementing classes).

### Fixed
* Fixed potential integer overflow error in ReinsertionDistance
* Fixed transient fields in Permutation that shouldn't be transient
* Classes implementing SequenceSampler interface: methods with probability p of sampling an element fixed to always use specified randomness source (previously incorrectly used default randomness source on some calls).
* Fixed potential finalizer vulnerability in org.cicirello.sequences.distance.EditDistance, detected by SpotBugs analysis. 

### Dependencies
* Bump rho-mu from 3.1.0 to 3.2.0
* Bump org.cicirello:core from 2.5.0 to 2.7.0

### CI/CD
* Integrated SpotBugs static analysis into build process.
* Integrated Find Security Bugs static analysis into build process.

### Other


## [5.1.0] - 2023-05-30

### Deprecated
* Deprecated double-valued costs constructor of EditDistance for arrays and other sequences, with the existing EditDistanceDouble class as its replacement. This constructor will be removed in the next major release, most likely sometime in the Fall of 2023.

### Dependencies
* Bump core from 2.4.6 to 2.5.0
* Bump rho-mu from 3.0.4 to 3.1.0 


## [5.0.4] - 2023-04-14

### Changed
* Optimized or otherwise refactored for code quality the scramble, reverse, and swapBlocks methods of the Permutation class.


## [5.0.3] - 2023-04-13

### Changed
* Permutation now caches hash on first call to hashCode() to optimize applications that rely heavily on hashing.

### Dependencies
* Bump rho-mu from 3.0.3 to 3.0.4


## [5.0.2] - 2023-03-07

### Changed
* Minor code improvements and/or optimizations within the following classes:
  * Permutation
  * PermutationIterator
  * KendallTauDistance
  * WeightedKendallTauDistance.
  * CyclicEdgeDistance
  * CyclicRTypeDistance.
  * The various SequenceSamplers
  * KendallTauSequenceDistance

### Dependencies
* Bump core from 2.4.4 to 2.4.6
* Bump rho-mu from 3.0.2 to 3.0.3


## [5.0.1] - 2023-01-12

### Dependencies
* Bump core from 2.4.3 to 2.4.4
* Bump rho-mu from 3.0.1 to 3.0.2


## [5.0.0] - 2022-11-18

**BREAKING CHANGES:** See the Removed section for details.

### Added
* Added the SequenceSampler interface's methods to SequenceCompositeSampler.
* Added the SequenceSampler interface's methods to SequenceReservoirSampler.
* Added the SequenceSampler interface's methods to SequencePoolSampler.
* Added the SequenceSampler interface's methods to SequenceInsertionSampler.
* Methods to sample by specifying probability of including element in sample to the classes that implement SequenceSampler
  including SequenceReservoirSampler, SequencePoolSampler, SequenceInsertionSampler, and SequenceCompositeSampler.

### Changed
* SequenceSampler converted from a utility class of static methods to an interface, retaining the existing static
  methods that use a default source of randomness, but introducing nextSample methods for classes to implement.

### Removed
* SequenceSampler.sampleReservoir methods, previously deprecated in 4.3.0, replaced by the SequenceReservoirSampler class.
* SequenceSampler.samplePool methods, previously deprecated in 4.3.0, replaced by the SequencePoolSampler class.
* SequenceSampler.sampleInsertion methods, previously deprecated in 4.3.0, replaced by the SequenceInsertionSampler class.

### Dependencies
* Bump rho-mu from 2.5.0 to 3.0.1


## [4.3.1] - 2022-11-17

### Changed
* Reformatted all sourcecode to [Google Java Style](https://google.github.io/styleguide/javaguide.html)

### Fixed
* Corrected configuration of maven-shade-plugin to ensure full pom with all dependencies published during deploy.

### Dependencies
* Bump core from 2.4.0 to 2.4.3

### CI/CD
* Configured the [refactor-first-maven-plugin](https://github.com/jimbethancourt/RefactorFirst) within a profile in the pom.xml.
* Configured [Spotify's fmt-maven-plugin](https://github.com/spotify/fmt-maven-plugin) to format to Google Java Style during builds. 

### Other
* Adopted [Google Java Style](https://google.github.io/styleguide/javaguide.html)


## [4.3.0] - 2022-10-17

### Added
* EditDistanceDouble: extracted from EditDistance (for sequences) all of the distancef methods for
  double-valued distances. The original EditDistance now subclasses this one to retain existing functionality.
* Extracted the following classes from SequenceSampler that implement different algorithms for efficiently 
  randomly sampling array elements without replacement:
  * SequencePoolSampler: the pool sampling algorithm of Ernvall and Nevalainen (1982),
  * SequenceReservoirSampler: the reservoir sampling algorithm of Vitter (1985),
  * SequenceInsertionSampler: the insertion sampling algorithm of Cicirello (2022), and
  * SequenceCompositeSampler: the composite of the above three of Cicirello (2022).

### Changed
* Refactored the following classes to reduce cyclomatic complexity, or for other maintainability reasons:
  * SequenceSampler
  * ExactMatchDistance (for sequences)
  * KendallTauSequenceDistance
  * EditDistance (for sequences)

### Deprecated
* The SequenceSampler.sampleReservoir methods, which are now replaced by the SequenceReservoirSampler class.
* The SequenceSampler.samplePool methods, which are now replaced by the SequencePoolSampler class.
* The SequenceSampler.sampleInsertion methods, which are now replaced by the SequenceInsertionSampler class.

### Dependencies
* Bump rho-mu from 2.4.1 to 2.5.0
* Bump core from 2.2.2 to 2.4.0


## [4.2.0] - 2022-08-30

### Added
* Four Permutation.applyThenValidate methods, which are counterparts to the four Permutation.apply methods, 
  but which validate the Permutation(s) that result from the applied operator; whereas the existing apply
  methods do not verify the state of the Permutation after operator application.
* IllegalPermutationStateException exception class that is thrown by the new Permutation.applyThenValidate 
  methods if validation fails.

### Dependencies
* Bump rho-mu from 2.3.2 to 2.4.1


## [4.1.0] - 2022-08-03

### Added
* PermutationFullUnaryOperator and PermutationFullBinaryOperator functional interfaces for the purpose
  of specifying custom operations on Permutation objects. These are variations of the existing
  PermutationUnaryOperator and PermutationBinaryOperator interfaces that were added in 3.2.0, but with 
  both the raw int arrays and Permutation objects passed to the operators.
* Permutation.apply methods, one for each of the two new PermutationFullUnaryOperator 
  and PermutationFullBinaryOperator interfaces, for applying such custom Permutation operators.

### Dependencies
* Bump core from 2.1.0 to 2.2.2.
* Bump rho-mu from 2.3.0 to 2.3.2.


## [4.0.0] - 2022-06-02

**BREAKING CHANGES:** This is a major release that includes breaking changes. See
details below.

### Added
* Support for all Java 17 random number generators in generating random Permutation objects.

### Changed
* Minimum supported Java version is now Java 17 (breaking change).
* Utilized Java 17 RandomGenerator interface to eliminate redundant code.

### Removed
* Permutation.Mechanic nested class previously deprecated in v3.2.0 (breaking change).

### Dependencies
* Bumped core from 1.1.0 to 2.1.0 (breaking change).
* Bumped rho-mu from 1.2.0 to 2.3.0 (breaking change).


## [3.3.0] - 2022-06-01

### Added
* CycleDistance: implementation of cycle distance described in https://doi.org/10.3390/app12115506
* CycleEditDistance: implementation of cycle edit distance described in https://doi.org/10.3390/app12115506
* KCycleDistance: implementation of k-cycle distance described in https://doi.org/10.3390/app12115506

### Fixed
* ReversalDistance.max method now handles general case properly.


## [3.2.0] - 2022-03-17

### Added
* PermutationUnaryOperator and PermutationBinaryOperator functional interfaces for the purpose
  of specifying custom operations on Permutation objects.
* Permutation.apply methods, one for each of the two new PermutationUnaryOperator 
  and PermutationBinaryOperator interfaces, for applying such custom Permutation operators.

### Changed
* Various improvements to the documentation.

### Deprecated
* The Permutation.Mechanic nested class, which will be removed in the next 
  major release 4.0.0. The new Permutation.apply methods should be used instead.


## [3.1.1] - 2022-02-17

### Fixed
* Fixed inefficient memory usage in KendallTauDistance (issue #183)
* Fixed inefficient memory usage in KendallTauSequenceDistance (issue #184)
* Fixed inefficient memory usage in WeightedKendallTauDistance (issue #185)
* Fixed inefficiency in WeightedKendallTauDistance related to redundant computation (issue #182)


## [3.1.0] - 2022-02-15

### Added
* WeightedKendallTauDistance: an implementation of a weighted version of Kendall tau distance

### Changed
* Bumped dependency rho-mu to 1.2.0
* Bumped dependency org.cicirello.core to 1.1.0
* Migrated test cases to JUnit 5 (specifically JUnit Jupiter 5.8.2).

### CI/CD
* Automated commenting of test coverage percentages on pull requests.
* Revised documentation workflow to deploy to API doc updates to website only
  on releases to ensure consistent with latest library release.

### Other
* Various updates to project website.  


## [3.0.0] - 2021-09-24

### Changed
* Minimum supported Java version is now Java 11+.
* The library now uses Java modules, providing the
  module `org.cicirello.jpt`, which includes the
  existing packages `org.cicirello.permutations` and
  `org.cicirello.sequences`, as well as their
  subpackages.
* The randomization and other math utilities, previously
  contained in the package `org.cicirello.math` and its
  subpackages have been moved to a new library
  [&rho;&mu;](https://rho-mu.cicirello.org), within a module
  `org.cicirello.rho_mu`. That module is now a dependency
  of jpt, declared with a `requires transitive` to ease the
  transition for existing users of JPT (they should only 
  need a `requires org.cicirello.jpt` to get access to
  that functionality as well).
* Similar to the above, the package `org.cicirello.util`
  has been moved out of JPT to a new library
  [org.cicirello.core](https://core.cicirello.org), within 
  a module `org.cicirello.core`. The package name of
  `org.cicirello.util` has been kept. This new module is
  now a dependency of JPT. And just as in the case with the
  math and randomization utilities, JPT declares the module
  requires with `requires transitive` to ease the transition
  of existing users.
* We will now also publish a `jar-with-dependencies` for those
  users of the library who do not use a dependency manager. This
  fat jar contains the library and all dependencies. This fat
  jar is not modularized (unlike the regular jar), as Java 
  only allows a single module per jar. However, it is still 
  built for a Java 11 target.


## [2.6.5] - 2021-09-10

### Other
* Fixed Zenodo metadata. This release is
  strictly to confirm correct integration with Zenodo. There are
  no actual changes or additions, etc to the library itself.


## [2.6.4] - 2021-09-10

### Other
* Configured a CITATION.cff file in repository. This release is
  strictly to confirm correct integration with Zenodo. There are
  no actual changes or additions, etc to the library itself.


## [2.6.3] - 2021-09-10

### Other
* Configured a CITATION.cff file in repository. This release is
  strictly to confirm correct integration with Zenodo. There are
  no actual changes or additions, etc to the library itself.


## [2.6.2] - 2021-09-10

### Other
* Configured a CITATION.cff file in repository. This release is
  strictly to confirm correct integration with Zenodo. There are
  no actual changes or additions, etc to the library itself.


## [2.6.1] - 2021-05-13

### Changed
* Various code improvements based on initial run of MuseDev static analysis.
  Changes included eliminating error prone code, etc, but no actual bugs were
  uncovered.

### CI/CD
* Upgraded coverage reporting to JaCoCo 0.8.7.
* Enabled MuseDev code scanning on all pull requests.


## [2.6.0] - 2021-04-02

### Added
* Added the `Permutation.Mechanic.set(Permutation, int[], int, int, int)` method.

### Changed
* Refactored the SequenceDistanceMeasurer and SequenceDistanceMeasurerDouble interfaces 
  into a hierarchy, eliminated an abstract base class made obsolete by that change, and
  changed all sequence distances in the library to use the new hierarchy. This is a non-breaking
  change, as the only thing removed was a package-private abstract class, and the
  change to the SequenceDistanceMeasurer interface was done in such a way that all inherited
  methods have default implementations.
* Refactored the PermutationDistanceMeasurer, PermutationDistanceMeasurerDouble,
  NormalizedPermutationDistanceMeasurer, and NormalizedPermutationDistanceMeasurerDouble 
  interfaces, and all of the classes that implement them, to move default implementations 
  from an abstract base class into the appropriate subinterfaces in hierarchy. This is a 
  non-breaking change, as the only thing removed was a package-private abstract class, and the
  changes to interfaces were done in such a way that all inherited methods have default 
  implementations.


## [2.5.0] - 2021-03-29

### Added
* New methods added to the Permutation class.
    * The cycle method creates a permutation cycle.
    * Multiple variations of a scramble(int[] indexes) method that 
      randomizes the elements indicated by the array of indexes. The
      variations of this method correspond to the different random number
      generators supported by the library.

### Changed
* Minor code improvements in Permutation class (e.g., refactoring to 
  reduce duplicated code, and some minor code optimizations).

### CI/CD
* Started using CodeQL code scanning on all push/pull-request events.


## [2.4.0] - 2021-02-15

### Changed
* Minor optimizations to ReversalDistance and ReinsertionDistance.
* Refactored the RandomVariates.nextCauchy methods to remove redundancy.
* Refactored org.cicirello.math.rand.BTPE to remove redundancy.
* Refactored various methods of RandomIndexer to remove redundancy.
* Minor optimizations to RandomIndexer.sample methods.

### Fixed
* Fixed large magnitude negative input case in MathFunctions.logGamma.
* Fixed but in JacobiDiagonalization in case when default epsilon is not used.

### CI/CD
* Added test cases to improve test coverage to 100%.


## [2.3.0] - 2021-01-30

### Added
* Test cases added to improve test coverage.

### Changed
* Modified API documentation website (https://jpt.cicirello.org/) to improve browsing on mobile devices.
* Minor optimizations in Permutation class.

### Removed
* Moved the example programs to a new repository. They were previously found in directories examples and replication, both of which have been removed. All of the examples are now located in the repository: https://github.com/cicirello/jpt-examples.
* Removed jars of the library from the repo. These have been available from Maven Central, GitHub Packages, and GitHub Releases, for quite some time. No need to store in repo, and it is inefficient to do so.
* Removed the zip files generated by javadoc of the indexes for the api website. These are not needed for search functionality, as javadoc also stores and uses the js files contained in these zips. Later versions of javadoc no longer generate these. Also gitignored these to prevent future storage.

### Fixed
* Bug in Permutation.toString which was inserting an extra space at end.
* Added validation checking for all permutation distance measures validating same length permutations (except for EditDistance which can handle that case).
* Bug in ReversalDistance.max in case when permutation length is 2, and also added missing parameter validation.
* Minor bug fix in KendallTauSequenceDistance in the case of distance between arrays of floats.

### CI/CD
* Migrated build process from Ant to Maven, including GitHub workflows.
* Migrated workflow for generating library website from Ant to Maven.
* Added test coverage via JaCoCo.


## [2.2.0] - 2020-09-18

### Changed
* Modernized API documentation website (https://jpt.cicirello.org/) to html5 with search and no frames.


## [2.1.3] - 2020-09-16

### Changed
* Revised workflow for publishing packages to publish to both Maven Central and Github Packages using one pom.xml.
* Documentation updates


## [2.1.2] - 2020-09-15

### Other
* Working on getting workflow for publishing to maven central working properly.  No functional difference from previous release.


## [2.1.1] - 2020-09-15

### Other
* Working on getting workflow for publishing to maven central working properly.  No functional difference from previous release.


## [2.1.0] - 2020-09-15
### Added
* Automated generation of sitemap.xml via GitHub actions for the documentation website.
* .zenodo.json file for providing metadata to Zenodo for archival purposes.
* This ChangeLog was introduced, compiled from release notes of prior releases. The ChangeLog is likely inaccurate prior to this date, but should include the major changes.  Beginning with this release, the ChangeLog will be updated at the time of pull requests. 
* Enabled DependaBot to keep dependencies up to date.
* First release published in Maven Central

### Changed
* Updated dependency versions



## [2.0.5] - 2020-07-30
### Removed
* Removed unnecessary maven settings.xml.

## [2.0.4] 
### Other
* All GitHub releases beginning with this version now include prebuilt jar files of the library, sources, and javadocs.
* This release contains no changes or additions to library functionality. It is functionally equivalent to v2.0.3.

## [2.0.3]
### Fixed
* Fixed bug in workflow for publishing to GitHub Package Registry. Doesn't affect library itself. Just rereleasing to test workflow. v2.0.3 is functionally identical to v2.0.1 and v2.0.2.

## [2.0.2]
### Fixed
* Fixed bug in workflow for publishing to GitHub Package Registry. Doesn't affect library itself. Just rereleasing to test workflow. v2.0.2 is functionally identical to v2.0.1.

## [2.0.1]
### Added
* First version published to the GitHub Package Registry

### Changed
* Changed jar naming scheme
* Renamed distribution directory from lib to dist

## [2.0.0]
### Added
* RandomVariates class with methods to generate random values from various distributions including the Cauchy distribution and binomial distribution. 
* Copyable interface also new. 
* Also now includes the permutation distance called Block Interchange Distance.

## [1.5.0]
### Added
* SequenceSampler class for randomly sampling arrays and other sequences. 
* New functionality added to the RandomIndexer class. 

### Changed
* A variety of refactorings to improve code readability and maintenance, as well as a variety of code optimizations.

## [1.4.0]
### Added
* RandomIndexer class which can be used to efficiently generate random indexes and combination of indexes into arrays and permutations. 

### Changed
* Library has been optimized for more efficient random number generation (e.g., generating random permutations more efficiently).

## [1.3.0]
### Added
* New functionality in the Permutation class to make it easier in the future to add new operations on Permutations.

## [1.2.5]
### Added
* Added functionality to the Permutation class, and added normalization for permutation distance measures.

## [1.2.4]
### Added
* KendallTauSequenceDistance in the package org.cicirello.sequences.distance
* New functionality in the permutation distance classes

## [1.2.3]
### Added
* Replication program for reproducing the data from the paper: Vincent A. Cicirello. Classification of Permutation Distance Metrics for Fitness Landscape Analysis. In Proceedings of the 11th International Conference on Bio-inspired Information and Communications Technologies. ICST, March 2019.

## [1.2.2]
### Changed
* Release includes all updates, revisions, etc produced during review of paper for Journal of Open Source Software, titled "JavaPermutationTools: A Java library of permutation distance metrics."

## [1.2.1]
### Changed
* Release includes all updates, revisions, etc produced during review of paper for Journal of Open Source Software, titled "JavaPermutationTools: A Java library of permutation distance metrics."

## [1.2.0]
### Changed
* Release includes all updates, revisions, etc produced during review of paper for Journal of Open Source Software, titled "JavaPermutationTools: A Java library of permutation distance metrics."

## [1.1.0]
### Added
* Distance metrics on sequences (i.e., Strings, arrays of primitives, arrays of objects)

## [1.0.1]
### Changed
* More efficient implementations of ReinsertionDistance and KendallTauDistance. Specifically O(n lg n) implementations where the prior implementations were O(n^2).

## [1.0.0]
This is the initial release.
