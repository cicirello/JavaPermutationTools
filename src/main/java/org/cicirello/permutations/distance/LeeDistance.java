/*
 * JavaPermutationTools: A Java library for computation on permutations and sequences
 * Copyright 2005-2022 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * <p>Lee Distance is closely related to deviation distance.  However, Lee Distance considers the
 * permutation to be a cyclic structure when computing positional deviations.  That is, an element's
 * deviation between permutations is the minimum of its deviation to the right or to the left (wrapping
 * around ends cyclicly).</p>
 *
 * <p>Consider p1 = [0, 1, 2, 3, 4, 5] and p2 = [5, 1, 0, 2, 3, 4].
 * Element 0 is 2 positions displaced (as in deviation distance).
 * Elements 2, 3, and 4 are a 1 position displaced (as in deviation distance).
 * Element 1 is stationary.
 * Element 5 is 1 position displaced (for Lee Distance) whereas for Deviation Distance it would be 5 positions displaced.
 * For Lee distance, the displacement is the minimum of how far it is displaced in either of the two directions.  In this
 * case element 5 is at the right most end in p1 and left most end of p2, which as a cyclic structure are adjacent positions.</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 *
 * <p>Described in:<br>
 * C. Lee, "Some properties of nonbinary error-correcting codes," in IRE Transactions on Information Theory, vol. 4, no. 2, pp. 77-82, June 1958.</p>
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * 
 */
public final class LeeDistance implements NormalizedPermutationDistanceMeasurer {
	
	/**
	 * Constructs the distance measurer as specified in the class documentation.
	 */
	public LeeDistance() {}

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
		if (p1.length() <= 1) return 0;
		int distancePoints = 0;
		int[] invP1 = p1.getInverse();
		int[] invP2 = p2.getInverse();
	  
		for (int i = 0; i < invP1.length; i++) {
			int dev = Math.abs(invP1[i]-invP2[i]);
			distancePoints += Math.min(dev, invP1.length - dev);
		}
	  
		return distancePoints;
	}
	
	@Override
	public int max(int length) {
		if (length <= 1) return 0;
		return length * (length >> 1);
	}

}