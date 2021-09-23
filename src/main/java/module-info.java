/*
 * JavaPermutationTools - A Java library for computation on permutations.
 * Copyright 2005-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * <h2>JavaPermutationTools - A Java library for computation on permutations</h2>
 * 
 * <p>JavaPermutationTools (JPT) is a Java library for representing and generating permutations 
 * and sequences, as well as performing computation on permutations and sequences. This includes 
 * implementations of a variety of permutation distance metrics as well as distance metrics on 
 * sequences (i.e., Strings, arrays, and other ordered data types).</p>
 *
 * <p>The <a href="https://github.com/cicirello/JavaPermutationTools" target=_top>source code</a> 
 * is hosted on GitHub; and is licensed under the 
 * <a href="https://www.gnu.org/licenses/gpl-3.0.html" target=_top>GNU General Public License 
 * Version 3 (GPLv3)</a>. The library's jar files are regularly published 
 * to <a href="https://search.maven.org/artifact/org.cicirello/jpt" target=_top>Maven Central</a>, 
 * from which it is easily imported into software projects using Maven and other 
 * commonly used build tools.  Additionally, there is a 
 * GitHub <a href="https://github.com/cicirello/jpt-examples" target=_top>repository of 
 * example programs</a> that show basic usage of the JPT library, as well as replication programs that 
 * reproduce results found in published papers.</p>
 *
 * <p>For more information see the <a href="https://jpt.cicirello.org/" target=_top>JavaPermutationTools 
 * website</a>. Potential contributors, please see the source repository, and/or 
 * contact <a href="https://www.cicirello.org/" target=_top>Vincent A. Cicirello</a>.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
module org.cicirello.jpt {
	exports org.cicirello.permutations;
	exports org.cicirello.permutations.distance;
	exports org.cicirello.sequences;
	exports org.cicirello.sequences.distance;
}