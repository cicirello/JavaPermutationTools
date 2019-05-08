/*
 * Copyright 2010, 2014-2015, 2017-2019 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * Cyclic RType Distance:
 *
 * <p>Cyclic RType distance treats the permutations as if they represent sets of directed edges, and counts
 * the number of edges that differ.  It treats the last to the first element as an edge.</p>
 *
 * <p>Consider the example permutation: [1, 5, 2, 4, 0, 3]. 
 * Cyclic RType distance treats this as equivalent to the set of directed edges:
 * {(1,5), (5,2), (2,4), (4,0), (0,3), (3,1)}.</p> 
 *
 * <p>E.g., distance between [1, 5, 2, 4, 0, 3] and [ 5, 1, 4, 0, 3, 2] is 4.
 * Why? Well, the first permutation has the directed edges: {(1,5), (5,2), (2,4), (4,0), (0,3), (3,1)}.
 * The second has 2 of these (4,0), and (0,3),
 * but does not include 4 of the edges: (1,5), (5,2), (2,4), (3,1)</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 *
 * <p>Cyclic RType distance was introduced in:<br>
 * V.A. Cicirello, <a href="https://www.cicirello.org/publications/cicirello2016evc.html" target=_top>"The Permutation in a Haystack Problem 
 * and the Calculus of Search Landscapes,"</a> 
 * IEEE Transactions on Evolutionary Computation, 20(3):434-446, June 2016.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.19.5.8 
 * @since 1.0
 */
public class CyclicRTypeDistance extends AbstractPermutationDistanceMeasurer {
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int distance(Permutation p1, Permutation p2) {
		int countNonSharedEdges = 0;
		int L1 = p1.length();
		int L2 = p2.length();
		
		int[] successors2 = new int[L2];
		for (int i = 0; i < L2; i++) {
			successors2[p2.get(i)] = p2.get((i+1) % L2);
		}
		
		for (int i = 0; i < L1; i++) {
			if (p1.get((i+1) % L1) != successors2[p1.get(i)]) countNonSharedEdges++;
		}
		return countNonSharedEdges;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int max(int length) {
		if (length <= 2) return 0;
		return length;
	}
}
