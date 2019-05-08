/*
 * Copyright 2010, 2014, 2017-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * RType Distance:
 *
 * <p>RType distance treats the permutations as if they represent sets of directed edges, and counts
 * the number of edges that differ.</p>
 *
 * <p>Consider the example permutation: [1, 5, 2, 4, 0, 3]. 
 * RType distance treats this as equivalent to the set of directed edges:
 * {(1,5), (5,2), (2,4), (4,0), (0,3)}.</p> 
 *
 * <p>E.g., distance between [1, 5, 2, 4, 0, 3] and [ 5, 1, 4, 0, 3, 2] is 3.
 * Why? Well, the first permutation has the directed edges: {(1,5), (5,2), (2,4), (4,0), (0,3)}.
 * The second has 2 of these (4,0), and (0,3),
 * but does not include 3 of the edges: (1,5), (5,2), (2,4)</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 *
 * <p>RType distance was introduced in:<br>
 * V. Campos, M. Laguna, and R. Marti, "Context-independent scatter and tabu search for permutation problems," 
 * INFORMS Journal on Computing, vol. 17, no. 1, pp. 111–122, 2005.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.19.5.8 
 * @since 1.0
 */
public class RTypeDistance extends AbstractPermutationDistanceMeasurer {

	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int distance(Permutation p1, Permutation p2) {
		int countNonSharedEdges = 0;
		int L1 = p1.length();
		int L2 = p2.length();
		if (L1==L2 && L1==0) return 0;
		int[] successors2 = new int[L2];
		for (int i = 0; i < L2 - 1; i++) {
			successors2[p2.get(i)] = p2.get(i+1);
		}
		successors2[p2.get(L2-1)] = -1;
		
		for (int i = 0; i < L1 - 1; i++) {
			if (p1.get(i+1) != successors2[p1.get(i)]) countNonSharedEdges++;
		}
		return countNonSharedEdges;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int max(int length) {
		if (length <= 1) return 0;
		return length - 1;
	}

}
