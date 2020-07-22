# Examples Directory Readme:

The source for examples is contained in this directory, and
currently includes the following examples:

* org.cicirello.examples.jpt.AverageDistance
* org.cicirello.examples.jpt.TableOfDistances
* org.cicirello.examples.jpt.PermutationExamples
* org.cicirello.examples.jpt.SimpleDistanceExamples
* org.cicirello.examples.jpt.SequenceDistanceExamples
* org.cicirello.examples.jpt.PermutationHashCodes

Read the comments in the source code itself for a description of what they
demonstrate, etc.

Make sure the jar file of the library is in your classpath when
you compile and/or run any of these. Specifically, make sure jpt-2.0.1.jar 
is in your classpath (or any later version).

## Building Examples

See the README for the repository itself (in the parent of this directory).
The examples are built using ant and the build.xml from the build directory.

## Running the Examples

Assuming you use the ant build file provided, the compiled examples will be
in the exbin directory.  If your working directory is the root
of the repository, then you can run the examples from the command line with the following:

```
java -cp "dist/jpt-2.0.1.jar;exbin" org.cicirello.examples.jpt.AverageDistance
java -cp "dist/jpt-2.0.1.jar;exbin" org.cicirello.examples.jpt.TableOfDistances
java -cp "dist/jpt-2.0.1.jar;exbin" org.cicirello.examples.jpt.PermutationExamples
java -cp "dist/jpt-2.0.1.jar;exbin" org.cicirello.examples.jpt.SimpleDistanceExamples
java -cp "dist/jpt-2.0.1.jar;exbin" org.cicirello.examples.jpt.SequenceDistanceExamples
java -cp "dist/jpt-2.0.1.jar;exbin" org.cicirello.examples.jpt.PermutationHashCodes
```