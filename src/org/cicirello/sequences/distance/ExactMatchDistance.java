/*
 * Copyright 2018-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * This file is part of JavaPermutationTools (https://jpt.cicirello.org/).
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
 */
package org.cicirello.sequences.distance;


/**
 * <p>ExactMatch distance (or Hamming Distance) of a pair of non-binary strings (or more generally sequences) is the number of
 * sequence (or string) positions where the two sequences differ. 
 * This class supports comparison of Java String objects,
 * as well as comparisons of arrays of any of Java's eight primitive types, and arrays of any object type. 
 * It is the count of the number of positions for which the two sequences contain different elements.</p>
 *
 * <p>Most other implementations of Hamming distance require the two strings to be of the same length.  Ours does not.
 * If the two sequences (i.e., arrays or Strings) are of different lengths, then the difference in length affects
 * distance.  E.g., although "abbd" is a distance of 1 from "abcd" since they differ in one position, "abbd" is a distance of 4 from "abcdefg"
 * (the one difference, plus the three extra characters).</p>
 *
 * <p>Runtime: O(n), where n is the length of the shorter of the two sequences.</p>
 *
 * <p>If your sequences are guaranteed not to have duplicates, and to contain the same set of elements, then consider instead using the
 * {@link org.cicirello.permutations.distance.ExactMatchDistance} class, which assumes permutations of the integers from 0 to N-1.</p>
 *
 * <p>Exact match distance was introduced specifically for permutations in:<br>
 * S. Ronald, "More distance functions for order-based encodings," in Proc. IEEE CEC. IEEE Press, 1998, pp. 558–563.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.19.5.10
 * @since 1.1
 */
public final class ExactMatchDistance extends AbstractSequenceDistanceMeasurer {
	
	/**
	 * Constructs the distance measurer as specified in the class documentation.
	 */
	public ExactMatchDistance() {}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	<T> int distance(Sequence<T> s1, Sequence<T> s2) {
		int n = s1.length();
		int m = s2.length();
		if (m < n) {
			int temp = n;
			n = m;
			m = temp;
		}
		int cost = m - n;
		for (int i = 0; i < n; i++) {
			if (!s1.equal(i, s2, i)) {
				cost++;
			}
		}
		return cost;
	}
}