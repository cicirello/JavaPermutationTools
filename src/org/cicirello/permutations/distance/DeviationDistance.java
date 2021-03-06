/*
 * Copyright 2008, 2010, 2014, 2017-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
package org.cicirello.permutations.distance;

import org.cicirello.permutations.Permutation;
/**
 * Deviation Distance:
 *
 * <p>Deviation distance is the sum of the positional deviation of the permutation elements.
 * The positional deviation of an element is the difference in its location in the two
 * permutations.</p>
 *
 * <p>For example, consider p1 = [0, 1, 2, 3, 4, 5] and p2 = [1, 0, 5, 2, 4, 3].
 * Element 0 is displaced by 1 position.  Likewise for elements 1 and 2.
 * Element 3 is displaced by 2 positions.
 * Element 4 is in the same position in both.
 * Element 5 is displaced by 3 positions.</p> 
 *
 * <p>Sum the deviations: 1 + 1 + 1 + 2 + 0 + 3 = 8.  And that is the distance.</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 * 
 * <p>Deviation distance was introduced in:<br>
 * S. Ronald, "More distance functions for order-based encodings," in Proc. IEEE CEC. IEEE Press, 1998, pp. 558–563.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 4.2.2021
 * 
 */
public final class DeviationDistance implements NormalizedPermutationDistanceMeasurer {
	
	/**
	 * Constructs the distance measurer as specified in the class documentation.
	 */
	public DeviationDistance() {}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if p1.length() is not equal to p2.length().
	 */
	@Override
	public int distance(Permutation p1, Permutation p2) {
		if (p1.length() != p2.length()) {
			throw new IllegalArgumentException("Permutations must be the same length");
		}
		
		int distancePoints = 0;
		int[] invP2 = p2.getInverse(); 
			  
		for (int i = 0; i < invP2.length; i++) {
			distancePoints += Math.abs(invP2[p1.get(i)]-i);
		}
		return distancePoints;  
	}
	
	@Override
	public int max(int length) {
		if (length <= 1) return 0;
		return (length * length - (length & 1)) >> 1;
	}
  
}