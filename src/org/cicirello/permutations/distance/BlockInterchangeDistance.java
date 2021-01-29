/*
 * Copyright 2019, 2021 Vincent A. Cicirello, <https://www.cicirello.org/>.
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
 * Block Interchange Distance:
 *
 * <p>Block Interchange Distance is the minimum number of block interchanges 
 * necessary to transform one permutation into the other.  A block interchange is
 * an edit operation that takes two non-overlapping blocks (i.e., subsequence)
 * and exchanges their locations in the permutation.  For example, the
 * permutation p1 = [0, 1, 2, 3, 4, 5, 6, 7] and p2 = [5, 6, 3, 4, 0, 1, 2, 7]
 * is a block interchange distance of 1 since you can transform p2 into
 * p1 by exchanging the blocks [5, 6] and [0, 1, 2] within p2.</p>
 *
 * <p>Interchange distance is computed efficiently using the algorithm
 * described in the article: D.A. Christie, "Sorting permutations by 
 * block-interchanges," Information Processing Letters, vol 60, pages 165-169, 1996.</p>
 *
 * <p>Runtime: O(n), where n is the permutation length.</p>
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 * @version 1.28.2021
 */
public class BlockInterchangeDistance extends AbstractPermutationDistanceMeasurer {
	
	/**
	 * Constructs the distance measurer as specified in the class documentation.
	 */
	public BlockInterchangeDistance() {}
	
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
		int[] inv2 = p2.getInverse();
		int[] p = new int[inv2.length+2];
		int[] inv = new int[p.length];
		boolean[] visited = new boolean[p.length];
		for (int i = 0; i < p1.length(); i++) {
			int index = inv2[p1.get(i)]+1;
			p[index] = i+1;
			inv[i+1] = index;
		}
		p[0] = inv[0] = 0;
		p[p.length-1] = inv[p.length-1] = p.length-1;
		int cycles = 0;
		for (int i = 0; i <= p1.length(); i++) {
			if (!visited[i]) {
				cycles++;
				int j = i;
				while (!visited[j]) {
					visited[j] = true;
					j++;
					j = p[inv[j]-1];
				}
			}
		}
		return (p1.length()+1-cycles)/2;
	}
	
	@Override
	public int max(int length) {
		return length >> 1;
	}
}