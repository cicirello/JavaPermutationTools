---
title: 'JavaPermutationTools: A Java Library of Permutation Distance Metrics'
tags:
  - Java
  - permutation distance
  - permutation metric
  - fitness landscape analysis
  - evolutionary population management
authors:
  - name: Vincent A. Cicirello
    orcid: 0000-0003-1072-8559
    affiliation: 1
affiliations:
  - name: Computer Science, School of Business, Stockton University, Galloway, NJ 08205
    index: 1
date: 8 August 2018
bibliography: paper.bib
---

# Summary

Permutations are used to represent a wide variety of ordered data.  For example,
a permutation may represent an individual's preferences (or ranking) of a collection
of products such as books or music.  Or perhaps a permutation may represent a route
for delivering a set of packages.  Permutations can also represent one-to-one mappings 
between sets (e.g., instructors to courses at a fixed time).  There are applications 
where measuring the distance between a pair of permutations is necessary.  For example,
a recommender system may want to compare the similarity of two individuals' preferences 
for music, perhaps to make song recommendations.  Depending upon the application, 
the permutation features most important to distance calculation may be 
the absolute positions of the elements (e.g., for one-to-one mappings), the adjacency 
of elements (e.g., the routing example), or the general precedence of pairs of 
elements (e.g., music preference example).  Thus, it should be no surprise that there
exists a large variety of permutation distance metrics in the research literature.
Knuth's seminal books on algorithms [@knuth1; @knuth2; @knuth3] include permutation related 
algorithms employed by our library such as mixed radix representation, permutation 
inverse computation, etc.  

The motivation and origin of this library is our research on fitness landscape analysis 
for permutation optimization problems [@cicirello2016; @cicirello2014; @cicirello2013].  A 
permutation optimization problem is a problem where solutions are represented by 
permutations of the elements of some set, and where the objective is to maximize 
or minimize some function.  For example, a solution 
to a traveling salesperson problem (TSP) is a permutation of the set of cities, and the 
objective is to find the permutation that corresponds to the tour of the cities of minimal cost.
During the course of our research, we developed a Java library of permutation distance 
metrics.  Most of the distance metrics in the literature are described mathematically with 
no source code available.  Thus, our library offers convenient access to efficient 
implementations of a variety of distance metrics that share a common programmatic 
interface.  The library also provides distance metrics on sequences (strings and arrays of 
various types); where unlike a permutation, a sequence may contain multiple copies of the same element. 

The source repository (https://github.com/cicirello/JavaPermutationTools) 
contains source code of the library, programs that provide example 
usage of key functionality, as well as programs that reproduce results from papers that 
have used the library.  API documentation is hosted on the web (https://jpt.cicirello.org/).
 
# Statement of Need and Target Audience

The target audience of this library are those conducting computational research where
the similarity of permutations or sequences must be assessed, or for which other computation
on permutations is required (e.g., includes functionality for generating and mutating permutations
in various ways).  Permutation distance is important to those developing recommender systems,
and also important to those applying evolutionary computation to the solution
of permutation optimization problems.
 
Evolutionary computation, such as genetic algorithms, solve problems through 
simulated evolutionary processes [@mitchell].  They maintain a population of solutions 
to the problem, and this population evolves over many generations using operators 
such as mutation and crossover.  Just as it is in natural evolution, a diverse gene 
pool is important.  In later generations, if variation among the members of the population 
declines, then the evolutionary search can stagnate.  Approaches to population 
management [@sevaux2005], such as in scatter search [@campos2005; @marti2005], 
attempt to maintain population diversity, and require a means of measuring distance.

In search landscape analysis, one often requires computation of distance between locations on the
landscape.  A fitness (or search) landscape [@mitchell] is the space of possible solutions to an 
optimization problem spatially organized on a landscape with similar solutions as neighbors, and 
where elevation corresponds to fitness (or solution quality).  Peaks (for a maximization problem) 
and valleys (for a minimization problem) correspond to locally optimal solutions. The optimization 
problem is to find an optimal point on that landscape.  Search landscape analysis is the term 
covering the theoretical and practical techniques for studying what characteristics of a problem 
make it hard, how different search operators affect fitness landscape topology, among others.  There 
is a wide variety of work on fitness landscape analysis, including for permutation 
landscapes [@cicirello2016; @hernando2015; @tayarani2014; @cicirello2014; @cicirello2013; @sorensen07; @schiavinotto2007].
Fitness landscape analysis techniques, such as fitness distance correlation (FDC) [@fdc] and 
search landscape calculus [@cicirello2016] require distance metrics for the type of structure you are optimizing.

# The Metrics of the Library

The following table summarizes the distances available in the library, their runtimes
($n$ is permutation length), and whether they satisfy the metric requirements.

Distance | Runtime | Metric? | Citations
-------- | ------- | ------- | ---------
acyclic edge distance | $O(n)$ | pseudo | [@ronald1997; @ronald1995]
cyclic edge distance | $O(n)$ | pseudo | [@ronald1997; @ronald1995]
cyclic r-type distance | $O(n)$ | pseudo | [@cicirello2016]
deviation distance | $O(n)$ | yes | [@cicirello2016; @campos2005]
deviation distance normalized | $O(n)$ | yes | [@ronald1998; @sorensen07]
edit distance | $O(n^2)$ | yes | [@wagner74; @sorensen07]
exact match distance | $O(n)$ | yes | [@ronald1998]
interchange distance | $O(n)$ | yes | [@cicirello2013]
Kendall tau distance | $O(n \lg n)$ | yes | [@kendall1938; @meila2010; @fagin2003]
Lee distance | $O(n)$ | yes | [@lee58]
r-type distance | $O(n)$ | yes | [@campos2005; @marti2005]
reinsertion distance | $O(n \lg n)$ | yes | [@cicirello2016; @cicirello2013]
reversal distance | Init: $O(n!n^3)$ Compute: $O(n^2)$ | yes | [@cicirello2016; @caprara1997]
squared deviation distance | $O(n)$ | yes | [@sevaux2005]

The following table summarizes the metrics on
sequences in the library ($n \leq m$ are the lengths of the compared sequences).

Distance | Runtime | Metric? | Citations
-------- | ------- | ------- | ---------
edit distance | $O(n*m)$ | yes | [@wagner74]
exact match distance | $O(n)$ | yes | [@ronald1998]
Kendall tau distance | $O(n \lg n)$ | yes | [@kendall1938]
longest common subsequence distance | $O(n*m)$ | yes | [@wagner74]

# References
