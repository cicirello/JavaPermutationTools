/*
 * Copyright 2014, 2015, 2017-2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * Kendall Tau Distance:
 *
 * <p>Kendall Tau distance is sometimes also known as bubble sort distance, as it is
 * the number of adjacent swaps necessary to transform one permutation into the other.</p>
 *
 * <p>Another way of describing it is the number of pairs of elements whose order is opposite
 * in one permutation relative to the other.</p>
 * 
 * <p>For example, consider p1 = [0, 1, 2, 3, 4] and p2 = [0, 3, 2, 1, 4].
 * The length is 5, so there are a total of 5*4/2 = 10 pairs of elements.
 * 0 precedes all of 1, 2, 3, and 4 in both permutations.
 * However, 1 precedes 2, 3, and 4 in p1, but only 4 in p2, so 2 adjacent swaps are needed for element 1.
 * Elements 2 and 3 are in one order in p1, but switched in p2 relative to p1.
 * So a total of 3 adjacent swaps are needed to transform p1 to p2.  Kendall Tau distance is thus 3.</p>
 *
 * <p>Kendall originally normalized the distance, but more recently many do not.  Our implementation does not normalize.</p>
 *
 * <p>Runtime: O(n^2), where n is the permutation length.</p>
 *
 * <p>Kendall Tau distance originally described in:<br>
 * M. G. Kendall, "A new measure of rank correlation," Biometrika, vol. 30, no. 1/2, pp. 81–93, June 1938.</p>
 * 
 * @author <a href=https://www.cicirello.org/>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/>https://www.cicirello.org/</a>
 * @version 06.06.2018
 * 
 */
public class KendallTauDistance extends PermutationDistanceMeasurerBase {

	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int distance(Permutation p1, Permutation p2) {
		int count = 0;
		int L1 = p1.length();
	  
		int[] invP1 = p1.getInverse();
		int[] invP2 = p2.getInverse();
		
		for (int i = 0; i < L1-1; i++) {
			for (int j = i+1; j < L1; j++) {
				if ((invP1[i]-invP1[j])*(invP2[i]-invP2[j]) < 0) count++; 
			}
		}
		
		return count;
	}
	
	
}