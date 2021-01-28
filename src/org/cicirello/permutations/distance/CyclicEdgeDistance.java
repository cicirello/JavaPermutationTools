/*
 * Copyright 2014-2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * Cyclic Edge Distance:
 *
 * <p>Cyclic edge distance treats the permutations as if they represent sets of edges, and counts
 * the number of edges that differ.  It treats the last to the first element as an edge.</p>
 *
 * <p>Consider the example permutation: [1, 5, 2, 4, 0, 3]. 
 * Cyclic edge distance treats this as equivalent to the set of undirected edges:
 * {(1,5), (5,2), (2,4), (4,0), (0,3), (3,1)}.</p>
 *
 * <p>E.g., distance between [1, 5, 2, 4, 0, 3] and [ 5, 1, 4, 0, 3, 2] is 3.
 * Why? Well, the first permutation has the edges: {(1,5), (5,2), (2,4), (4,0), (0,3), (3,1)}.
 * The second has three of these (5,1), which is the same as (1,5) since they are undirected edges, (4,0), and (0,3),
 * but does not include 3 of the edges: (5,2), (2,4), (3,1)</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 *
 * <p>Cyclic edge distance was first described in:<br>
 * S. Ronald, "Distance functions for order-based encodings," in Proc. IEEE CEC. IEEE Press, 1997, pp. 49–54.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.28.2021
 */
public final class CyclicEdgeDistance extends AbstractPermutationDistanceMeasurer {
	
	/**
	 * Constructs the distance measurer as specified in the class documentation.
	 */
	public CyclicEdgeDistance() {}
	
	@Override
	public int distance(Permutation p1, Permutation p2) {
		if (p1.length() != p2.length()) {
			throw new IllegalArgumentException("Permutations must be the same length");
		}
		int countNonSharedEdges = 0;
		int[] successors2 = new int[p2.length()];
		for (int i = 0; i < successors2.length; i++) {
			successors2[p2.get(i)] = p2.get((i+1) % successors2.length);
		}
		
		for (int i = 0; i < successors2.length; i++) {
			if (p1.get((i+1) % successors2.length) != successors2[p1.get(i)] && p1.get(i) != successors2[p1.get((i+1) % successors2.length)]) countNonSharedEdges++;
		}
		
		return countNonSharedEdges;
	}
	
	@Override
	public int max(int length) {
		if (length <= 3) return 0;
		if (length == 4) return 2;
		return length;
	}
}
