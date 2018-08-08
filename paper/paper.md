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
the permutation features most important to distance calculation may be one or more of 
the absolute positions of the elements (e.g., for one-to-one mappings), the adjacency 
of elements (e.g., the earlier routing example), or the general precedence of pairs of 
elements (e.g., music preference example).  Thus, it should be no surprise that there
exists a large variety of permutation distance metrics in the research literature.

The motivation and origin of this library of permutation distance metrics is our research 
on evolutionary computation, and other metaheuristic approaches, for solving permutation 
optimization problems.  A permutation optimization problem is a problem where solutions 
are represented by permutations of the elements of some set, and where the objective is to 
maximize or minimize some function.  For example, a solution to a traveling salesperson 
problem (TSP) is a permutation of the set of cities, and the objective is to find the 
permutation that corresponds to the tour of the cities of minimal cost.

There are at least two applications of permutation distance that come from evolutionary 
computation.  The first application is to maintain population diversity.  The various forms 
of evolutionary computation, such as genetic algorithms, genetic programming, etc, solve 
problems through simulated evolutionary processes [@mitchell].  They maintain a population of solutions 
to the problem at hand, and this population evolves over many generations using operators 
such as mutation, crossover, etc.  Just as it is in natural evolution, a diverse gene 
pool is important to evolutionary computation.  In later generations, if variation among 
the members of the population declines, then the evolutionary search can stagnate.  Approaches 
to population management [@sevaux2005], such as in scatter search [@campos2005; @marti2005], 
attempt to maintain diversity among the population of solutions.  They require a means of 
measuring distance between population members.  And if the problem is one in which solutions are 
represented as permutations, then a permutation distance metric is required.

The second application of permutation distance for evolutionary computation is that of search 
landscape analysis.  A fitness (or search) landscape [@mitchell] is the space of possible 
solutions to an optimization problem spatially organized on a landscape with similar solutions 
as neighbors, and where elevation corresponds to fitness (or solution quality).  Peaks (for 
a maximization problem) and valleys (for a minimization problem) correspond to locally optimal 
solutions. The optimization problem is to find an optimal point on that landscape.  Search 
landscape analysis is the term covering the theoretical and practical techniques for studying what
characteristics of a problem make it hard, how different search operators affect fitness landscape 
topology, among others.  There is a wide variety of work on fitness landscape analysis, including 
for permutation landscapes [@cicirello2016; @hernando2015; @tayarani2014; @cicirello2014; @cicirello2013; @sorensen07; @schiavinotto2007].
Some fitness landscape analysis techniques use fitness distance correlation (FDC) [@fdc], the Pearson 
correlation coefficient of the fitness of a solution vs its distance to the nearest optimal solution.  
The search landscape calculus [@cicirello2016] is a fitness landscape analysis technique that examines 
local rates of change of fitness.  These as well as others require distance metrics for the type of 
structure you are optimizing.

During the course of our research on fitness landscape analysis for permutation optimization 
problems [@cicirello2016; @cicirello2014; @cicirello2013], we developed a Java library of permutation
distance metrics.  Most of the distance metrics in the literature are described mathematically with 
no source code available.  Thus, our library offers convenient access to efficient implementations of
a variety of distance metrics that share a common programmatic interface.  The following table
summarizes the distances available in this initial release of the library, along with their runtime
($n$ is permutation length), whether they satisfy the requirements of a metric, and one or more key citations.

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
Kendall tau distance | $O(n^2)$ | yes | [@kendall1938; @meila2010; @fagin2003]
Lee distance | $O(n)$ | yes | [@lee58]
r-type distance | $O(n)$ | yes | [@campos2005; @marti2005]
reinsertion distance | $O(n^2)$ | yes | [@cicirello2016; @cicirello2013]
reversal distance | Init: $O(n!n^3)$ Compute: $O(n^2)$ | yes | [@cicirello2016; @caprara1997]
squared deviation distance | $O(n)$ | yes | [@sevaux2005]

The source repository (https://github.com/cicirello/JavaPermutationTools) 
contains source code of the library, programs that provide example 
usage of key functionality, as well as programs that reproduce results from papers that 
have used the library.  API documentation is hosted on the web (https://jpt.cicirello.org/).

# References
