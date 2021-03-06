/*
 * Copyright 2018-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * <p>This class implements the concept of a cyclic independent distance measure.
 * This is relevant if any rotation of the permutation has the same problem dependent
 * interpretation.</p>
 *
 * <p>In this case, this class computes the minimum of the distance from permutation p1
 * to rotations of p2, where the underlying distance measure is passed as a parameter to
 * the constructor.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.28.2021
 *  
 */
public final class CyclicIndependentDistanceDouble implements PermutationDistanceMeasurerDouble {
	
	private PermutationDistanceMeasurerDouble d;
	
	/**
	 * Constructs a distance measure for measuring distance with cyclic independence, such that
	 * distance = min_{i in [0,N)} distance(p1,rotate(p2,i))
	 * @param d A distance measure.
	 */
	public CyclicIndependentDistanceDouble(PermutationDistanceMeasurerDouble d) {
		this.d = d;
	}
	
	/**
	 * Measures the distance between two permutations, with cyclic independence:
	 * distance = min_{i in [0,N)} distance(p1,rotate(p2,i))
	 * 
	 * @param p1 first permutation
	 * @param p2 second permutation 
	 * @return distance between p1 and p2 
	 * @throws IllegalArgumentException if p1.length() is not equal to p2.length().
	 */
	@Override
	public double distancef(Permutation p1, Permutation p2) {
		double result = d.distancef(p1, p2);
		Permutation pCopy = new Permutation(p2);
		int L = pCopy.length();
		for (int i = 0; i < L && result > 0; i++) {
			pCopy.rotate(1);   
			result = Math.min(result, d.distancef(p1, pCopy));
		}
		return result;
	}
	
}