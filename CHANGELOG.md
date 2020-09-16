# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased] - 2020-09-15
### Added

### Changed

### Deprecated

### Removed

### Fixed


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
