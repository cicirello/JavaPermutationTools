/*
 * Copyright 2008, 2010, 2017-2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
 *
 * This file is part of package org.cicirello.permutations.distance.
 *
 * Java package org.cicirello.permutations.distance is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * Java package org.cicirello.permutations.distance is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java package org.cicirello.permutations.  If not, 
 * see <http://www.gnu.org/licenses/>.
 */
package org.cicirello.permutations.distance;

import org.cicirello.permutations.Permutation;
/**
 * Exact Match Distance:
 *
 * <p>Exact Match distance is an extension of Hamming distance but to non-binary strings, in this case, permutations.
 * It is the count of the number of positions for which the two permutations contain different elements.</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 *
 * <p>Exact match distance was introduced in:<br>
 * S. Ronald, "More distance functions for order-based encodings," in Proc. IEEE CEC. IEEE Press, 1998, pp. 558–563.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 * @version 2.18.8.2  
 * @since 1.0
 */
public class ExactMatchDistance extends AbstractPermutationDistanceMeasurer {
  
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int distance(Permutation p1, Permutation p2) {
		int misMatchPoints = 0;
		int L = p1.length();
		for (int i = 0; i < L; i++) {
			if (p1.get(i) != p2.get(i)) {
				misMatchPoints++;
			}
		}
		return misMatchPoints; 
	}
  
}