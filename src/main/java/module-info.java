/*
 * JavaPermutationTools - A Java library for computation on permutations.
 * Copyright 2005-2023 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * JavaPermutationTools is free software: you can
 * redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JavaPermutationTools is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaPermutationTools.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/**
 *
 *
 * <h2>JavaPermutationTools - A Java library for computation on permutations</h2>
 *
 * <p>Copyright &copy; 2005-2023 <a href="https://www.cicirello.org/" target=_top>Vincent A.
 * Cicirello</a>.
 *
 * <p><a href="https://doi.org/10.21105/joss.00950"><img
 * src="http://joss.theoj.org/papers/10.21105/joss.00950/status.svg" alt="DOI:10.21105/joss.00950"
 * height="20" width="168"></a> <a href="https://central.sonatype.com/search?namespace=org.cicirello&q=jpt"><img
 * src="https://img.shields.io/maven-central/v/org.cicirello/jpt.svg?logo=apachemaven" alt="Maven
 * Central" height="20" width="153"></a> <a
 * href="https://github.com/cicirello/JavaPermutationTools/releases"><img
 * src="https://img.shields.io/github/v/release/cicirello/JavaPermutationTools?logo=GitHub"
 * alt="GitHub release (latest by date)" height="20" width="111"></a> <a
 * href="https://github.com/cicirello/JavaPermutationTools"><img
 * src="https://jpt.cicirello.org/images/GitHub.svg" alt="GitHub Repository" width="68"
 * height="20"></a> <a
 * href="https://github.com/cicirello/JavaPermutationTools/blob/master/LICENSE"><img
 * src="https://img.shields.io/github/license/cicirello/JavaPermutationTools" alt="GNU General
 * Public License Version 3 (GPLv3)" height="20" width="102"></a>
 *
 * <h3>About the JavaPermutationTools Library</h3>
 *
 * <p>JavaPermutationTools (JPT) is a Java library for representing and generating permutations and
 * sequences, as well as performing computation on permutations and sequences. This includes
 * implementations of a variety of permutation distance metrics as well as distance metrics on
 * sequences (i.e., Strings, arrays, and other ordered data types).
 *
 * <p>The <a href="https://github.com/cicirello/JavaPermutationTools" target=_top>source code</a> is
 * hosted on GitHub; and is licensed under the <a href="https://www.gnu.org/licenses/gpl-3.0.html"
 * target=_top>GNU General Public License Version 3 (GPLv3)</a>. The library's jar files are
 * regularly published to <a href="https://search.maven.org/artifact/org.cicirello/jpt"
 * target=_top>Maven Central</a>, from which it is easily imported into software projects using
 * Maven and other commonly used build tools. Additionally, there is a GitHub <a
 * href="https://github.com/cicirello/jpt-examples" target=_top>repository of example programs</a>
 * that show basic usage of the JPT library, as well as replication programs that reproduce results
 * found in published papers.
 *
 * <p>For more information see the <a href="https://jpt.cicirello.org/"
 * target=_top>JavaPermutationTools website</a>. Potential contributors, please see the source
 * repository, and/or contact <a href="https://www.cicirello.org/" target=_top>Vincent A.
 * Cicirello</a>.
 *
 * <h3>How to Cite</h3>
 *
 * <p>If you use the JPT library in your research, please cite the following article which describes
 * the library:
 *
 * <ul>
 *   <li>Vincent A. Cicirello. <a
 *       href="https://www.cicirello.org/publications/cicirello2018joss.html">JavaPermutationTools:
 *       A Java Library of Permutation Distance Metrics</a>. <i>Journal of Open Source Software</i>,
 *       3(31), 950, November 2018. <a
 *       href="https://www.cicirello.org/publications/cicirello2018joss.pdf">[PDF]</a> <a
 *       href="https://www.cicirello.org/publications/cicirello2018joss.bib">[BIB]</a> <a
 *       href="https://doi.org/10.21105/joss.00950">[DOI]</a>
 * </ul>
 *
 * <h3>Support JavaPermutationTools</h3>
 *
 * <p><a href="https://github.com/sponsors/cicirello"><img
 * src="https://jpt.cicirello.org/images/github-sponsors.svg" alt="GitHub Sponsors" width="107"
 * height="28"></a> <a href="https://liberapay.com/cicirello"><img
 * src="https://jpt.cicirello.org/images/Liberapay.svg" alt="Liberapay" width="119" height="28"></a>
 * <a href="https://ko-fi.com/cicirello"><img src="https://jpt.cicirello.org/images/ko-fi.svg"
 * alt="Ko-Fi" width="82" height="28"></a>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
module org.cicirello.jpt {
  exports org.cicirello.permutations;
  exports org.cicirello.permutations.distance;
  exports org.cicirello.sequences;
  exports org.cicirello.sequences.distance;

  // Specified transitive on these to minimize the impact
  // of the introduction of modules on users' existing code
  // that might assume access to the classes/interfaces from these
  // modules. Strictly speaking, these do not need to be transitive
  // for jpt because jpt's public interface does not depend on anything
  // in these (e.g., no objects of any classes from these modules are
  // returned or expected as parameters).
  requires transitive org.cicirello.rho_mu;
  requires transitive org.cicirello.core;
}
