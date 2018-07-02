/*
 * Copyright 2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 *
 */
package org.cicirello.permutations.distance;

import org.cicirello.permutations.Permutation;

/**
 * <p>This class implements the combination of cyclic independence and reversal independence.
 * This is relevant if any rotation of the permutation or its reverse has the same problem dependent
 * interpretation.  For example, if the permutation represents a solution to a traveling salesperson problem (i.e.
 * a tour of a set of cities), then the cost of that tour is the same if you rotate it, reverse it, or reverse it and rotate it.</p>
 *
 * <p>In this case, this class computes the minimum of the distance from permutation p1
 * to rotations of p2 and rotations of the reverse of p2, where the underlying distance measure is passed as a parameter to
 * the constructor.</p>
 *
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 * @version 06.20.2018
 *  
 */
public final class CyclicReversalIndependentDistanceDouble implements PermutationDistanceMeasurerDouble {
	
	private PermutationDistanceMeasurerDouble d;
	
	/**
	 * Constructs a distance measure for measuring distance with cyclic and reversal independence, such that
	 * distance = min_{i in [0,N)} { distance(p1,rotate(p2,i)), distance(p1,rotate(reverse(p2),i)) }
	 * @param d A distance measure.
	 */
	public CyclicReversalIndependentDistanceDouble(PermutationDistanceMeasurerDouble d) {
		this.d = d;
	}
	
	/**
	 * Measures the distance between two permutations, with cyclic and reversal independence:
	 * distance = min_{i in [0,N)} { distance(p1,rotate(p2,i)), distance(p1,rotate(reverse(p2),i)) }
	 * 
	 * @param p1 first permutation
	 * @param p2 second permutation 
	 * @return distance between p1 and p2 
	 */
	@Override
	public double distancef(Permutation p1, Permutation p2) {
		double result = d.distancef(p1, p2);
		if (result > 0) {
			Permutation reverse2 = new Permutation(p2);
			reverse2.reverse();
			result = Math.min(result, d.distancef(p1, reverse2));
			if (result > 0) {
				int L = reverse2.length();
				for (int i = 0; i < L && result > 0; i++) {
					reverse2.rotate(1);   
					result = Math.min(result, d.distancef(p1, reverse2));
				}
			}
			if (result > 0) {
				Permutation pCopy = new Permutation(p2);
				int L = pCopy.length();
				for (int i = 0; i < L && result > 0; i++) {
					pCopy.rotate(1);   
					result = Math.min(result, d.distancef(p1, pCopy));
				}
			}
		}
		return result;
	}
}