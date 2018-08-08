/*
 * Copyright 2015, 2017-2018 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * Reinsertion Distance:
 *
 * <p>Reinsertion distance is the count of the number of removal/reinsertion operations
 * needed to transform one permutation into the other.</p>
 * 
 * <p>This implementation utilizes the observation that the elements that must be removed and reinserted
 * are exactly those elements that are not in the longest common subsequence.</p>
 *
 * <p>Runtime: O(n^2), where n is the permutation length.</p>
 *
 * <p>Reinsertion distance more generally was described in:<br>
 * V. A. Cicirello and R. Cernera, <a href="https://www.cicirello.org/publications/cicirello2013flairs.html" target=_top>"Profiling the distance characteristics 
 * of mutation operators for permutation-based genetic algorithms,"</a> 
 * in Proceedings of the 26th FLAIRS Conference. AAAI Press, May 2013, pp. 46–51.</p> 
 *
 * <p>However, in that paper, it was computed using an adaptation of string Edit Distance.</p>
 *
 * <p>For description of computing it using the length of the longest common subsequence, see:<br> 
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2016evc.html" target=_top>"The Permutation in a Haystack Problem 
 * and the Calculus of Search Landscapes,"</a> 
 * IEEE Transactions on Evolutionary Computation, 20(3):434-446, June 2016.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 2.18.8.2 
 * @since 1.0
 *
 */
public class ReinsertionDistance extends AbstractPermutationDistanceMeasurer {

	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int distance(Permutation p1, Permutation p2) {
		return p1.length() - lcs(p1,p2);
	}
	
	private int lcs(Permutation p1, Permutation p2) {
		int L1 = p1.length();
		int L2 = p2.length();
		int start = L1;
		for (int i = 0; i < L1; i++) {
			if (p1.get(i) != p2.get(i)) {
				start = i;
				break;
			}
		}
		if (start == L1) return L1;
		int end = L1-1;
		for (int i = L1-1; i > start; i--) {
			if (p1.get(i) != p2.get(i)) {
				end = i;
				break;
			}
		}
		int C_length = end-start+2;
		int[][] C = new int[C_length][C_length];
		for (int i = 1; i < C_length; i++) {
			for (int j = 1; j < C_length; j++) {
				if (p1.get(start+i-1) == p2.get(start+j-1)) {
					C[i][j] = C[i-1][j-1] + 1;
				} else {
					C[i][j] = Math.max(C[i][j-1], C[i-1][j]);
				}
			}
		}
		return C[C_length-1][C_length-1]+start+L1-end-1;
	}

}