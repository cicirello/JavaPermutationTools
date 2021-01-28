/*
 * Copyright 2014, 2015, 2017-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
import java.util.Arrays;

/**
 * Kendall Tau Distance:
 *
 * <p>Kendall Tau distance is sometimes also known as bubble sort distance, as it is
 * the number of adjacent swaps necessary to transform one permutation into the other.</p>
 *
 * <p>Another way of describing it is the number of pairs of elements whose order is inverted
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
 * <p>Runtime: O(n lg n), where n is the permutation length.  This runtime is achieved using a modified version of
 * mergesort to count the inversions.</p>
 *
 * <p>Kendall Tau distance originally described in:<br>
 * M. G. Kendall, "A new measure of rank correlation," Biometrika, vol. 30, no. 1/2, pp. 81–93, June 1938.</p>
 * 
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.28.2021
 * 
 */
public final class KendallTauDistance extends AbstractPermutationDistanceMeasurer {
	
	/**
	 * Constructs the distance measurer as specified in the class documentation.
	 */
	public KendallTauDistance() {}

	@Override
	public int distance(Permutation p1, Permutation p2) {
		if (p1.length() != p2.length()) {
			throw new IllegalArgumentException("Permutations must be the same length");
		}
		
		// use inverse of p1 as a relabeling
		int[] invP1 = p1.getInverse();
		
		// relabel array copy of p2
		int[] arrayP2 = new int[invP1.length];
		for (int i = 0; i < arrayP2.length; i++) {
			arrayP2[i] = invP1[p2.get(i)];
		}
		return countInversions(arrayP2);
	}
	
	@Override
	public int max(int length) {
		if (length <= 1) return 0;
		return length*(length - 1)>>1;
	}
	
	private int countInversions(int[] array) {
		if (array.length <= 1) return 0;
		int m = array.length >> 1;
		int[] left = Arrays.copyOfRange(array, 0, m);
		int[] right = Arrays.copyOfRange(array, m, array.length);
		int count = countInversions(left) + countInversions(right);
		int i = 0;
		int j = 0;
		int k = 0;
		while (i < left.length && j < right.length) {
			if (left[i] < right[j]) {
				array[k] = left[i];
				i++;
				k++;
			} else {
				// inversions
				count += (left.length - i);
				array[k] = right[j];
				j++;
				k++;
			}
		}
		while (i < left.length) {
			array[k] = left[i];
			i++;
			k++;
		}
		while (j < right.length) {
			array[k] = right[j];
			j++;
			k++;
		}
		return count;
	}
	
}